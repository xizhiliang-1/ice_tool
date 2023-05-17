pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        script {
          // Retrieve checkbox selection
          def checkboxSelection = "${CHECKBOX_PARAMETER_NAME}".split('\n')
          println "Selected checkboxes: ${checkboxSelection}"
          
          // Perform actions based on checkbox selection
          if (checkboxSelection.contains("Option 1")) {
            // Perform actions for Option 1
          }
          if (checkboxSelection.contains("Option 2")) {
            // Perform actions for Option 2
          }
          // ...
        }
      }
    }
  }
}

In the example above, ${CHECKBOX_PARAMETER_NAME} represents the environment variable for the checkbox parameter you defined in your Jenkins job configuration. The checkboxSelection variable is an array containing the selected checkboxes' values.


You can then use the checkboxSelection array to perform specific actions or logic based on the selected checkboxes.


Please note that the syntax above assumes you're using a Jenkins pipeline. If you're using a different build script format (e.g., Freestyle job with shell script build steps), the syntax for accessing environment variables may vary slightly.

pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        script {
          // Retrieve checkbox selection
          def checkboxSelection = params.CHECKBOX_PARAMETER_NAME.split('\n')
          println "Selected checkboxes: ${checkboxSelection}"
          
          // Perform actions based on checkbox selection
          if (checkboxSelection.contains("Option 1")) {
            // Perform actions for Option 1
          }
          if (checkboxSelection.contains("Option 2")) {
            // Perform actions for Option 2
          }
          // ...
        }
      }
    }
  }
}

In the above script, params.CHECKBOX_PARAMETER_NAME is used to access the selected checkboxes. The params object in a Jenkins Declarative Pipeline provides access to the parameters defined for the job, and CHECKBOX_PARAMETER_NAME refers to the name you provided for the checkbox parameter in your job configuration.


You can then use the checkboxSelection array to perform specific actions or logic based on the selected checkboxes, just like in the previous example.


Remember to replace CHECKBOX_PARAMETER_NAME with the actual name you provided for your checkbox parameter in the Jenkins job configuration.
