package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * Team Members:
 * @author Payden Brown
 * @author Jonathan Chance
 * @author Conner Eilenfeldt
 * @author Ajay Tiwari
 * 
 * Class ID: CSE360 85141
 * 
 * Assignment: Team Project TextALot
 * Description:
 * Main initializes and starts the program
 */
public class Main extends Application {
	@Override
	/**
	 * start initializes the GUI
	 * @param primaryStage the GUI window
	 */
	public void start(Stage primaryStage) {
		try {
			DisplayGui root = new DisplayGui();
			int sceneWidth = root.getTextAreaWidth();
			int sceneHeight = root.getTextAreaHeight();

			Scene scene = new Scene(root,sceneWidth * 2,sceneHeight);
			root.prefWidthProperty().bind(primaryStage.widthProperty().multiply(1));
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * main launches the program
	 * @param args the arguments passed to the program
	 */
	public static void main(String[] args) {
		launch(args);
	}
}