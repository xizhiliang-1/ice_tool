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
    
    <?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <include resource=
      "org/springframework/boot/logging/logback/defaults.xml" />
    <include resource=
      "org/springframework/boot/logging/logback/file-appender.xml" />
    <root level="INFO">
        <appender-ref ref="FILE" />
    </root>
</configuration>

    logging.file=baeldung-disabled-console.log
    
    
    @echo off
set java=
for /f "delims=" %%a in ('where javaw') do @set java=%%a
%java% -jar showme.jar --spring.config.name=custom-config,application

打开IntelliJ IDEA并确保您正在查看项目的文件视图。

使用快捷键Shift + Shift 或者点击菜单栏中的"Find" -> "Find in Path"来打开搜索窗口。

在搜索窗口中，您将看到一个文本框和一些选项。在文本框中，输入您要搜索的第一个关键词。

在搜索窗口底部的选项中，选择"Regular Expression"（正则表达式）复选框。这将使您能够使用正则表达式来进一步定义搜索模式。

在文本框中，输入一个正则表达式来匹配同时包含第二个关键词的文件。例如，如果您要搜索同时包含"keyword1"和"keyword2"的文件，您可以使用正则表达式(?=.*keyword1)(?=.*keyword2).*。

点击搜索窗口中的"Find"按钮或按下回车键，IntelliJ IDEA将开始搜索并显示包含两个关键词的所有文件的结果。


请注意，这是一个使用正则表达式的高级搜索方法，确保您在使用正则表达式时有一定的了解。如果您只是简单地想搜索两个关键词，而不使用正则表达式，您可以跳过步骤4并在文本框中输入两个关键词，用空格分隔它们。
    
    
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MenuBarExample extends Application {
    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();

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

        // Add the MenuBar to the GridPane
        gridPane.add(menuBar, 0, 0); // Add it to the first row and first column

        // Add other content to the GridPane
        // ...

        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Menu Bar Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

