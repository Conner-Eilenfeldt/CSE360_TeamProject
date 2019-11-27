package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			DisplayGui root = new DisplayGui();
			int sceneWidth = root.getTextAreaWidth();
			int sceneHeight = root.getTextAreaHeight();
			
			Scene scene = new Scene(root,sceneWidth * 2,sceneHeight);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			root.prefWidthProperty().bind(primaryStage.widthProperty().multiply(1));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
