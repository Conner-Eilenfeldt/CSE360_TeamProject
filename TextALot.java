package application;

import java.io.*;
import java.util.ArrayList;

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
 * 
 */
public class TextALot 
{
	final int MAX_LINE_LENGTH = 95;
	final int MAX_COLUMN_LENGTH = 35;

	/**		Error Messages		**/
	final String COMMAND_ERROR_MESSAGE = "Error invalid command or entered command incorrectly";
	final String INVALID_NUMERICAL_MESSAGE = "Error invalid numerical value";

	int lineLength = 80;
	int justification = 0; // 0 = left, 1 = center, 2 = right
	int paragraph = 0; // number of spaces for new paragraph
	boolean equalSpacing = false; // true = equal spacing
	boolean wrapping = false; // true = wrapped text
	boolean column = false; // true = 2 columns
	boolean spacing = false; //false = single spacing true = double spacing
	ColumnLinkedList columnList;
	WrapQueue wrapQueue = new WrapQueue();
	DisplayGui dispGui;

	/**
	 * TextALot constructor
	 */
	public TextALot()
	{
		dispGui = null;
		columnList = new ColumnLinkedList();
	}
  
	/**
	 * TextALot constructor
	 * @param dispGui the GUI
	 */
	public TextALot(DisplayGui dispGui)
	{
		this.dispGui = dispGui;
		columnList = new ColumnLinkedList(dispGui);
	}
  
	/**
	 * resetDefaults sets all format settings to their defaults
	 */
	public void resetDefaults()
	{
		lineLength = 80;
		justification = 0; // 0 = left, 1 = center, 2 = right
		paragraph = 0; // number of spaces for new paragraph
		equalSpacing = false; // true = equal spacing
		wrapping = false; // true = wrapped text
		column = false; // true = 2 columns
		spacing = false; //false = single spacing true = double spacing
		columnList.clear();
		wrapQueue.clear();
	}
  
	/**
	 * textFormatter parses the input file and runs the corresponding commands
	 * @param fileName the input file containing commands and the text to format
	 */
	public void textFormatter(String fileName)
	{
		BufferedReader reader = null;

		try 
		{
			File file = new File(fileName);

			reader = new BufferedReader(new FileReader(file));
			String text = null;
			text = reader.readLine();

			while (text != null) 
			{
				if (text.length() > 0 && text.charAt(0) == '-')
				{
          if(paragraph > 0) {
						dispGui.updateErrorDisplay("Error conflicting commands\n");
						paragraph = 0;
					}						
					else {	
            switch(text.charAt(1))
            {
            case 'n':
              try
              {
                int number = Integer.parseInt(text.substring(2));
                if(number <= 0)
                {
                  dispGui.updateErrorDisplay("Error Line Length can not be negative or zero\n");
                }
                else if(number > MAX_LINE_LENGTH)
                {
                  dispGui.updateErrorDisplay("Error new line length exceeds max line length\n");
                }
                else if(column)
                {
                  dispGui.updateErrorDisplay("Error can not modify line length while column is active\n");
                }
                else
                {
                  lineLength = number;
                }
              }
              catch(NumberFormatException exception) 
              {
                dispGui.updateErrorDisplay(INVALID_NUMERICAL_MESSAGE + "\n");
              }
              break;
            case 'l':
              if(text.equals("-l"))
                justification = 0;
              else
                dispGui.updateErrorDisplay(COMMAND_ERROR_MESSAGE + "\n");
              break;
            case 'c':
              if(text.equals("-c"))
                justification = 1;
              else
                dispGui.updateErrorDisplay(COMMAND_ERROR_MESSAGE + "\n");
              break;
            case 'r':
              if(text.equals("-r"))
                justification = 2;
              else
                dispGui.updateErrorDisplay(COMMAND_ERROR_MESSAGE + "\n");
              break;
            case 'e':
              if(text.equals("-e"))
                equalSpacing = !equalSpacing;
              else
                dispGui.updateErrorDisplay(COMMAND_ERROR_MESSAGE + "\n");
              break;
            case 'w':
              if(text.equals("-w+"))
                wrapping = true;
              else if(text.equals("-w-"))
              {
                wrapping = false;
                if(column)
                  addColumnWrap();
                else
                  printWrap();
              }
              else
              {
                dispGui.updateErrorDisplay(COMMAND_ERROR_MESSAGE + "\n");
              }
              break;					
            case 'p':
              try
              {
                int number = Integer.parseInt(text.substring(2));
                if(number >= lineLength)
                {
                  String errorMessage = "Error paragraph exceeds line length";
                  dispGui.updateErrorDisplay(errorMessage + "\n");
                }
                else if(number <= 0)
                {
                  dispGui.updateErrorDisplay("Error Paragraph cannot be zero or less\n");
                }
                else
                  paragraph = number;
              }
              catch(NumberFormatException exception)
              {
                dispGui.updateErrorDisplay(INVALID_NUMERICAL_MESSAGE + "\n");
              }
              break;
            case 'b':
              try
              {
                int number = Integer.parseInt(text.substring(2));
                if(number <= 0)
                {
                  dispGui.updateErrorDisplay("Error Number of Blank Lines can not be less than or equal to zero\n");
                }
                else
                {
                  if(wrapping)
                  {
                    if(column)
                      addColumnWrap();
                    else
                      printWrap();
                  }
                  for(int i = 0; i < number; i++)
                  {
                    if(column)
                      formatColumnInput("");
                    else
                      dispGui.updateTextDisplay("\n");
                  }
                }	
              }
              catch(NumberFormatException exception)
              {
                dispGui.updateErrorDisplay(INVALID_NUMERICAL_MESSAGE + "\n");
              }
              break;
            case 't':
              text = reader.readLine();
              if(text != null)
              {
                if(text.charAt(0) == '-') 
                {	
						dispGui.updateErrorDisplay("Error conflicting commands\n");
				}	
				else 
				{	
                  int prevJustification = justification;
                  justification = 1;
                  if(column)
                  {
                    if(text.length() > MAX_COLUMN_LENGTH)
                    {
                      String errorExceeds = "Error title exceeds maximum column "
                          + "length of " + MAX_COLUMN_LENGTH;
                      dispGui.updateErrorDisplay(errorExceeds + "\n");
                    }
                    else
                    {
                      String titleBars = "";
                      for(int i = 0; i < text.length(); i++)
                        titleBars += "-";
                      if(wrapping)
                        addColumnWrap();
                      formatColumnInput(text);
                      formatColumnInput(titleBars);
                    }
                  }
                  else
                  {
                    if(text.length() > lineLength)
                    {
                      String errorExceeds = "Error title exceeds maximum length of " + lineLength;
                      dispGui.updateErrorDisplay(errorExceeds + "\n");
                    }
                    else
                    {
                      String titleBars = "";
                      for(int i = 0; i < text.length(); i++)
                        titleBars += "-";
                      text = formatInput(text);
                      titleBars = formatInput(titleBars);

                      if(wrapping)
                        printWrap();
                      dispGui.updateTextDisplay(text + "\n" + titleBars + "\n");
                    }
                  }
                  justification = prevJustification;
                }
              }
              break;
            case 's':
              if(text.equals("-s"))
                spacing = false;
              else
                dispGui.updateErrorDisplay(COMMAND_ERROR_MESSAGE + "\n");
              break;
            case 'd':
              if(text.equals("-d"))
                spacing = true;
              else
                dispGui.updateErrorDisplay(COMMAND_ERROR_MESSAGE + "\n");
              break;
            case 'a':
              try
              {
                int number = Integer.parseInt(text.substring(2));
                if(number == 1)
                {
                  //If columns are turned off, remaining
                  //In wrap must be added at the end
                  if(wrapping && column)
                  {
                    addColumnWrap();
                  }
                  column = false;
                  columnList.printColumns();
                  columnList.clear();		
                }
                else if(number == 2)
                {
                  column = true;
                  lineLength = 80;
                  //Once columns are turned on wrapping must print
                  if(wrapping)
                  {
                    printWrap();
                  }
                }
                else
                {
                  dispGui.updateErrorDisplay(COMMAND_ERROR_MESSAGE + "\n");
                }
              }
              catch(NumberFormatException e)  // change e
              {
                dispGui.updateErrorDisplay(INVALID_NUMERICAL_MESSAGE + "\n");
              }
              break;
            default:
              dispGui.updateErrorDisplay(COMMAND_ERROR_MESSAGE + "\n");
            }
          }
				}
				else
				{
					String output = formatInput(text);

					if(wrapping)
					{
						if (column)
							formatWrapColumnInput(text); //Modify to have column for wrapping
						else
							formatWrapInput(text);
					}
					else
					{
						if (column)
							formatColumnInput(text);
						else
						{
							dispGui.updateTextDisplay(output + "\n");
						}
					}
				}
				text = reader.readLine();
			}
			if(wrapping)
			{
				if(column)
					addColumnWrap();
				else
					printWrap();
			}
			if(column)
			{
				columnList.printColumns();
				columnList.clear();
			}
		}
		catch (FileNotFoundException e) 
		{
			dispGui.updateInputErrorDisp("Error, File Not Found");
		}
		catch (IOException e) 
		{
			dispGui.updateInputErrorDisp("Input/Output Error. Please try again");
		}
		finally 
		{
			try 
			{
				if (reader != null) 
				{
					reader.close();
				}
			} 
			catch (IOException e) 
			{
				dispGui.updateInputErrorDisp("Input/Output Error. Please try again");
			}
		}
	}
  
	/**
	 * leftAlign left aligns input text
	 * @param inputText the input text to format
	 * @param lineLimit the current line length
	 * @return String: the formatted text
	 */
	private String leftAlign(String inputText, int lineLimit)
	{
		String alignedText = inputText;
		int adjustmentSpace = lineLimit - inputText.length();

		for(int i = 0; i < adjustmentSpace; i++)
		{
			alignedText += " ";
		}
		return alignedText;
	}
  
	/**
	 * rightAlign right aligns input text
	 * @param inputText the input text to format
	 * @param lineLimit the current line length
	 * @return String: the formatted text
	 */
	private String rightAlign(String inputText, int lineLimit)
	{
		String alignedText = "";
		int adjustmentSpace = lineLimit - inputText.length();

		for(int i = 0; i < adjustmentSpace; i++)
		{
			alignedText += " ";
		}
		alignedText += inputText;
		return alignedText;
	}
  
	/**
	 * centerAlign center aligns input text
	 * @param inputText the input text to format
	 * @param lineLimit the current line length
	 * @return String: the formatted text
	 */
	private String centerAlign(String inputText, int lineLimit)
	{
		String spacing = "";
		String alignedText = "";

		int adjustmentSpace = lineLimit - inputText.length();

		for(int i = 0; i < adjustmentSpace / 2; i++)
		{
			spacing += " ";
		}

		alignedText = spacing + inputText;

		if(adjustmentSpace % 2 != 0)
			alignedText += spacing + " ";
		else
			alignedText += spacing;
		return alignedText;
	}
  
  /**
   * Equally spaces add spaces between the words equally
	 * @param UserText the input text to format
	 * @param UserWidth the current line length
	 * @return String: the formatted text 
   */
	private String equallySpaced(String UserText, int UserWidth)
	{
    /*
		 * Dynamic array to store user text
		 * Array type: string
		 */
		ArrayList < String > lines = new ArrayList < String > ();
		
		//Replaces all duplicate spaces with single space to be split
		UserText = UserText.replaceAll("\\s+", " ");
		/*
		 * Splits user text 
		 * Store in string array
		 */
		String[] words = UserText.split(" ");
		
		/*
		 * 
		 * presentLine is type String to keep track of the current line
		 * presentWord is type int to keep track of the current word
		 * tempLength stores word length
		 * wordPresentOnThisLine shows the number of words on that line
		 * 
		 */
		String presentLine = "";
		int presentWord = 0;
		int tempLength = words.length;
		int totalWordLength = 0;
		int wordsPresentOnThisLine = 0;
		
		for(int i = 0; i < tempLength; i++)
		{
			totalWordLength += words[i].length();
		}
		int leftoverSpace = UserWidth - totalWordLength;
		if(tempLength > 1)
		{
			//Add spaces
			String spaces = "";
			
			int numSpaces = leftoverSpace / (tempLength - 1);
			int remainingSpace = leftoverSpace % (tempLength - 1);
			
			for(int i = 0; i < numSpaces; i++)
			{
				spaces += " ";
			}
			while (presentWord < tempLength - 1) 
			{
				presentLine = "";
				/*
				 * if present word's length and remaining words left on the line is less than 
				 * line length than add the word on that line
				 */
				if ((presentLine.length() + words[presentWord].length()) <= UserWidth || (presentLine.length() + words[presentWord].length()) > UserWidth && wordsPresentOnThisLine == 0)
				{				
					// adds word on that line with spacing
					presentLine = words[presentWord] + spaces;
					if(remainingSpace > 0)
					{
						presentLine += " ";
						remainingSpace--;
					}
					presentWord++;
					wordsPresentOnThisLine++;
					lines.add(presentLine);
				} 
				else 
				{
					//it will add it truncate it and add it to next line
					presentLine = joinLineWithSpaces(presentLine.trim(), UserWidth - (presentLine.trim().length()));
					lines.add(presentLine.trim());
					presentLine = "";
					wordsPresentOnThisLine = 0;
				}	
			}
			lines.add(words[presentWord]);
		/*
		 *  if word is less than length than wont add it on the that line
		 */
		
		}
		else if(tempLength > 0)
		{
			//Add single word
			lines.add(words[presentWord]);
		}
		
		//Combining all equally spaced words to be returned
		int listLength = lines.size();
		String equalSpacedLines = "";
		for(int index = 0; index < listLength; index++)
		{
			equalSpacedLines += lines.get(index);
		}
		return equalSpacedLines;
	}
	public static String joinLineWithSpaces(String userText, int numberSpaces)
	{
		String newText = "";
		
		if (numberSpaces == 0) return userText;
		else if (numberSpaces == 1) return userText + " ";
		else if (numberSpaces > 1) {
			
			//split each word
			String[] words = userText.split(" ");
			int numberOfWords = words.length;

			int leftText = (numberSpaces + (numberOfWords - 1));
			int rightText = ((words.length - 1));
			
			/*
			 * add more spaces to fill the remaining line length
			 */
			
			int SpacesToAddEachWord = (int)((double) leftText / (double) rightText);

			for (int x = 0; x < numberOfWords; x++) {
				
				if (x == numberOfWords) newText = newText + words[x];
				else newText = newText + words[x] + getSpaces(SpacesToAddEachWord);
				
			}
		} else return userText;
		return newText;
	}
	
	// create spaces between words
	
	public static String getSpaces(int spaces) 
	{
		
		String helperText = "";
		
		for (int x = 0; x < spaces; x++) {
			helperText += " ";
		}
		return helperText;
	}

	/**
	 * addColumnWrap adds what is in the wrapQueue to the columnLinkedList
	 */
	private void addColumnWrap()
	{
		int tempJustification = justification;
		boolean tempEqualSpace = equalSpacing;
		boolean tempSpacing = spacing;
		int tempLineLimit = lineLength;

		while(!wrapQueue.isEmpty())
		{
			WrapInformation wrapInfo = wrapQueue.removeFromHead();
			justification = wrapInfo.justification;
			equalSpacing = wrapInfo.equalSpacing;
			spacing = wrapInfo.spacing;
			lineLength = wrapInfo.lineLength;

			formatColumnInput(wrapInfo.storedText);
		}
		wrapQueue.clear();

		//Restore defaults
		justification = tempJustification;
		equalSpacing = tempEqualSpace;
		spacing = tempSpacing;
		lineLength = tempLineLimit;
	}
  
	/**
	 * printWrap formats the input text while wrapping
	 */
	private void printWrap()
	{
		int tempJustification = justification;
		boolean tempEqualSpace = equalSpacing;
		boolean tempSpacing = spacing;
		int tempLineLimit = lineLength;

		while(!wrapQueue.isEmpty())
		{
			WrapInformation wrapInfo = wrapQueue.removeFromHead();
			justification = wrapInfo.justification;
			equalSpacing = wrapInfo.equalSpacing;
			spacing = wrapInfo.spacing;
			lineLength = wrapInfo.lineLength;

			dispGui.updateTextDisplay(formatInput(wrapInfo.storedText) + "\n");
		}
		wrapQueue.clear();

		//Restore defaults
		justification = tempJustification;
		equalSpacing = tempEqualSpace;
		spacing = tempSpacing;
		lineLength = tempLineLimit;
	}
  
	/**
	 * formatWrapColumnInput formats the input text while wrapping in two columns
	 * @param inputText the input text to format
	 */
	private void formatWrapColumnInput(String inputText)
	{
		int wrappingLimit = MAX_COLUMN_LENGTH;

		//Paragraph Exceeds 0
		if(paragraph > 0)
		{
			for(int i = 0; i < paragraph;i++)
			{
				inputText = " " + inputText;
			}
			paragraph = 0; //Reset paragraph to 0
		}
		boolean exceedsLimit = inputText.length() > MAX_COLUMN_LENGTH;

		if(wrapQueue.isEmpty())
		{
			if(exceedsLimit)
			{
				char succeedingCharacter = inputText.charAt(MAX_COLUMN_LENGTH);
				int index = MAX_COLUMN_LENGTH;

				//A word needs to be cut off
				if(succeedingCharacter != ' ')
				{
					while(index > 0 && inputText.charAt(index) != ' ')
					{
						index--;
					}
					//Index is now space between words and is cutoff
					//Ensures to recursively execute only once as substring
					//Will be less than maximum line length
					formatColumnInput(inputText.substring(0, index));
					formatWrapColumnInput(inputText.substring(index + 1));
				}
				//EDITED TO NOT INCLUDE SPACES
				//Designed so if spaces then the spaces are NOT included in following
				else
				{
					formatColumnInput(inputText.substring(0,index));
					while(index < inputText.length() && inputText.charAt(index) == ' ')
					{
						index++;
					}
					if(index < inputText.length())
					{
						formatWrapColumnInput(inputText.substring(index));
					}
				}
			}
			else
			{
				boolean wordFound = false;
				int index = 0;
				while(!wordFound && index < inputText.length())
				{
					if(inputText.charAt(index) != ' ' && inputText.charAt(index) != '\n')
					{
						wordFound = true;
					}
				}
				if(wordFound)
					wrapQueue.insertText(inputText, justification, equalSpacing, spacing,lineLength);
				else
				{
					formatColumnInput(inputText);
				}
			}
		}
		else
		{
			boolean wordFound = false;
			boolean wordEnd = false;

			int index = 0;
			int startWordIndex = 0;
			int endWordIndex = 0;
			while(!wordEnd && index < inputText.length())
			{
				if(inputText.charAt(index) != ' ' && inputText.charAt(index) != '\n')
				{
					if(!wordFound)
						startWordIndex = index;
					wordFound = true;
				}
				else
				{
					if(wordFound)
					{
						wordEnd = true;
						endWordIndex = index - 1;
					}
				}
				index++;
			}
			if(wordFound && index == inputText.length())
			{
				wordEnd = true;
				endWordIndex = index - 1;
			}

			if(wordEnd) //Word exists and can be wrapped
			{
				int wordLength = endWordIndex - startWordIndex + 1;
				int lastCharacterIndex = wrapQueue.lastCharacterIndex();
				int wrappedStringLength = lastCharacterIndex + 1 + wordLength;
				//WRAPPABLE
				if(wrappedStringLength < wrappingLimit) //At least one word can be wrapped
				{
					int remainingSpace = wrappingLimit - wrapQueue.lastCharacterIndex() - 2;
					int endWrapIndex = startWordIndex;

					while(endWrapIndex < inputText.length() && remainingSpace > 0)
					{
						endWrapIndex++;
						remainingSpace--;
					}
					if(remainingSpace > 0) //More than needed
					{
						//insert substring from startWordIndex and also (0, lastCharacterIndex) of in queue
						String wrappedText = inputText.substring(startWordIndex);
						String queueWrap = wrapQueue.getQueueText().substring(0,lastCharacterIndex + 1);

						wrapQueue.updateQueue(queueWrap + " " + wrappedText);
					}
					else //Used up all space
					{
						if(endWrapIndex < inputText.length()) //Space ran out before word ran out
						{
							if(inputText.charAt(endWrapIndex) == ' ')
							{
								//Insert into queue directly
								String wrappedText = inputText.substring(startWordIndex,endWrapIndex).trim();
								String queueWrap = wrapQueue.getQueueText().substring(0,lastCharacterIndex + 1);

								wrapQueue.updateQueue(queueWrap + " " + wrappedText);

								while(endWrapIndex < inputText.length() && inputText.charAt(endWrapIndex) == ' ')
								{
									endWrapIndex++;
								}
								//If there are words remaining to be wrapped, otherwise
								if(endWrapIndex < inputText.length())
								{
									//Designed so if there is space between words and cannot be wrapped
									//The word is then sent to possibly be wrapped
									//E.g.
									//Word Limit = 20
									//First Line
									//Wrapped Up                         also up
									//Output:	First Line Wrapped Up also up

									formatWrapColumnInput(inputText.substring(endWrapIndex));
								}
							}	
							else
							{
								//find proper end index then insert into queue
								//Recursively call with remainder
								int wrapIndex = endWrapIndex;
								while(wrapIndex >= 0 && inputText.charAt(wrapIndex) != ' ')
								{
									wrapIndex--;
								}
								//Insert remaining portion into queue
								String wrappedText = inputText.substring(startWordIndex,wrapIndex);
								String queueWrap = wrapQueue.getQueueText().substring(0,lastCharacterIndex + 1);

								wrapQueue.updateQueue(queueWrap + " " + wrappedText);

								formatWrapColumnInput(inputText.substring(wrapIndex + 1));
							}		
						}
						else
						{
							String wrappedText = inputText.substring(startWordIndex);
							String queueWrap = wrapQueue.getQueueText().substring(0,lastCharacterIndex + 1);

							wrapQueue.updateQueue(queueWrap + " " + wrappedText);
						}
					}
				}
				else
				{
					addColumnWrap();
					formatWrapColumnInput(inputText);
				}
			}
			else
			{
				addColumnWrap();
				formatColumnInput(inputText);
			}
		}
	}
  
  /**
   * formatWrapInput applies wrap formatting to text line
   * @param inputText line text to be formatted
   */
	private void formatWrapInput(String inputText)
	{
		int wrappingLimit = wrapQueue.getLineLength();
		//Paragraph Exceeds 0
		if(paragraph > 0)
		{
			for(int i = 0; i < paragraph;i++)
			{
				inputText = " " + inputText;
			}
			paragraph = 0; //Reset paragraph to 0
		}
		boolean exceedsLimit = inputText.length() > lineLength;

		if(wrapQueue.isEmpty())
		{
			if(exceedsLimit)
			{
				char succeedingCharacter = inputText.charAt(lineLength);
				int index = lineLength;

				//A word needs to be cut off
				if(succeedingCharacter != ' ')
				{
					while(index > 0 && inputText.charAt(index) != ' ')
					{
						index--;
					}
					//Index is now space between words and is cutoff
					//Ensures to recursively execute only once as substring
					//Will be less than maximum line length
					String formattedSubStr = formatInput(inputText.substring(0, index));
					dispGui.updateTextDisplay(formattedSubStr +  "\n"); 
					formatWrapInput(inputText.substring(index + 1));
				}
				//EDITED TO NOT INCLUDE SPACES
				//Designed so if spaces then the spaces are NOT included in following 
				else
				{
					String formattedSubStr = formatInput(inputText.substring(0,index));
					dispGui.updateTextDisplay(formattedSubStr + "\n");
					while(index < inputText.length() && inputText.charAt(index) == ' ')
					{
						index++;
					}
					if(index < inputText.length())
					{	
						formatWrapInput(inputText.substring(index));
					}			
				}
			}
			else //MAKE SURE TO ACCOUNT FOR BLANK LINES
			{
				boolean wordFound = false;
				int index = 0;
				while(!wordFound && index < inputText.length())
				{
					if(inputText.charAt(index) != ' ' && inputText.charAt(index) != '\n')
					{
						wordFound = true;
					}
				}
				if(wordFound)
					wrapQueue.insertText(inputText, justification, equalSpacing, spacing,lineLength);
				else
				{
					String formattedText = formatInput(inputText);
					dispGui.updateTextDisplay(formattedText + "\n");
				}
			}
		}
		else
		{
			boolean wordFound = false;
			boolean wordEnd = false;

			int index = 0;
			int startWordIndex = 0;
			int endWordIndex = 0;
			while(!wordEnd && index < inputText.length())
			{
				if(inputText.charAt(index) != ' ' && inputText.charAt(index) != '\n')
				{
					if(!wordFound)
						startWordIndex = index;
					wordFound = true;
				}
				else
				{
					if(wordFound)
					{
						wordEnd = true;
						endWordIndex = index - 1;
					}
				}
				index++;
			}
			if(wordFound && index == inputText.length())
			{
				wordEnd = true;
				endWordIndex = index - 1;
			}

			if(wordEnd) //Word exists and can be wrapped
			{
				int wordLength = endWordIndex - startWordIndex + 1;
				int lastCharacterIndex = wrapQueue.lastCharacterIndex();
				int wrappedStringLength = lastCharacterIndex + 1 + wordLength;
				//WRAPPABLE
				if(wrappedStringLength < wrappingLimit) //At least one word can be wrapped
				{
					int remainingSpace = wrappingLimit - wrapQueue.lastCharacterIndex() - 2;
					int endWrapIndex = startWordIndex;

					while(endWrapIndex < inputText.length() && remainingSpace > 0)
					{
						endWrapIndex++;
						remainingSpace--;
					}
					if(remainingSpace > 0) //More than needed
					{
						//insert substring from startWordIndex and also (0, lastCharacterIndex) of in queue
						String wrappedText = inputText.substring(startWordIndex);
						String queueWrap = wrapQueue.getQueueText().substring(0,lastCharacterIndex + 1);

						wrapQueue.updateQueue(queueWrap + " " + wrappedText);
					}
					else //Used up all space
					{
						if(endWrapIndex < inputText.length()) //Space ran out before word ran out
						{
							if(inputText.charAt(endWrapIndex) == ' ')
							{
								//Insert into queue directly
								String wrappedText = inputText.substring(startWordIndex,endWrapIndex).trim();
								String queueWrap = wrapQueue.getQueueText().substring(0,lastCharacterIndex + 1);
								
								wrapQueue.updateQueue(queueWrap + " " + wrappedText);

								while(endWrapIndex < inputText.length() && inputText.charAt(endWrapIndex) == ' ')
								{
									endWrapIndex++;
								}
								//If there are words remaining to be wrapped, otherwise
								if(endWrapIndex < inputText.length())
								{
									//Designed so if there is space between words and cannot be wrapped
									//The word is then sent to possibly be wrapped
									//E.g.
									//Word Limit = 20
									//First Line
									//Wrapped Up                         also up
									//Output:	First Line Wrapped Up also up

									formatWrapInput(inputText.substring(endWrapIndex));
								}
							}	
							else
							{
								//find proper end index then insert into queue
								//Recursively call with remainder
								int wrapIndex = endWrapIndex;
								while(wrapIndex >= 0 && inputText.charAt(wrapIndex) != ' ')
								{
									wrapIndex--;
								}
								//Insert remaining portion into queue
								String wrappedText = inputText.substring(startWordIndex,wrapIndex);
								String queueWrap = wrapQueue.getQueueText().substring(0,lastCharacterIndex + 1);
								
								wrapQueue.updateQueue(queueWrap + " " + wrappedText);

								formatWrapInput(inputText.substring(wrapIndex + 1));
							}		
						}
						else
						{
							String wrappedText = inputText.substring(startWordIndex);
							String queueWrap = wrapQueue.getQueueText().substring(0,lastCharacterIndex + 1);

							wrapQueue.updateQueue(queueWrap + " " + wrappedText);
						}
					}
				}
				else
				{
					printWrap();
					formatWrapInput(inputText);
				}
			}
			else
			{
				String formattedInput = formatInput(inputText);
				printWrap();
				dispGui.updateTextDisplay(formattedInput);
			}
		}
	}

/**
 * formatColumnInput applies column formatting to text line
 * @param inputText line text to be formatted
 */
	private void formatColumnInput(String inputText)
	{
		//Paragraph Exceeds 0
		if(paragraph > 0)
		{
			for(int i = 0; i < paragraph;i++)
			{
				inputText = " " + inputText;
			}
			paragraph = 0; //Reset paragraph to 0
		}
		boolean exceedsLimit = inputText.length() > MAX_COLUMN_LENGTH;

		if(exceedsLimit)
		{
			char succeedingCharacter = inputText.charAt(MAX_COLUMN_LENGTH);
			int index = MAX_COLUMN_LENGTH;
			//Word previously
			if(succeedingCharacter != ' ')
			{
				while(index > 0 && inputText.charAt(index) != ' ')
				{
					index--;
				}
				if(index <= 0)
				{
					dispGui.updateErrorDisplay("Error a single word can not exceed the line length: " + MAX_COLUMN_LENGTH + "\n");
				}
				else
				{
					//Index is now space between words and is cutoff
					//Ensures to recursively execute only once as substring
					//Will be less than maximum line length
					formatColumnInput(inputText.substring(0, index));
					formatColumnInput(inputText.substring(index + 1));
				}
			}
			//EDITED TO REMOVE SPACES
			//Designed so if spaces then the spaces are included in following 
			else
			{
				formatColumnInput(inputText.substring(0,index));
				//formatColumnInput(inputText.substring(index));

				while(index < inputText.length() && inputText.charAt(index) == ' ')
				{
					index++;
				}
				if(index < inputText.length())
				{
					formatColumnInput(inputText.substring(index));
				}
			}
		}
		else
		{
			switch (justification)
			{
			case 0:
				if(equalSpacing)
					inputText = equallySpaced(inputText,MAX_COLUMN_LENGTH);
				columnList.addTextLine(leftAlign(inputText,MAX_COLUMN_LENGTH));
				break;
			case 1:
				columnList.addTextLine(centerAlign(inputText,MAX_COLUMN_LENGTH));
				break;
			case 2:
				columnList.addTextLine(rightAlign(inputText,MAX_COLUMN_LENGTH));
				break;
			default:
				dispGui.updateErrorDisplay("Error invalid justification value\n");
			}
			if(spacing)
			{
				columnList.addTextLine(leftAlign("",MAX_COLUMN_LENGTH));
			}
		}
	}

/**
 * formatInput outputs a formatted version of text
 * @param inputText line text to be formatted
 * @return	formatted version of text
 */
	private String formatInput(String inputText)
	{
		//Paragraph Exceeds 0
		if(paragraph > 0)
		{
			for(int i = 0; i < paragraph;i++)
			{
				inputText = " " + inputText;
			}
			paragraph = 0; //Reset paragraph to 0
		}
		String formattedOutput = "";
		boolean exceedsLimit = inputText.length() > lineLength;

		if(exceedsLimit)
		{
			char succeedingCharacter = inputText.charAt(lineLength);
			int index = lineLength;
			//Word previously
			if(succeedingCharacter != ' ')
			{
				while(index > 0 && inputText.charAt(index) != ' ')
				{
					index--;
				}
				if(index <= 0)
				{
					dispGui.updateErrorDisplay("Error a single word can not exceed the line length: " + lineLength + "\n");
				}
				else
				{
					//Index is now space between words and is cutoff
					//Ensures to recursively execute only once as substring
					//Will be less than maximum line length
					formattedOutput += formatInput(inputText.substring(0, index));
					formattedOutput += "\n" + formatInput(inputText.substring(index + 1));
				}
			}
			//Designed so if spaces then the spaces are NOT included in following 
			else
			{
				//Guaranteed at least one space
				formattedOutput += formatInput(inputText.substring(0,index));
				while(index < inputText.length() && inputText.charAt(index) == ' ')
				{
					index++;
				}
				if(index < inputText.length())
				{
					formattedOutput += "\n" + formatInput(inputText.substring(index));
				}
				/*
				else
					formattedOutput+= "\n" + formatInput("");
				 */
			}
		}
		else
		{
			switch (justification)
			{
			case 0:
				if(equalSpacing)
					inputText = equallySpaced(inputText,lineLength);
				formattedOutput = leftAlign(inputText,lineLength);
				break;
			case 1:
				formattedOutput = centerAlign(inputText,lineLength);
				break;
			case 2:
				formattedOutput = rightAlign(inputText,lineLength);
				break;
			default:
				dispGui.updateErrorDisplay("Error invalid justification value\n");
			}
			if(spacing)
			{
				formattedOutput += "\n";
			}
		}	
		return formattedOutput;
	}

}
