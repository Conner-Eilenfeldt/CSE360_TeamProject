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
 * The implementation of the queue with WrapInformation nodes, class 
 * instances include a head pointer and tail pointer, and the number
 * of nodes in the queue. Supports normal queue functions such as: 
 * check if it is empty, update node information, getters and setters
 * popping the head of the queue. 
 */
public class WrapQueue 
{
	/**
	 * Subclass Node
	 * contains an instance of wrap information
	 * is to be used to implement a queue
	 * points to the next node in the list
	 */
	private class Node
	{
		public WrapInformation wrapInfo;
		public Node next;
	}
	
	private int nodeAmount;
	private Node head;
	private Node tail;
	
	/**
	 * WrapQueue Constructor
	 * sets all members to default values
	 */
	public WrapQueue()
	{
		head = null;
		tail = null;
		nodeAmount = 0;
	}
	/**
	 * retrieves the total number of nodes in the linked list
	 * @return the number of lines in the queue
	 */
	public int getNodeAmount()
	{
		return this.nodeAmount;
	}
	/**
	 * retrieves the head of the linked list
	 * @return node at the head of the queue
	 */
	public Node getHead()
	{
		return this.head;
	}
	/**
	 * retrieves the tail of the linked list
	 * @return node at the tail of the queue
	 */
	public Node getTail()
	{
		return this.tail;
	}
	/**
	 * isEmpty checks if the queue is empty or not
	 * @return true if it is empty, false if not
	 */
	public boolean isEmpty()
	{
		boolean empty = false;
		if(head == null)
			empty = true;
		return empty;
	}
	/**
	 * updateQueue updates the text in the wrap information 
	 * @param updatedText replaces stored text
	 */
	public void updateQueue(String updatedText)
	{
		if(head != null)
		{
			head.wrapInfo.storedText = updatedText;
		}
	}
	/**
	 * returns text from the head of the queue
	 * so long as the queue is not empty
	 * @return the text that is stored
	 */
	public String getQueueText()
	{
		String text = "";
		if(head != null)
		{
			text = head.wrapInfo.storedText;
		}
		return text;
	}
	/**
	 * lastCharacterIndex finds the index of the last 
	 * character of the last word in the line
	 * @return index of the last non-space character
	 * @return index of the last non-space character
	 */
	public int lastCharacterIndex()
	{
		int lastCharacterIndex = -1;
		if(head != null)
		{
			String storedInfo = head.wrapInfo.storedText;
			if(storedInfo.length() > 0)
			{
				int index = storedInfo.length() - 1;
				while(index > 0 && (storedInfo.charAt(index) == ' ' || storedInfo.charAt(index) == '\n'))
				{
					index--;
				}
				lastCharacterIndex = index;
			}
		}
		return lastCharacterIndex;
	}
	/**
	 * retrieves the line length setting when the
	 * text was added to the queue 
	 * @return the line length specified by user
	 */
	public int getLineLength()
	{
		int lineLength = 80;
		
		if(head != null)
		{
			lineLength = head.wrapInfo.lineLength;
		}
		
		return lineLength;
	}
	/**
	 * searchList finds a node at a specific index
	 * @param index is the index of the node to return
	 * @return the node found at index
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
	/**
	 * removes the head of the queue and
	 * retrieves the wrap information
	 * that was stored
	 * @return the head of the queue
	 */
	public WrapInformation removeFromHead()
	{
		WrapInformation removedText = null;
		
		if(head != null)
		{
			removedText = head.wrapInfo;
			head = head.next;
			if(head == null) //Removed last remaining node
				tail = null;
		}
		return removedText;
	}
	/**
	 * inserts the text into the queue storing all current
	 * user settings
	 * @param insertText updates text to be inserted
	 * @param allignment updates allignment
	 * @param allignment updates alignment
	 * @param equallySpaced is it equally spaced
	 * @param singleSpaced	updates single or double spaced
	 * @param lineLength	updates the max line length
	 */
	public void insertText(String insertText,int allignment,boolean equallySpaced, boolean singleSpaced,int lineLength)
	{
		Node insertNode = new Node();
		insertNode.wrapInfo = new WrapInformation(insertText,allignment,equallySpaced,singleSpaced,lineLength);
		insertNode.next = null;
		
		if(isEmpty()) //List is empty
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
	/**
	 * clear resets a node back to default values
	 */
	public void clear()
	{
		head = null;
		tail = null;
		nodeAmount = 0;
	}
}
