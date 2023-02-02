import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HuffmanEncoding {
	
	/**
     *  Main entry point for the program.
     *  @param args[]   Holds four command line arguments:  the input filename
     *                  and the output filename.
     * @throws CloneNotSupportedException 
     */
	public static void main (String [] args) {
        BufferedReader  frequency;
        BufferedReader  encoded;
        BufferedReader  clearText;
        
        BufferedWriter  cipher;
        BufferedWriter  preorder;
        BufferedWriter  encodedMessages;
        BufferedWriter  decodedMessages;
        
        PriorityQueue 	queue = new PriorityQueue();
        
        Node 			root;
        
        String 			match;
        String 			printText;
        String          charInfo;
        String			encodedLine;
        String			messageText;
        
        
        //  Check for command line arguments.
        if (args.length != 7) {
            System.out.println("Usage:  java [frequency taable file pathname]" +
                " [cipher code file path name] [preorder traversal file pathname] [encoded messages file pathanme] "
                + "[encoded message from clear text file path name] [clearText file pathname]"
                + "[decoded messages (from encoded file) file pathname]");
            System.exit(1);
        }
        
        //  Open the files that will be used for input and output.
        try {
            frequency = new BufferedReader(new FileReader(args[0]));
            encoded = new BufferedReader(new FileReader(args[3]));
            clearText = new BufferedReader(new FileReader(args[5]));
            
            cipher = new BufferedWriter(new FileWriter(args[1]));
            preorder = new BufferedWriter(new FileWriter(args[2]));
            encodedMessages = new BufferedWriter(new FileWriter(args[4]));
            decodedMessages = new BufferedWriter(new FileWriter(args[6]));
        } catch (Exception ioe) {
            System.err.println(ioe.toString());
            return;
        }
        
        //Read frequency table file
        charInfo = readFile(frequency);
         
        while (charInfo != null) {
        	Node node = new Node();
        	
        	node.initializeNode(charInfo);
        	
        	queue.push(node);
        	
        	charInfo = readFile(frequency);
        }
        
        
        // Generate Cipher displays character and code for every letter in alphabet
        HuffmanTree tree = new HuffmanTree(queue);
        root = tree.makeTree();
        String code = "";
        String outputText = "\n\nHuffman Tree Code Cipher:\n";
        writeResult(outputText, cipher);
        generateCipher(root, code, cipher);
        
        
        // preOrder prints preOrder traversal to file
        String preOrderText = "\n\nHuffman Tree PreOrder Traversal:\n";
        writeResult(preOrderText, preorder);
        preOrder(root, preorder);
        
        
        // Decodes encoded message and writes decoded message to file
        String decodedText = "\n\nHuffman Tree Encoded.txt file Decoded Messages:\n";
        writeResult(decodedText, encodedMessages);
        encodedLine = readFile(encoded);
        
        Coding statements = new Coding(root);
        
        // Generating clear message from encoded message
        while (encodedLine != null) {
        	
        	// decode method in Coding class
        	String decodedLine = statements.decode(encodedLine);
        	
        	printText = "Encoded Message: " + encodedLine + "\n\nDecoded Message: " + decodedLine + "\n";
        	writeResult(printText, encodedMessages);
        	
        	//re-encode decoded line and make sure it matches
        	String encode = statements.encode(decodedLine);
        	
        	if (encodedLine.equals(encode)) {
        		match = "True";
        	}
        	else {
        		match = "False";
        	}
        	
        	printText = "Verify Encoded Message: " + encode + "\n\nVerify Decoded Message: " + decodedLine + 
        			"\n\n" + "Messages Match: " + match + "\n";
        	writeResult(printText, encodedMessages);
        	
        	encodedLine = readFile(encoded);
        }
        
        // Decodes encoded message and writes decoded message to file
        String encodedText = "\n\nHuffman Tree ClearText.txt file Encoded Messages:\n";
        writeResult(encodedText, decodedMessages);
        messageText = readFile(clearText);
        
        // Generating encoded message from clear message
        while (messageText != null) {
        	
        	// decode method in Coding class
        	encodedLine = statements.encode(messageText);
        	
        	String newText = "Encoded Message: " + encodedLine + "\n\nDecoded Message: " + messageText + "\n";
        	
        	writeResult(newText, decodedMessages);
        	
        	//re-decode encoded line and make sure it matches original message
        	String decode = statements.decode(encodedLine);
        	
        	newText = "Verify Encoded Message: " + encodedLine + "\n\nVerify Decoded Message: " + decode + "\n";
        	writeResult(newText, decodedMessages);

        	messageText = readFile(clearText);
        }
        
        
        //  Clean up and return to the operating system.
        try {
            frequency.close();
            cipher.close();
            preorder.close();
            encodedMessages.close();
            decodedMessages.close();
            encoded.close();
            clearText.close();
        } catch (Exception x) {
            System.err.println(x.toString());
        }
        return;
	}
	
	/**
	 * Takes the tree and generates the cipher for decoding messages
	 * 
	 * @param root Huffman tree
	 * @param binary String for each character
	 * @param output file to write code to
	 */
	public static void generateCipher(Node root, String binary, BufferedWriter output) {
		if (root.left == null && root.right == null) {
			if (root.character.length() == 1) {
				String outputText = root.character + " : " + binary;
				writeResult(outputText, output);
				return;
			}
		}
		generateCipher(root.left, binary + "0", output);
		generateCipher(root.right, binary + "1", output);
	}
	
	/**
     *  Write Pre-Order of Tree to File
     *  
     *  @param root Huffman Tree
     *  @param output file the tree will be written too
     */
    private static void preOrder(Node root, BufferedWriter output) {
    	if (root == null)
            return;
    	
    	String nodeText = root.character + " : " + root.frequency;
    	
        writeResult(nodeText, output);
 
        preOrder(root.left, output);
        preOrder(root.right, output);
    }

	 /**     * 
     *  Reads the next expression from the input file.
     *  @param  input A buffered stream from a file that contain
     *                 one prefix expression per line.
     *  @return The next prefix expression from the input file.
     */
    private static String readFile(BufferedReader input) {
    
        String text = "";
        
        try {
            text = input.readLine();

        } catch (IOException iox) {
            System.err.println(iox.toString());
            System.exit(2);
        }
        return text;
    }
    
    
    /**
     *   
     *  Write a string to the output stream.
     *  @param text   The text to write.
     *  @param output The output stream to write the text to.
     */
    private static void writeResult(String text, BufferedWriter output) {
    
        try {
            output.write(text, 0, text.length());  
            output.newLine();
        } catch (IOException iox) {
            System.err.println(iox.toString());
            System.exit(3);
        }
        return;
    }   
}
