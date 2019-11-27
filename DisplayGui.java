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
   private final int TEXT_AREA_WIDTH = 600;
   private final int TEXT_AREA_HEIGHT = 600;
   private final int ADJUSTMENT_LABEL_SPACE = 20;
   
   private Button openButton, saveButton;
   private TextArea formattedTextDisplay,errorDisplay;
   private TextField openFileField, saveFileField;
   private Label inputOutputError, displayTextLabel,errorLabel;
   private ColumnTester executor;
   
   private ComboBox<String> colorCombo, widthCombo;
   private ArrayList<Line> lineList;
   private Pane canvas;
   private Line currentLine; //stores value of current line being drawn
   private Color lineColor;
   private int lineWidth;
   
   public DisplayGui()
   {
	  int adjustmentSpace = 10;
	  
      //Step #1: initialize instance variable and set up layout
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
      
      /**
      BorderPane displayTextPane = new BorderPane();
      displayTextPane.setCenter(displayTextLabel);
      displayTextPane.setBottom(formattedTextDisplay);
      
      BorderPane errorPane = new BorderPane();
      errorPane.setCenter(errorLabel);
      errorPane.setBottom(errorDisplay);
      */
      
      HBox displayPane = new HBox();
      displayPane.getChildren().addAll(displayTextPane,errorPane);
      
      this.getChildren().addAll(inputPane,displayPane);
      this.setPrefSize(1000, 600);
      /**
      lineColor = Color.BLACK; //default color
      lineWidth = 1; //default line width

      //Stores Strings of colors for the lines  
      ArrayList<String> colors = new ArrayList<String>();
       colors.add("Black");
       colors.add("Blue");
       colors.add("Red");
       colors.add("Yellow");
       colors.add("Green");
       
       ObservableList<String> obsColors = FXCollections.observableArrayList(colors);
       colorCombo = new ComboBox<String>(obsColors); //instantiates combo box of color selections
       colorCombo.getSelectionModel().selectFirst(); //default selects first (black)
       
       ArrayList<String> widthSize = new ArrayList<>();
       
       //Adds odd integers from 1 - 7 to widthSize
       for(int i = 1; i <= 7; i += 2)
       {
    	   widthSize.add(i + "");
       }
       ObservableList<String> obsSize = FXCollections.observableArrayList(widthSize);
       widthCombo = new ComboBox<String>(obsSize);
       widthCombo.getSelectionModel().selectFirst(); //defaults to width 1
       
       //Used to track lines created
       lineList = new ArrayList<Line>();
       
      //topPane should contain two combo boxes and two buttons
      HBox topPane = new HBox();
      topPane.setSpacing(40);
      topPane.setPadding(new Insets(10, 10, 10, 10));
      topPane.setStyle("-fx-border-color: black");
      topPane.getChildren().addAll(colorCombo,widthCombo,openButton,saveButton);
       
       //canvas is a Pane where we will draw lines
      canvas = new Pane();
      canvas.setStyle("-fx-background-color: white;");

       
      //add the canvas at the center of the pane and top panel
      //should have two combo boxes and two buttons
      this.setCenter(canvas);
      this.setTop(topPane);

       
      //Step #3: Register the source nodes with its handler objects

      colorCombo.setOnAction(new ColorHandler());
      widthCombo.setOnAction(new WidthHandler());
      
      ButtonHandler remover = new ButtonHandler();
      
      openButton.setOnAction(remover);
      saveButton.setOnAction(remover);
      
      MouseHandler mouseHandle = new MouseHandler();
      
      canvas.setOnMousePressed(mouseHandle);
      canvas.setOnMouseDragged(mouseHandle);
      canvas.setOnMouseReleased(mouseHandle);
      */
      ButtonHandler fileHandler = new ButtonHandler();
      openButton.setOnAction(fileHandler);
      saveButton.setOnAction(fileHandler);
   }



    //Step #2(A) - MouseHandler
   /**
    private class MouseHandler implements EventHandler<MouseEvent>
    {
        public void handle(MouseEvent event)
        {
        	if(event.getEventType() == MouseEvent.MOUSE_PRESSED)
        	{
                double startX = event.getX();
                double startY = event.getY();
                
              //creates new line and formats color and width
                currentLine = new Line(startX,startY,startX,startY); 
                currentLine.setStroke(lineColor);
                currentLine.setStrokeWidth(lineWidth);
                canvas.getChildren().add(currentLine);
        	}
        	else if(event.getEventType() == MouseEvent.MOUSE_DRAGGED)
        	{
        		double x1 = event.getX();
        		double x2 = event.getY();
        		
        		currentLine.setEndX(x1);
        		currentLine.setEndY(x2);
        	}
        	else if(event.getEventType() == MouseEvent.MOUSE_RELEASED)
        	{
        		double x1 = event.getX();
        		double x2 = event.getY();
        		
        		currentLine.setEndX(x1);
        		currentLine.setEndY(x2);
        		lineList.add(currentLine); //adds currentLine to list
        	}
 
        }//end handle()
    }//end MouseHandler
	*/
   
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


   /*
   //Step #2(C)- A handler class used to handle colors
   private class ColorHandler implements EventHandler<ActionEvent>
   {
       public void handle(ActionEvent event)
       {
    	   switch(colorCombo.getSelectionModel().getSelectedItem())
    	   {
    	   case "Black":
    		   lineColor = Color.BLACK;
    		   break;
    	   case "Blue":
    		   lineColor = Color.BLUE;
    		   break;
    	   case "Red":
    		   lineColor = Color.RED;
    		   break;
    	   case "Yellow":
    		   lineColor = Color.YELLOW;
    		   break;
    	   case "Green":
    		   lineColor = Color.GREEN;
    		   break;
    	   default:
    			   lineColor = Color.BLACK;
    	   }
       }
   }//end ColorHandler
    
    //Step #2(D)- A handler class used to handle widths of lines
    private class WidthHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent event)
        {
        switch(widthCombo.getSelectionModel().getSelectedItem())
        {
        case "1":
        	lineWidth = 1;
        	break;
        case "3":
        	lineWidth = 3;
        	break;
        case "5":
        	lineWidth = 5;
        	break;
        case "7":
        	lineWidth = 7;
        	break;
        default:
        	lineWidth = 1;
        }
            
        }
    }//end WidthHandler
    */
   public void updateTextDisplay(String formattedText)
   {
	   formattedTextDisplay.appendText(formattedText);
   }
   public int getTextAreaWidth()
   {
	   return TEXT_AREA_WIDTH;
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
}//end class DrawingPane