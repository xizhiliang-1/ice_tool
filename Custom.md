import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BorderPaneAndGridPaneExample extends Application {
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // Create a MenuBar
        MenuBar menuBar = new MenuBar();

        // Create a Menu
        Menu fileMenu = new Menu("File");

        // Create MenuItems and add them to the Menu
        MenuItem openMenuItem = new MenuItem("Open");
        MenuItem saveMenuItem = new MenuItem("Save");
        fileMenu.getItems().addAll(openMenuItem, saveMenuItem);

        // Add the Menu to the MenuBar
        menuBar.getMenus().add(fileMenu);

        // Set the MenuBar at the top of the BorderPane
        root.setTop(menuBar);

        // Create a GridPane
        GridPane gridPane = new GridPane();

        // Add content to the GridPane
        // ...

        // Set the GridPane at the center of the BorderPane
        root.setCenter(gridPane);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("BorderPane and GridPane Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
