package teamProject;
import java.io.*;

public class ColumnTester 
{
	final int MAX_LINE_LENGTH = 150;
	final int MAX_COLUMN_LENGTH = 35;

	int lineLength = 80;
	int justification = 0; // 0 = left, 1 = center, 2 = right
	int paragraph = 0; // number of spaces for new paragraph
	boolean equalSpacing = false; // true = equal spacing
	boolean wrapping = false; // true = wrapped text
	boolean column = false; // true = 2 columns
	boolean spacing = false; //false = single spacing true = double spacing
	ColumnLinkedList columnList = new ColumnLinkedList();
	WrapQueue wrapQueue = new WrapQueue();
	
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
					switch(text.charAt(1))
					{
					case 'n':
						try
						{
							int number = Integer.parseInt(text.substring(2));
							lineLength = number;
						}
						catch(Exception e)  // change e
						{
							System.out.print("error");
						}
						break;
					
					case 'l':
						justification = 0;
						break;
						
					case 'c':
						justification = 1;
						break;
						
					case 'r':
						justification = 2;
						break;
						
					case 'e':
						equalSpacing = !equalSpacing;
						break;
					case 'w':
						// wrapping method
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
							System.out.println("Error");
						break;					
					case 'p':
						try
						{
							int number = Integer.parseInt(text.substring(2));
							if(number >= lineLength)
							{
								System.out.println("Error");
							}
							else
								paragraph = number;
						}
						catch(Exception e)  // change e
						{
							System.out.print("error");
						}
						
						break;
					case 'b':
						try
						{
							int number = Integer.parseInt(text.substring(2));
							for(int i = 0; i < number; i++)
							{
								if(wrapping)
								{
									if(column)
										formatColumnInput("");
									else
										formatWrapInput("");
								}
								else
								{
									if(column)
										formatColumnInput("");
									else
										System.out.println();
								}
								
							}
						}
						catch(Exception e)  // change e
						{
							System.out.print("error");
						}
						
						break;
					//Error potentially with title and wrapping
					case 't':
						text = reader.readLine();
						if(text != null)
						{
							justification = 1; //Change to center allignment
							if(column)
							{
								if(text.length() > MAX_COLUMN_LENGTH)
									System.out.println("Error");
								else
								{
									String titleBars = "";
									for(int i = 0; i < text.length(); i++)
										titleBars += "-";
									formatColumnInput(text);
									formatColumnInput(titleBars);
								}
							}
							else
							{
								if(text.length() > lineLength)
									System.out.println("Error");
								else
								{
									String titleBars = "";
									for(int i = 0; i < text.length(); i++)
										titleBars += "-";
									text = formatInput(text);
									titleBars = formatInput(titleBars);
									
									if(column)
									{
										columnList.addTextLine(text);
										columnList.addTextLine(titleBars);
									}
									else
										System.out.println(text + "\n" + titleBars);
								}
							}
						}
						break;
					case 's':
						spacing = false;
						break;
					case 'd':
						spacing = true;
						break;
					case 'a':
						try
						{
							int number = Integer.parseInt(text.substring(2));
							if(number == 1)
							{
								//If columns are turned off, remaining
								//In wrap must be added at the end
								if(wrapping)
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
								//Once columns are turned on wrapping must print
								if(wrapping)
								{
									printWrap();
								}
							}
							else
								System.out.print("error");
						}
						catch(Exception e)  // change e
						{
							System.out.print("error");
						}
						break;
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
							System.out.println(output);
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
		    e.printStackTrace();
		}
		catch (IOException e) 
		{
		    e.printStackTrace();
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
		    	
		    }
		}
		/**
		colList.addTextLine("This is the start           ");
		colList.addTextLine("It continues afterwards here");
		colList.addTextLine("Then it continues to move   ");
		colList.addTextLine("Top of Column 2             ");
		colList.addTextLine("2nd row of column 2         ");
		colList.addTextLine("3rd row of column 2         ");
		
		colList.printColumns();
		*/
	}
	private String leftAllign(String inputText, int lineLimit)
	{
		String allignedText = inputText;
		int adjustmentSpace = lineLimit - inputText.length();
		
		for(int i = 0; i < adjustmentSpace; i++)
		{
			allignedText += " ";
		}
		return allignedText;
	}
	private String rightAllign(String inputText, int lineLimit)
	{
		String allignedText = "";
		int adjustmentSpace = lineLimit - inputText.length();
		
		for(int i = 0; i < adjustmentSpace; i++)
		{
			allignedText += " ";
		}
		allignedText += inputText;
		return allignedText;
	}
	private String center(String inputText, int lineLimit)
	{
		String spacing = "";
		String allignedText = "";
		
		int adjustmentSpace = lineLimit - inputText.length();
		
		for(int i = 0; i < adjustmentSpace / 2; i++)
		{
			spacing += " ";
		}
		
		allignedText = spacing + inputText;
		
		if(adjustmentSpace % 2 != 0)
			allignedText += spacing + " ";
		else
			allignedText += spacing;
		return allignedText;
	}
	//Assumes does not exceed line limit
	//Modified to have last word fit at end
	private String equallySpaced(String inputStr,int lineLimit)
	{
		WordLinkedList wordList = new WordLinkedList();
		int spaceCount = 0;
		int totalWordCharacters = 0;
		String equallySpacedWords = "";
		int beginWordIndex = 0;
		boolean atLeastOneWord = false; //If there is at least one word
		boolean wordFound = false;
		
		for(int index = 0; index < inputStr.length(); index++)
		{
			if(inputStr.charAt(index) != ' ' && inputStr.charAt(index) != '\n')
			{
				wordFound = true;
				atLeastOneWord = true;
				beginWordIndex = index;
				index++;
				
				while(index < inputStr.length() && wordFound)
				{
					if(inputStr.charAt(index) == ' ' || inputStr.charAt(index) == '\n')
					{
						wordFound = false;
					}
					else
						index++;
				}
				//End of string is a word
				if(wordFound)
				{
					wordList.addWord(inputStr.substring(beginWordIndex));
					totalWordCharacters += inputStr.length() - beginWordIndex;
				}
				else
				{
					wordList.addWord(inputStr.substring(beginWordIndex,index));
					totalWordCharacters += index - beginWordIndex;
				}
			}
			else if(!atLeastOneWord)
			{
				spaceCount++;
			}
		}
		//All words have been added to word list
		if(wordList.getNodeAmount() > 1) //If there are at least 2 words
		{
			for(int i = 0; i < spaceCount; i++)
			{
				equallySpacedWords += " ";
			}
			int wordAmount = wordList.getNodeAmount();
			int spaceLength = (lineLimit - spaceCount - totalWordCharacters) / (wordAmount - 1);
			int remainingSpace = (lineLimit - spaceCount - totalWordCharacters) % (wordAmount - 1);
			
			String spaceAmount = "";
			
			for(int i = 0; i < spaceLength; i++)
			{
				spaceAmount += " ";
			}
			//Remove first word from list
			equallySpacedWords += wordList.removeFromHead();
			while(!wordList.isEmpty())
			{
				String insertWord = wordList.removeFromHead();
				//equallySpacedWords += insertWord + spaceAmount; OLD METHOD
				
				if(remainingSpace > 0)
				{
					equallySpacedWords += " ";
					remainingSpace--;
				}
				equallySpacedWords += spaceAmount + insertWord;
			}
		}
		else
		{
			equallySpacedWords = inputStr;
		}
		wordList.clear();
		return equallySpacedWords;
	}
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
	//Formats wrap input
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
			
			System.out.println(formatInput(wrapInfo.storedText));
		}
		wrapQueue.clear();
		
		//Restore defaults
		justification = tempJustification;
		equalSpacing = tempEqualSpace;
		spacing = tempSpacing;
		lineLength = tempLineLimit;
	}
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
		//When validating if input exceeds limit, remember
		//to include error when entire word exceeds limit
		//and cannot be broken up
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
					formatWrapInput(inputText.substring(index + 1));
				}
				//Designed so if spaces then the spaces are included in following 
				else
				{
					formatColumnInput(inputText.substring(0,index));
					formatWrapInput(inputText.substring(index));
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
								String wrappedText = inputText.substring(startWordIndex);
								String queueWrap = wrapQueue.getQueueText().substring(0,lastCharacterIndex + 1);
								
								wrapQueue.updateQueue(queueWrap + " " + wrappedText);
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
								String wrappedText = inputText.substring(startWordIndex,wrapIndex + 1);
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
					addColumnWrap();
					formatWrapInput(inputText);
				}
			}
			else
			{
				addColumnWrap();
				formatColumnInput(inputText);
			}
		}
	}
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
		//When validating if input exceeds limit, remember
		//to include error when entire word exceeds limit
		//and cannot be broken up
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
					System.out.println(formatInput(inputText.substring(0, index)));
					formatWrapInput(inputText.substring(index + 1));
				}
				//Designed so if spaces then the spaces are included in following 
				else
				{
					System.out.println(formatInput(inputText.substring(0,index)));
					formatWrapInput(inputText.substring(index));
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
					System.out.println(formatInput(inputText));
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
								String wrappedText = inputText.substring(startWordIndex);
								String queueWrap = wrapQueue.getQueueText().substring(0,lastCharacterIndex + 1);
								
								wrapQueue.updateQueue(queueWrap + " " + wrappedText);
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
								String wrappedText = inputText.substring(startWordIndex,wrapIndex + 1);
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
				printWrap();
				System.out.println(formatInput(inputText));
			}
		}
	}
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
		//When validating if input exceeds limit, remember
		//to include error when entire word exceeds limit
		//and cannot be broken up
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
				//Index is now space between words and is cutoff
				//Ensures to recursively execute only once as substring
				//Will be less than maximum line length
				formatColumnInput(inputText.substring(0, index));
				formatColumnInput(inputText.substring(index + 1));
			}
			//Designed so if spaces then the spaces are included in following 
			else
			{
				formatColumnInput(inputText.substring(0,index));
				formatColumnInput(inputText.substring(index));
			}
		}
		else
		{
			switch (justification)
			{
				case 0:
					if(equalSpacing)
						inputText = equallySpaced(inputText,MAX_COLUMN_LENGTH);
					columnList.addTextLine(leftAllign(inputText,MAX_COLUMN_LENGTH));
					break;
				case 1:
					columnList.addTextLine(center(inputText,MAX_COLUMN_LENGTH));
					break;
				case 2:
					columnList.addTextLine(rightAllign(inputText,MAX_COLUMN_LENGTH));
					break;
				default:
					System.out.println("Error");
			}
			if(spacing)
			{
				columnList.addTextLine(leftAllign("",MAX_COLUMN_LENGTH));
			}
		}
	}
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
				//Index is now space between words and is cutoff
				//Ensures to recursively execute only once as substring
				//Will be less than maximum line length
				formattedOutput += formatInput(inputText.substring(0, index));
				formattedOutput += "\n" + formatInput(inputText.substring(index + 1));
			}
			//Designed so if spaces then the spaces are included in following 
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
				else
					formattedOutput+= "\n" + formatInput("");
			}
		}
		else
		{
			switch (justification)
			{
				case 0:
					if(equalSpacing)
						inputText = equallySpaced(inputText,lineLength);
					formattedOutput = leftAllign(inputText,lineLength);
					break;
				case 1:
					formattedOutput = center(inputText,lineLength);
					break;
				case 2:
					formattedOutput = rightAllign(inputText,lineLength);
					break;
				default:
					System.out.println("Error");
			}
			if(spacing)
			{
				formattedOutput += "\n";
			}
		}	
		return formattedOutput;
	}

}
