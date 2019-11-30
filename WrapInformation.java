package application;

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
 * Class to be used in implementing a queue to keep track of all the 
 * lines that will need to be wrapped. Tracks all the settings 
 * specified by the user that affect wrapping. 
 */

public class WrapInformation 
{
	public String storedText;
	public int justification;
	public boolean equalSpacing;
	public boolean spacing;
	public int lineLength;
	
	/**
	 * WrapInformation Constructor
	 * sets all information to default settings
	 */
	public WrapInformation()
	{
		storedText = "";
		justification = 0;
		equalSpacing = false;
		spacing = false;
		lineLength = 80;
	}
	
	/**
	 * WrapInformation Constructor with specified settings
	 * @param insertText updates the text to be added 
	 * @param allignment matches the alignment settings
	 * @param equallySpaced is equally spaced setting on
	 * @param singleSpaced matches settings of single or double spaced
	 * @param lineLimit updates the line limit
	 * updates all the settings to users specifications
	 */
	public WrapInformation(String insertText, int alignment,boolean equallySpaced,boolean singleSpaced,int lineLimit)
	{
		storedText = insertText;
		justification = alignment;
		equalSpacing = equallySpaced;
		spacing = singleSpaced;
		lineLength = lineLimit;
	}
}