
/**
 * Node class creates node foundation for Priority Queue and Huffman Tree
 * 
 * @author Tim Jarvis
 * @version 11/13/2022
 *
 */
public class Node {
	public String character;
	public int frequency;
	public Node next;
	public Node right;
	public Node left;
	
    /**
     * 
     *  Creates a Huffman Node object with string and character data
     *  
     *  @param text frequency string and character
     *  
     *  @return Node with character and string data
     */
    public void initializeNode(String text) {
    	
    	// checks to see if frequency of character is one or two digits
    	if (text.charAt(text.length() - 2) == ' ') {
    		frequency = (char) Character.getNumericValue(text.charAt(text.length() - 1));
    	}
    	else {
    		frequency = Integer.valueOf(text.substring(text.length() - 2));
    	}
        
    	character = Character.toString(text.charAt(0));
    
    }
}




