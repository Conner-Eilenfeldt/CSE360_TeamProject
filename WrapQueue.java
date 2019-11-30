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
	private class Node
	{
		public WrapInformation wrapInfo;
		public Node next;
	}
	
	private int nodeAmount;
	private Node head;
	private Node tail;
	
	public WrapQueue()
	{
		head = null;
		tail = null;
		nodeAmount = 0;
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
	public boolean isEmpty()
	{
		boolean empty = false;
		if(head == null)
			empty = true;
		return empty;
	}
	public void updateQueue(String updatedText)
	{
		if(head != null)
		{
			head.wrapInfo.storedText = updatedText;
		}
	}
	public String getQueueText()
	{
		String text = "";
		if(head != null)
		{
			text = head.wrapInfo.storedText;
		}
		return text;
	}
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
	public int getLineLength()
	{
		int lineLength = 80;
		
		if(head != null)
		{
			lineLength = head.wrapInfo.lineLength;
		}
		
		return lineLength;
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
	public void clear()
	{
		head = null;
		tail = null;
		nodeAmount = 0;
	}
}
