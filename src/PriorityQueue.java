
/**
 * 
 * Creates a Priority queue based on integer and character values in the nodes
 * 
 * 
 * @author Tim Jarvis
 * @version 11/13/2022
 *
 */
public class PriorityQueue {
	private Node Top;
	
	public PriorityQueue() {
		Top = null;
	}
	
	/** 
	 * Checks to see if the queue contains any items
	 * 
	 * @return true if queue contains no items; false if there are items
	 */
	public boolean isEmpty() {
		return (Top == null);
	}
	
	/**
	 * Add new item to queue
	 * 
	 * @param item Node input placed on queue based on priority
	 */
	public void push(Node item) {
		// check if queue is empty
		if (Top == null) {
			Top = item;
		}
		
		// check if new node frequency is less than top frequency
		else if(item.frequency < Top.frequency) {
			item.next = Top;
			Top = item;
		}
		
		// if frequency is the same compare the strings
		else if (item.frequency == Top.frequency) {
			if (stringCompare(item, Top) < 0) {
				item.next = Top;
				Top = item;
			}
			else {
				item.next = Top.next;
				Top.next = item;
			}
		}
		
		// if new node frequency is more then top frequency need to find a new place in the queue
		// ***Look at using a search in the queue to increase efficiency!  Youre doing it the slowest way possible
		else {
			Node temp = Top.next;
			Node previous = Top;
			
			//if there is only one item in the tree
			if (temp == null && item.frequency > Top.frequency) {
				Top.next = item;
				item.next = null;
			}
			
			// if the new node frequency goes directly after the top of the queue
			else if (temp.frequency > item.frequency) {
				previous.next = item;
				item.next = temp;
			}
			
			// check each node after the top to find the proper spot
			else {
				while (temp.frequency < item.frequency) {
					if (temp.next == null) {
						temp.next = item;
						previous.next = temp;
						break;
					}
					previous = temp;
					temp = temp.next;
				}
			}
			
			// Check to see if item goes before or after
			if (temp != null && temp.frequency > item.frequency) {
				previous.next = item;
				item.next = temp;
			}
			
			else if (temp != null && temp.frequency == item.frequency) {
				int x = stringCompare(temp, item);
				if (x < 0) {
					item.next = temp.next;
					temp.next = item;
				}
				else {
					temp.next = item.next;
					item.next = temp;
				}
			}
		}
	}
	
	/**
	 * Removes item from top of queue; queue must not be empty
	 * Pointers adjusted to next node in queue
	 * 
	 * @return item from top of queue and remove item from queue
	 */
	public Node pop() {
		Node Temp = Top;
		
		try {
			if (this.isEmpty()) {
				throw new QueueEmptyException();
			}
			else {
				Top = Top.next;
			}
		}
		catch (QueueEmptyException e) {
			System.out.println(e + ": Queue is empty; Pop cannot be completed on empty stack");
		}
		return Temp;
	}
	
	/**
	 * Get the next item in the queue
	 * @return top of queue
	 */
	public Node peek() {
		return Top;
	}
	
	/**
	 * 
	 * Compares both node characters to determine precedence
	 * 
	 * @param n1 node 1
	 * @param n2 node 2
	 * @return integer value indicating whether one node is bigger or they're the same.
	 */
	private int stringCompare(Node n1, Node n2) {
		if (n1.character.length() == 1 && n2.character.length() == 1) {
			if (n1.character.charAt(0) < n2.character.charAt(0)) {
				return -1;
			}
			else {
				return 1;
			}
		}
		
		else if (n1.character.length() < n2.character.length()){
			return -1;
		}
		
		else if (n1.character.length() == n2.character.length()) {
			
			for (int i = 0; i < n1.character.length(); i++) {
				if (n1.character.charAt(i) < n2.character.charAt(i)) {
					return -1;
				}
				else if (n1.character.charAt(i) > n2.character.charAt(i)){
					return 1;
				}
			}
			return 0;
		}
		
		else {
			return 1;
		}
	}
	
	@SuppressWarnings("serial")
	private class QueueEmptyException extends Exception {
	    private QueueEmptyException() {
	    }
	}
}
