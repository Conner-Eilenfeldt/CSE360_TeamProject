package application;

public class ColumnLinkedList 
{
	private class Node
	{
		public String formattedText;
		public Node next;
	}
	
	private final String COLUMN_SPACE = "          ";
	
	private int nodeAmount;
	private Node head;
	private Node tail;
	private DisplayGui dispGui;
	
	public ColumnLinkedList()
	{
		head = null;
		tail = null;
		nodeAmount = 0;
	}
	public ColumnLinkedList(DisplayGui dispGui)
	{
		head = null;
		tail = null;
		nodeAmount = 0;
		this.dispGui = dispGui;
	}
	public int getNodeAmount()
	{
		return this.nodeAmount;
	}
	public Node getHead()
	{
		return this.head;
	}
	public Node getTail()
	{
		return this.tail;
	}
	private boolean isEmpty()
	{
		boolean empty = false;
		if(head == null)
			empty = true;
		return empty;
	}
	public Node searchList(int index)
	{
		Node searchResult = head;
		int i = 0;
		while(searchResult != null && i < index)
		{
			searchResult = searchResult.next;
			i++;
		}
		return searchResult;
	}
	private void insertAtTail(Node insertNode)
	{
		if(this.tail == null) //List is empty
		{
			head = insertNode;
			tail = insertNode;
		}
		else
		{
			tail.next = insertNode;
			tail = insertNode;
		}
		nodeAmount++;
	}
	//Make sure string to input meets 35 character
	//Limit
	//Curent iteration assumes inserted column
	//is already properly formatted
	
	public void addTextLine(String textInput)
	{
		Node insertNode = new Node();
		insertNode.formattedText = textInput;
		insertNode.next = null;
		
		insertAtTail(insertNode);
	}
	public void printColumns()
	{
		if(!isEmpty())
		{
			int median = nodeAmount / 2;
			boolean evenAmountNodes = nodeAmount % 2 == 0;
			
			Node leftColNavigator = this.head;
			Node rightColNavigator;
			
			if(evenAmountNodes)
				rightColNavigator = searchList(median);
			else
				rightColNavigator = searchList(median + 1);
			
			for(int i = 0; i < median; i++)
			{
				
				System.out.print(leftColNavigator.formattedText);
				System.out.print(COLUMN_SPACE);
				System.out.println(rightColNavigator.formattedText);
				
				dispGui.updateTextDisplay(leftColNavigator.formattedText);
				dispGui.updateTextDisplay(COLUMN_SPACE);
				dispGui.updateTextDisplay(rightColNavigator.formattedText + "\n");
				
				leftColNavigator = leftColNavigator.next;
				rightColNavigator = rightColNavigator.next;
			}
			if(leftColNavigator != null && !evenAmountNodes)
			{
				System.out.println(leftColNavigator.formattedText);
				dispGui.updateTextDisplay(leftColNavigator.formattedText + "\n");
			}		
		}
	}
	public void clear()
	{
		head = null;
		tail = null;
		nodeAmount = 0;
	}
}
