package teamProject;

public class WordLinkedList 
{
	private class Node
	{
		public String storedWord;
		public Node next;
	}
	
	private int nodeAmount;
	private Node head;
	private Node tail;
	
	public WordLinkedList()
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
	public String removeFromHead()
	{
		String removedWord = null;
		
		if(head != null)
		{
			removedWord = head.storedWord;
			head = head.next;
			if(head == null) //Removed last remaining node
				tail = null;
		}
		return removedWord;
	}
	public void addWord(String insertWord)
	{
		Node insertNode = new Node();
		insertNode.storedWord = insertWord;
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
