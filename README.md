# CSE360_TeamProject

  	TextALot is a text formatter that is able to take text file and formatted it to another .txt file with format instruction set by user 
  where, 
				each line contains at default 80 characters unless the user sets otherwise, 
				however the max length is 95 words and at minimum one character in one line.

				the format of each line is determined by the justification type, including left, right, and center, 
				equally spaced, wrapping is on/off, and paragraph 

				The format of each line can be divided into two columns set by user.

				every number of line is separated by num blank lines, where b# is given by the user.

				Title would be formatted with an underline represented by “---” underneath the title.

				Equally spaced format works only with left justification.

				Wrapping is given precedence over equally spaced.  

	
	Also, the TextALot will provide errors output, including errors made in format instructions and file not found. 
	Any special characters used when saving will display error.
	A user is required to select a .txt file as an input file. User must enter “.txt” when opening file. 
	Also, it is saved in project folder. When saving, user does not need to enter “.txt”. 
	Column resets line limit to 80 characters. Line limit cannot be modified while column is on. 
