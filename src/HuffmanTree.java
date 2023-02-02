

/**
 * HuffmanTree Class Creates a Huffman Tree
 * 
 * @author Tim Jarvis
 * @version 11/13/2022
 *
 */
public class HuffmanTree {
	
	private PriorityQueue queue;
	private Node n1;
	private Node n2;
	private Node root;
	
	String orderedString;
	
	/**
	 * 
	 * @param queue to be turned into Huffman Tree
	 */
	public HuffmanTree(PriorityQueue queue) {
		this.queue = queue;
	}
	
	/**
	 * Makes the Huffman tree from the queue
	 * 
	 * @return tree root node
	 */
	public Node makeTree() {
		
		while (!queue.isEmpty()) {
			n1 = queue.pop();
			n2 = queue.pop();
			
			Node combined = new Node();
			
			combined.frequency = n1.frequency + n2.frequency;
			
			combined.character = orderString(n1.character + n2.character);

			combined.left = n1;
			combined.right = n2;
			
			root = combined;
			
			queue.push(combined);
			
			if (queue.peek().next == null) {
				break;
			}
		}
		return root;
	}
	
	
	/**
	 * Orders the characters in node alphabetically
	 * 
	 * @param string that needs to be ordered
	 * @return alphabetically ordered String
	 */
	private static String orderString(String string) {
		char minChar;
		
		if (string.length() == 1) {
			return string;
		}
		else {
			if (string.charAt(0) < string.charAt(1)) {
				minChar = string.charAt(0);
			}
			else {
				minChar = string.charAt(1);
			}
			
			for (int i = 2; i < string.length() - 1; i++) {
				if (minChar < string.charAt(i)) {
				}
				else {
					minChar = string.charAt(i);
				}
			}
			
			String min = "" + minChar;
			
			string = string.replace(min, "");

			return min + orderString(string);
		}
	}
}
