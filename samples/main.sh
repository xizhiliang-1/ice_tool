#!/usr/bin/env bash

# Enable security features:
#   > Treat unset variables as an error when substituting
#   > Exit immediately if a command exits with a non-zero status
#   > Disable file name generation (globbing)
#   > Return value of a pipeline
set -o pipefail

readonly LOG_FILE="${PWD}/repo-log.log"
readonly GITHUB_API_URL="https://api.github.com"
readonly GITHUB_USER_URL="${GITHUB_API_URL}/user"
readonly GITHUB_API_FILE_PERM="600"
readonly ENCRYPTION_KEY_FILE_PERM="600"
readonly SSH_PRIVATE_KEY_FILE_PERM="600"
readonly SSH_PUBLIC_KEY_FILE_PERM="644"
readonly LOG_FILE_PERM="700"
readonly SSH_KEYS_STORE="${HOME}/.ssh"

source ./utils/log.sh
source ./utils/os.sh
source ./utils/commands.sh
source ./github_backup_repositories.sh
source ./github_upload_ssh_key.sh
source ./generate_ssh_keys.sh

GITHUB_API_KEY_FILE=""
ENCRYPTION_KEY_FILE=""
SSH_PUBLIC_KEY_FILE=""
SSH_PRIVATE_KEY_FILE=""
PERFORM_BACKUP=0
ENCRYPT_BACKUP=1
GENERATE_SSH_KEY=0
DEFAULT_UMASK_VALUE=$($COMMAND_UMASK)
REQUIRED_UMASK_VALUE="0077"

# Ensure user running the script has ownership and permissions over the API key 
# file.
validate_github_api_key_file() {
    if [[ -e "$GITHUB_API_KEY_FILE" ]]; then
        if validate_file_permissions "$GITHUB_API_KEY_FILE" "$GITHUB_API_FILE_PERM"; then
            GITHUB_API_TOKEN=$(<$GITHUB_API_KEY_FILE)
            log "6" "MAIN" "Using GitHub API key file '${GITHUB_API_KEY_FILE}'"
        else
            echo "GitHub API key file has no proper ownership or file permissions"
            log "4" "MAIN" "GitHub API key file '${GITHUB_API_KEY_FILE}' has no proper permissions"
            exit 1
        fi
    else
        echo "GitHub API key file not provided. Please check usage information."
        echo ""
        usage
        exit 1
    fi
}

# Ensure user running the script has ownership and permissions over the encryption
# key file.
validate_encryption_key_file() {
    if [[ -e "$ENCRYPTION_KEY_FILE" ]]; then
        if  ! validate_file_permissions "$ENCRYPTION_KEY_FILE" "$ENCRYPTION_KEY_FILE_PERM"; then
            echo "Encryption key file has no proper ownership or file permissions"
            log "4" "MAIN" "Encryption key file '${ENCRYPTION_KEY_FILE}' has no proper permissions"            
            exit 1
        fi
    else
        echo "Encryption key file not provided. Please check usage information."
        echo ""
        usage
        exit 1
    fi
}

# Ensure user running the script has ownership and permissions over the SSH 
# private key file.
validate_ssh_private_key_file() {
    if [[ -e "$SSH_PRIVATE_KEY_FILE" ]]; then
        if  ! validate_file_permissions "$SSH_PRIVATE_KEY_FILE" "$SSH_PRIVATE_KEY_FILE_PERM"; then
            echo "SSH private key file has no proper ownership or file permissions"
            log "4" "MAIN" "SSH private key file ${SSH_PRIVATE_KEY_FILE} has no proper permissions"
            exit 1
        fi
    else
        echo "SSH private key file not provided. Please check usage information."
        echo ""
        usage
        exit 1
    fi
}

# Ensure user running the script has ownership and permissions over the SSH 
# public key file.
validate_ssh_public_key_file() {
    if [[ -e "$SSH_PUBLIC_KEY_FILE" ]]; then
        if  ! validate_file_permissions "$SSH_PUBLIC_KEY_FILE" "$SSH_PUBLIC_KEY_FILE_PERM"; then
            echo "SSH public key file has no proper ownership or file permissions"
            log "4" "MAIN" "SSH public key file ${SSH_PUBLIC_KEY_FILE} has no proper permissions"
            exit 1
        fi
    else
        echo "SSH public key file not provided. Please check usage information."
        echo ""
        usage
        exit 1
    fi
}

usage() {
    echo "
    USAGE:
        `basename $0` [options]

        Options:
            -a <api_key_file>           specify the GitHub API key file
            -e <encryption_key_file>    private encryption key file
            -i <ssh_private_key_file>   SSH private key file
            -u <ssh_public_key_file>    upload public SSH key to GitHub
            -n                          disable repository backup encryption
            -b                          create a local backup of GitHub repositories
            -g                          generate SSH key
            -h                          show this help
    "
    exit 1
}


# Ensure Bash version >= 4 is being used
if [[ ! "${BASH_VERSINFO[0]:-0}" -ge 4 ]]; then
    echo ""
    echo "Incorrect Bash version. This script requires Bash version >= 4"
    echo ""
    exit 1
fi

if [[ $# -eq 0 ]]; then
    usage
fi

# Create the log file
if [[ ! -f "$LOG_FILE" ]]; then
    $COMMAND_TOUCH "$LOG_FILE"
    log "6" "MAIN" "Log file '$LOG_FILE' created"
    echo "Log file '$LOG_FILE' has been created"
else
    # If the log file already exists, ensure it has proper permissions
    if ! permissions_are_equal "$LOG_FILE" "$LOG_FILE_PERM"; then
        $COMMAND_CHMOD "$LOG_FILE_PERM" "$LOG_FILE"
        log "4" "MAIN" "Log file '$LOG_FILE' has no proper permissions. Fixed."
    fi
fi

# Parse command line flags
while getopts ":a:e:i:u:nbgh" OPTION; do
    case $OPTION in
        a )
            GITHUB_API_KEY_FILE=$OPTARG
            ;;
        b )
            PERFORM_BACKUP=1
            ;;
        e )
            ENCRYPTION_KEY_FILE=$OPTARG
            ;;
        i )
            SSH_PRIVATE_KEY_FILE=$OPTARG
            ;;
        n )
            ENCRYPT_BACKUP=0
            ;;
        g )
            GENERATE_SSH_KEY=1
            ;;
        u )
            SSH_PUBLIC_KEY_FILE=$OPTARG
            ;;
        h )
            usage
            ;;
        * )
            echo "Unknown option"
            usage
            ;;
    esac
done
shift $(($OPTIND - 1))


# Ensure all files created have restricted permissions
$COMMAND_UMASK $REQUIRED_UMASK_VALUE

# Perform backup option
if [[ "$PERFORM_BACKUP" -eq 1 ]]; then
    validate_github_api_key_file
    
    if [[ "$ENCRYPT_BACKUP" -eq 1 ]]; then
        validate_encryption_key_file
    fi

    validate_ssh_private_key_file

    backup_repositories
fi

# Generate SSH keys option
if [[ "$GENERATE_SSH_KEY" -eq 1 ]]; then
    echo "Please enter the following information to generate the SSH keys"
    echo ""
    read -p "User email: " USER_EMAIL
    read -p "SSH key name: " KEY_NAME
    read -sp "SSH passphrase: " KEY_PASSPHRASE
    echo ""
    echo ""

    crypto_generate_keys "$USER_EMAIL" "$KEY_NAME" "$KEY_PASSPHRASE" "$SSH_KEYS_STORE"
fi

# Upload SSH public key option
if [[ ! -z "$SSH_PUBLIC_KEY_FILE" ]]; then
    validate_github_api_key_file

    echo "Please enter the following information to upload the SSH public key"
    echo ""
    read -p "GitHub key name: " KEY_NAME
    echo ""
    echo ""

    validate_ssh_public_key_file

    upload_ssh_public_key "$KEY_NAME" "$SSH_PUBLIC_KEY_FILE"
fi

echo ""
echo ""
echo "DONE !"
echo ""

exit 0
