import java.util.*;

public class Formatter {
	int n;
	
	public Formatter() {
		n = 80;
	}
	
	public void title(String line) {
		if(line.length() > n) {
			System.out.println("Error: Line exceeds maximum character amount");
		}
		else {
			line = line.toUpperCase();
			int spaceNum = (n - line.length())/2;
			String space = "";
			String underline = "";
			for(int i = 0; i < spaceNum; i++) {
				space += " ";
			}
			for(int i = 0; i < n; i++) {
				underline += "_";
			}
			System.out.println(space+ line + space);
			System.out.println(underline);
			
		}
		
	}
	
	public void paragraph(String command, String nextLine) {
		int[] arr = new int[command.length()-2];
		boolean valid = true;
		int i = 0;
		while(valid == true && i + 2 < command.length()) {
			int num = (int)command.charAt(i+2);
			if(num>47 && num<58) { // if it is in the range of integers of the ASCII values 
				arr[i] = ((int)command.charAt(i+2)) - 48;
				i++;
			}
			else
				valid = false;
		}
		if(valid == false) {
			System.out.println("Error: incorrect command format");
		}
		else {
			String Space = "";
			int space = 0;
			for(int j = 0; j < arr.length; j++) {
				space += ((Math.pow(10, j))*arr[(arr.length-1) - j]); 
			}
			for(i = 0; i < space; i++) {
				Space += " ";
			}
			nextLine = Space + nextLine;
			System.out.println(nextLine);
		}
			
	}

}
