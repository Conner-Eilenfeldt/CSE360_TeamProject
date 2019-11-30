package application;

/*
 * Team Members:
 * @author Payden Brown
 * @author Jonathan Chance
 * @author Conner Eilenfeldt
 * @author Ajay Tiwari
 * 
 * Class ID:	CSE360 85141
 * 
 * Assignment: Team Project TextALot
 * Description:
 * ColumnLinkedList holds the output lines to then
 * be properly formatted into columns
 */

public class ColumnLinkedList 
{
	/**
	 * Node class is an individual
	 * node in the linked list containing
	 * the formattedText and pointer
	 * to the next node 
	 */
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
	
	/*
	 * Constructor for column linked list
	 */
	public ColumnLinkedList()
	{
		head = null;
		tail = null;
		nodeAmount = 0;
	}
	/*
	 * Constructor for column linked
	 * list with reference to display GUI
	 */
	public ColumnLinkedList(DisplayGui dispGui)
	{
		head = null;
		tail = null;
		nodeAmount = 0;
		this.dispGui = dispGui;
	}
	/*
	 * Returns the total amount of nodes within
	 * the linked list
	 * @return Amount of nodes in linked list
	 */
	public int getNodeAmount()
	{
		return this.nodeAmount;
	}
	/*
	 * Returns the head of the column linked list
	 * @return head of the linked list 
	 */
	public Node getHead()
	{
		return this.head;
	}
	/*
	 * Returns the tail of the column linked list
	 * @return tail of the linked list
	 */
	public Node getTail()
	{
		return this.tail;
	}
	/*
	 * Checks whether or not the column
	 * linked list is empty
	 * @return boolean value whether linked
	 * list is or is not empty
	 */
	private boolean isEmpty()
	{
		boolean empty = false;
		if(head == null)
			empty = true;
		return empty;
	}
	/*
	 * Returns the node at a specific
	 * index within the linked list
	 * 
	 * @param index the position of node in list to be searched
	 * @return node in linked list that was searched
	 */
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
	/*
	 * Inserts node at the end of the linked list
	 * @param insertNode node to be inserted at tail
	 * of linked list
	 */
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
	/*
	 * Precondition: textInput is properly
	 * formatted to be inserted
	 * 
	 * @param textInput properly formatted
	 * text to be inserted
	 */
	public void addTextLine(String textInput)
	{
		Node insertNode = new Node();
		insertNode.formattedText = textInput;
		insertNode.next = null;
		
		insertAtTail(insertNode);
	}
	/*
	 * Prints columns by finding the middle
	 * of the linked list and printing moving from
	 * the beginning and middle of list to
	 * equally divide the text to create a column
	 */
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
				dispGui.updateTextDisplay(leftColNavigator.formattedText);
				dispGui.updateTextDisplay(COLUMN_SPACE);
				dispGui.updateTextDisplay(rightColNavigator.formattedText + "\n");
				
				leftColNavigator = leftColNavigator.next;
				rightColNavigator = rightColNavigator.next;
			}
			if(leftColNavigator != null && !evenAmountNodes)
			{
				dispGui.updateTextDisplay(leftColNavigator.formattedText + "\n");
			}		
		}
	}
	/*
	 * Clears all values and resets
	 * the linked list
	 */
	public void clear()
	{
		head = null;
		tail = null;
		nodeAmount = 0;
	}
}
