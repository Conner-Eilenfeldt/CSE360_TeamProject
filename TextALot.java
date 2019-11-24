package teamProject;

public class TextALot
{
	int MAX_LINE_LENGTH = 150;

	int lineLength = 80;
	int justification = 0; // 0 = left, 1 = center, 2 = right
	int paragraph = 0; // number of spaces for new paragraph
	boolean equalSpacing = false; // true = equal spacing
	boolean wrapping = false; // true = wrapped text
	boolean column = false; // true = 2 columns
	//	ArrayList<String> array;

	String input;

	void justify(String input, int lineLength, int justification)
	{
		int spaces = 0;

		if (justification == 1)
			spaces = (lineLength - input.length()) / 2;
		else if (justification == 2)
			spaces = lineLength - input.length();

		for (int i = 0; i < spaces; i++)
			System.out.print(" ");

		System.out.print(input);
	}

	int main ()
	{
		String output;
		int number;
		input = getline();

		if (input.charAt(0) == '-')
		{
			switch(input.charAt(1))
			{
			case 'n':
				try
				{
					number = Integer.parseInt(input.substring(2));
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
				if(input.equals("-w+"))
					wrapping = true;
				else if(input.equals("-w-"))
					wrapping = false;
				else
					//command error
					break;

			case 'p':
				try
				{
					number = Integer.parseInt(input.substring(2));
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
					number = Integer.parseInt(input.substring(2));
					// blankLine(number);
				}
				catch(Exception e)  // change e
				{
					System.out.print("error");
				}

				break;

			case 't':
				// title method
				break;

			case 'a':
				// column method
				break;
			}
		}
		else
		{
			if (column)
				output = columnFormat(input);
			else
				output = format(input);

			System.out.print(output);
		}
	}

}