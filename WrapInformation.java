package application;

public class WrapInformation 
{
	public String storedText;
	public int justification;
	public boolean equalSpacing;
	public boolean spacing;
	public int lineLength;
	
	public WrapInformation()
	{
		storedText = "";
		justification = 0;
		equalSpacing = false;
		spacing = false;
		lineLength = 80;
	}
	public WrapInformation(String insertText, int allignment,boolean equallySpaced,boolean singleSpaced,int lineLimit)
	{
		storedText = insertText;
		justification = allignment;
		equalSpacing = equallySpaced;
		spacing = singleSpaced;
		lineLength = lineLimit;
	}
}
