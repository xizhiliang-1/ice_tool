package com.xizhi.showme;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * JavaFxApplicationSupport, an instance of this class is created by Application.main(...)
 * 
 * @author samuel.cuellar
 */

@SpringBootApplication
public class JavaFxApplicationSupport extends javafx.application.Application {

	private static Logger LOGGER = LoggerFactory.getLogger(JavaFxApplicationSupport.class);
	private static ConfigurableApplicationContext applicationContext;
	private static List<Image> icons = new ArrayList<>();

	@Override
	public void init() throws Exception {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(JavaFxApplicationSupport.class);
		builder.application().setWebApplicationType(WebApplicationType.NONE);
		applicationContext = builder.run(getParameters().getRaw().toArray(new String[0]));
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Properties props = new Properties();
		try (InputStream in = getClass().getResourceAsStream("/config.properties")) {
			if (in != null) {
				props.load(in);
				System.out.println(props);
			} else {
				System.err.println("config.properties not found");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		String dbUrl = props.getProperty("db.url");
		String dbUsername = props.getProperty("db.username");
		String dbPassword = props.getProperty("db.password");




		primaryStage.setTitle("My JavaFX App");

		// Create the UI elements
		Label label = new Label("This is a label");
		CheckBox checkBox1 = new CheckBox("Checkbox 1");
		CheckBox checkBox2 = new CheckBox("Checkbox 2");
		CheckBox checkBox3 = new CheckBox("Checkbox 3");
		CheckBox checkBox4 = new CheckBox("Checkbox 4");

		//checkBox1.setSelected(true);

		RadioButton radioBtn1 = new RadioButton("Option 1");
		RadioButton radioBtn2 = new RadioButton("Option 2");
		ToggleGroup radioGroup = new ToggleGroup();
		radioBtn1.setToggleGroup(radioGroup);
		radioBtn2.setToggleGroup(radioGroup);
		Button button = new Button("Click me!");
		Button button2 = new Button("Button 2");

		button.setOnAction(event -> {
			String dialogText;
			if (checkBox1.isSelected()) {
				dialogText = "CheckBox 1";

			} else if (checkBox2.isSelected()) {
				dialogText = "CheckBox 2";
			} else {
				dialogText = "No option selected";
			}
			Label dialogLabel = new Label(dialogText);
			VBox dialogLayout = new VBox(10, dialogLabel);
			dialogLayout.setPadding(new Insets(10));
			Scene dialogScene = new Scene(dialogLayout);
			Stage dialog = new Stage();
			dialog.setScene(dialogScene);
			dialog.show();
		});

		button2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// create a dialog
				Stage dialog = new Stage();
				VBox root = new VBox();
				root.setAlignment(Pos.CENTER);
				Label label = new Label("Do you want to proceed?");
				root.getChildren().add(label);
				// create radio buttons
				RadioButton yesButton = new RadioButton("Yes");
				RadioButton noButton = new RadioButton("No");
				ToggleGroup toggleGroup = new ToggleGroup();
				toggleGroup.getToggles().addAll(yesButton, noButton);
				root.getChildren().addAll(yesButton, noButton);
				// create a button for the dialog
				Button dialogButton = new Button("OK");
				dialogButton.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						// check which radio button is selected
						if (yesButton.isSelected()) {
							System.out.println("You clicked yes.");
							// do something if "yes" is selected
							try {
								generateExcel();
							} catch (IOException e) {
								throw new RuntimeException(e);
							}
						} else if (noButton.isSelected()) {
							System.out.println("You clicked no.");
							// do something if "no" is selected
						}
						// close the dialog
						dialog.close();
					}
				});
				root.getChildren().add(dialogButton);
				Scene dialogScene = new Scene(root, 300, 200);
				dialog.setScene(dialogScene);
				// show the dialog
				dialog.show();
			}
		});

		ChoiceBox<String> choiceBox = new ChoiceBox<>();
		choiceBox.getItems().addAll("Option 1", "Option 2", "Option 3");

		// Create the layout
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.add(label, 0, 0,2,1);
		grid.add(checkBox1, 0, 1,2,1);
		grid.add(checkBox2, 2, 1,2,1);
		grid.add(checkBox3, 4, 1,2,1);
		grid.add(checkBox4, 6, 1,2,1);

		grid.add(radioBtn1, 0, 2);
		grid.add(radioBtn2, 0, 3);

		HBox hbox = new HBox(1);
		//hbox.setAlignment(Pos.BOTTOM_RIGHT);
		hbox.getChildren().add(button);
		hbox.getChildren().add(choiceBox);
		grid.add(hbox, 0, 4,8,2);

		grid.add(button2,0,6,2,1);

		// Create the scene and add the layout
		Scene scene = new Scene(grid, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void generateExcel() throws IOException {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Sheet1");

		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("A");
		header.createCell(1).setCellValue("B");
		header.createCell(2).setCellValue("C");
		header.createCell(3).setCellValue("D");

		Row dataRow = sheet.createRow(1);
		dataRow.createCell(0).setCellValue("data A");
		dataRow.createCell(1).setCellValue("data B");
		dataRow.createCell(2).setCellValue("data C");
		dataRow.createCell(3).setCellValue("data D");

		FileOutputStream fileOut = new FileOutputStream("output.xlsx");
		workbook.write(fileOut);
		fileOut.close();
	}

	@Override
	public void stop() throws Exception {
		applicationContext.close();
	}

	
	public static void main(String[] args) {
		launch(args);
	}
}
