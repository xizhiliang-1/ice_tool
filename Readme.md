java -jar xxx.jar --spring.config.name=custom --spring.config.location=file:./

<dependency>
    <groupId>org.json</groupId>
    <artifactId>json</artifactId>
    <version>20200518</version>
</dependency>

Properties props = new Properties();
try (InputStream in = getClass().getResourceAsStream("./config.properties");
     OutputStream out = new FileOutputStream("./config.properties")) {

    if (in != null) {
        props.load(in);
        props.setProperty("key1", "value1");
        props.setProperty("key2", "value2");
        props.store(out, "Updated config properties");
        System.out.println(props);
    } else {
        System.err.println("config.properties not found");
    }
} catch (IOException e) {
    e.printStackTrace();
}

String temp = "aaa,bbbb,ccccc";
List<String> list = Arrays.asList(temp.split(","));
System.out.println(list);
    
    
 import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MyJavaFXApp extends Application {

    @Override
    public void start(Stage stage) {
        // Create a button
        Button button = new Button("Click me!");

        // Set the action to be performed when the button is clicked
        button.setOnAction(event -> {
            // Create a label with the message to be displayed
            Label label = new Label("Hello, world!");

            // Create a layout for the label and set its alignment
            VBox layout = new VBox(label);
            layout.setAlignment(Pos.CENTER);

            // Create a scene with the layout and set its size
            Scene scene = new Scene(layout, 300, 200);

            // Create a new stage and set its scene
            Stage popup = new Stage();
            popup.setScene(scene);

            // Show the popup window
            popup.show();
        });

        // Create a layout for the button and set its alignment
        VBox layout = new VBox(button);
        layout.setAlignment(Pos.CENTER);

        // Create a scene with the layout and set its size
        Scene scene = new Scene(layout, 300, 200);

        // Set the stage's scene and show it
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
