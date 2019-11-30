package application;

import java.io.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;	

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
 * DisplayGui makes a GUI and displays the formatted text and any errors generated by the program
 */
public class DisplayGui extends VBox
{
	private final int TEXT_AREA_WIDTH = 600;
	private final int TEXT_AREA_HEIGHT = 600;
	private final int ADJUSTMENT_LABEL_SPACE = 20;

	private Button openButton, saveButton;
	private TextArea formattedTextDisplay,errorDisplay;
	private TextField openFileField, saveFileField;
	private Label inputOutputError, displayTextLabel,errorLabel;
	private ColumnTester executor;

	/**
	 * DisplayGui constructor
	 */
	public DisplayGui()
	{
		int adjustmentSpace = 10;

		executor = new ColumnTester(this);

		openButton = new Button("OPEN");
		saveButton = new Button("SAVE");
		openButton.setMinWidth(80.0);
		saveButton.setMinWidth(80.0);

		formattedTextDisplay = new TextArea("");
		formattedTextDisplay.setEditable(false);
		formattedTextDisplay.setStyle("-fx-font-family: monospace");
		formattedTextDisplay.setPrefSize(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT);

		errorDisplay = new TextArea("");
		errorDisplay.setEditable(false);
		errorDisplay.setStyle("-fx-font-family: monospace");
		errorDisplay.setPrefSize(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT);

		openFileField = new TextField("");
		saveFileField = new TextField("");

		//Initialize Labels
		inputOutputError = new Label("Testing For Error");
		inputOutputError.setTextFill(Color.RED);

		displayTextLabel = new Label("Display");
		errorLabel = new Label("Errors");
		displayTextLabel.setPadding(new Insets(0,0,0,(TEXT_AREA_WIDTH - displayTextLabel.getText().length())
				/2 - ADJUSTMENT_LABEL_SPACE));
		errorLabel.setPadding(new Insets(0,0,0,(TEXT_AREA_WIDTH - displayTextLabel.getText().length())
				/2 - ADJUSTMENT_LABEL_SPACE));

		HBox openPane = new HBox();
		openPane.setSpacing(adjustmentSpace);
		openPane.setPadding(new Insets(10, 10, 10, 10));
		openPane.getChildren().addAll(openButton,openFileField);

		HBox savePane = new HBox();
		savePane.setSpacing(adjustmentSpace);
		savePane.setPadding(new Insets(10, 10, 10, 10));
		savePane.getChildren().addAll(saveButton,saveFileField);

		VBox inputPane = new VBox();
		inputPane.setSpacing(5);
		inputPane.setPadding(new Insets(10, 10, 10, 10));
		inputPane.getChildren().addAll(openPane,savePane,inputOutputError);
		inputPane.setPadding(new Insets(0,0,0,TEXT_AREA_WIDTH - 150));

		VBox displayTextPane = new VBox();
		displayTextPane.getChildren().addAll(displayTextLabel,formattedTextDisplay);

		VBox errorPane = new VBox();
		errorPane.getChildren().addAll(errorLabel,errorDisplay);

		HBox displayPane = new HBox();
		displayPane.getChildren().addAll(displayTextPane,errorPane);

		this.getChildren().addAll(inputPane,displayPane);
		this.setPrefSize(1000, 600);

		ButtonHandler fileHandler = new ButtonHandler();
		openButton.setOnAction(fileHandler);
		saveButton.setOnAction(fileHandler);
	}

	/**
	 * ButtonHandler performs an action when a button is clicked
	 */
	private class ButtonHandler implements EventHandler<ActionEvent>
	{
		/**
		 * handle performs the action in response to the button being clicked
		 * @param event when the button is clicked
		 */
		public void handle(ActionEvent event)
		{
			if(event.getSource() == openButton)
			{
				formattedTextDisplay.clear();
				errorDisplay.clear();
				inputOutputError.setText("");
				executor.resetDefaults();

				String fileName = openFileField.getText();
				executor.textFormatter(fileName);
			}
			else if(event.getSource() == saveButton)
			{
				//Invalid Characters
				String invalidChars = "/\\?%*:|\"<>.";
				String fileSaveName = saveFileField.getText();
				//Validate Save Names
				if(fileSaveName.length() == 0) // No Text
				{
					inputOutputError.setText("Please input a name to save");
				}
				else
				{
					boolean invalid = false;
					char invalidChar = ' ';
					int index = 0;

					while(!invalid && index < invalidChars.length())
					{
						if(fileSaveName.contains("" + invalidChars.charAt(index)))
						{
							invalid = true;
							invalidChar = invalidChars.charAt(index);
						}
						index++;
					}

					if(invalid)
					{
						inputOutputError.setText("Error, invalid operation "
								+ "'" + invalidChar + "' not allowed in save name");
					}
					else
					{
						FileWriter writeFile;
						try 
						{
							writeFile = new FileWriter(saveFileField.getText() + ".txt");
							BufferedWriter fileWrite = new BufferedWriter(writeFile);

							String[] formattedTextLines = formattedTextDisplay.getText().split("\n");
							int navigatingIndex = 0;
							System.out.print(formattedTextLines.length);

							while(navigatingIndex < formattedTextLines.length)
							{
								fileWrite.write(formattedTextLines[navigatingIndex]);
								fileWrite.write("\n");
								navigatingIndex++;
							}
							fileWrite.close();
							inputOutputError.setText("Successfully Saved!");
						}
						catch (IOException exception)
						{
							exception.printStackTrace();
						}
					}	
				}
			}
		}
	}

	/**
	 * updateTextDisplay adds the formatted text to the text display
	 * @param formattedText the formatted text to display
	 */
	public void updateTextDisplay(String formattedText)
	{
		formattedTextDisplay.appendText(formattedText);
	}

	/**
	 * getTextAreaWidth gets the text area width
	 * @return int: the text area width
	 */
	public int getTextAreaWidth()
	{
		return TEXT_AREA_WIDTH;
	}

	/**
	 * getTextAreaHeight gets the text area height
	 * @return int: the text area height
	 */
	public int getTextAreaHeight()
	{
		return TEXT_AREA_HEIGHT;
	}

	/**
	 * updateInputErrorDisp displays an error when an invalid file name is entered
	 * when opening and saving a file
	 * @param errorMessage the error message to display
	 */
	public void updateInputErrorDisp(String errorMessage)
	{
		inputOutputError.setText(errorMessage);
	}

	/**
	 * updateErrorDisplay adds the generated error to the error display
	 * @param errorMessage the error message to display
	 */
	public void updateErrorDisplay(String errorMessage)
	{
		errorDisplay.appendText(errorMessage);
	}
}