package application;

import java.io.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Orientation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent; 	

import java.util.ArrayList;

public class DisplayGui extends VBox
{
   private final int DISPLAY_TEXT_AREA_WIDTH = 700;
   private final int ERROR_TEXT_AREA_WIDTH = 400;
   private final int TEXT_AREA_HEIGHT = 600;
   private final int ADJUSTMENT_LABEL_SPACE = 20;
   
   private Button openButton, saveButton;
   private TextArea formattedTextDisplay,errorDisplay;
   private TextField openFileField, saveFileField;
   private Label inputOutputError, displayTextLabel,errorLabel;
   private TextALot executor;
   
   
   public DisplayGui()
   {
	  int adjustmentSpace = 10;
	  
      //Step #1: initialize instance variable and set up layout
	  executor = new TextALot(this);
	  
      openButton = new Button("OPEN");
      saveButton = new Button("SAVE");
      openButton.setMinWidth(80.0);
      saveButton.setMinWidth(80.0);
      
      formattedTextDisplay = new TextArea("");
      formattedTextDisplay.setEditable(false);
      formattedTextDisplay.setStyle("-fx-font-family: monospace");
      formattedTextDisplay.setPrefSize(DISPLAY_TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT);
      
      errorDisplay = new TextArea("");
      errorDisplay.setEditable(false);
      errorDisplay.setStyle("-fx-font-family: monospace");
      errorDisplay.setPrefSize(ERROR_TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT);
      
      openFileField = new TextField("");
      saveFileField = new TextField("");
      
      //Initialize Labels
      inputOutputError = new Label("");
      inputOutputError.setTextFill(Color.RED);
      
      displayTextLabel = new Label("Display");
      errorLabel = new Label("Errors");
      displayTextLabel.setPadding(new Insets(0,0,0,(DISPLAY_TEXT_AREA_WIDTH - displayTextLabel.getText().length())
    		  /2 - ADJUSTMENT_LABEL_SPACE));
      errorLabel.setPadding(new Insets(0,0,0,(ERROR_TEXT_AREA_WIDTH - displayTextLabel.getText().length())
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
      inputPane.setPadding(new Insets(0,0,0,(DISPLAY_TEXT_AREA_WIDTH + ERROR_TEXT_AREA_WIDTH) / 2
    		  - 150));
      
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
   
    //Step #2(B)- A handler class used to handle events opening file
    private class ButtonHandler implements EventHandler<ActionEvent>
    {
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
    }//end ButtonHandler

   public void updateTextDisplay(String formattedText)
   {
	   formattedTextDisplay.appendText(formattedText);
   }
   public int getDisplayWidth()
   {
	   return DISPLAY_TEXT_AREA_WIDTH + ERROR_TEXT_AREA_WIDTH;
   }
   public int getTextAreaHeight()
   {
	   return TEXT_AREA_HEIGHT;
   }
   public void updateInputErrorDisp(String errorMessage)
   {
	   inputOutputError.setText(errorMessage);
   }
   public void updateErrorDisplay(String errorMessage)
   {
	   errorDisplay.appendText(errorMessage);
   }
}