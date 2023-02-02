
/**
 * Class encodes and decodes string using Huffman Tree
 * 
 * @author Tim Jarvis
 * @version 11/14/2022
 *
 */
public class Coding {
	
	Node tree;
	
	/**
	 * Tree used for encoding / decoding
	 * @param tree Huffman tree
	 */
	public Coding(Node tree) {
		this.tree = tree;
	}
	
	/**
	 * Decodes an encoded message using tree
	 * 
	 * @param string encoded string
	 * @return decoded string
	 */
	public String decode(String string) {
		Node root = tree;
		
		String decode = "";
		
		for (int i = 0; i < string.length(); i++) {
			
			if (string.charAt(i) == '1') {
				root = root.right;
				
				if (root.right == null && root.left == null) {
					decode += root.character;
					root = tree;
				}
			}
			else {
				root = root.left;
				
				if (root.right == null && root.left == null) {
					decode += root.character;
					root = tree;
				}
			}
		}
		return decode;
	}
	
	/**
	 * Encodes a decoded message using tree
	 * 
	 * @param string decoded string
	 * @return encoded string
	 */
	public String encode(String string) {
		Node root = tree;
		
		String encode = "";
		
		String upperString = string.toUpperCase();
		
		for (int i = 0; i < upperString.length(); i++) {
			char letter = upperString.charAt(i);
			
			if (!(letter >= 'A') && (letter <= 'Z')) {
	    		continue;
	    	}
			
			String character = upperString.substring(i, i + 1);
			
			while (!(root.right == null && root.left == null)) {
				if (root.right.character.contains(character)) {
					encode += "1";
					root = root.right;
				}
				else {
					encode += "0";
					root = root.left;
				}
			}
			root = tree;
		}
		return encode;
	}
}
