import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class MenuItemWithAlertExample extends Application {
    @Override
    public void start(Stage primaryStage) {
        MenuItem menuItem = new MenuItem("Open URL");

        menuItem.setOnAction(event -> {
            // Create an Alert
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("URL Information");
            alert.setHeaderText(null);

            // Create a TextFlow for the content
            TextFlow textFlow = new TextFlow();
            textFlow.setPrefWidth(400); // Set desired width

            // Create text nodes with selectable URLs
            Text text1 = new Text("This is some text. ");
            Text text2 = new Text("Click here to open a URL.");
            text2.setStyle("-fx-fill: blue; -fx-underline: true; -fx-cursor: hand;");

            // Add event handler to open the URL in a browser
            text2.setOnMouseClicked(clickEvent -> {
                // Open the URL in a browser
                String url = "https://example.com";
                getHostServices().showDocument(url);
            });

            // Add the text nodes to the TextFlow
            textFlow.getChildren().addAll(text1, text2);

            // Set the content of the Alert to the TextFlow
            alert.getDialogPane().setContent(textFlow);

            // Create a Button for copy operation
            Button copyButton = new Button("Copy");
            ButtonBar.setButtonData(copyButton, ButtonBar.ButtonData.OK_DONE);

            // Add event handler to perform copy operation
            copyButton.setOnAction(copyEvent -> {
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                content.putString(text2.getText()); // Copy the URL
                clipboard.setContent(content);
            });

            // Add the copy button to the Alert's ButtonBar
            alert.getButtonTypes().add(ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getDialogPane().setContent(new VBox(10, textFlow, copyButton));

            // Show the Alert window
            alert.showAndWait();
        });

        // Add the MenuItem to a Menu or MenuBar
        // ...

        // Create a Scene and add it to the Stage
        // ...

        primaryStage.setTitle("Menu Item with Alert Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
