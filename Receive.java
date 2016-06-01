import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Receive
{
public static void main(String[] args) {
		// Define the key
		final byte[] key = { (byte) 0x00, (byte) 0x00, (byte) 0x27, (byte) 0xED, 
							 (byte) 0x8F, (byte) 0x5C, (byte) 0x3E, (byte) 0x8B, 
							 (byte) 0xAF, (byte) 0x16, (byte) 0x56, (byte) 0x0D, 
							 (byte) 0x14, (byte) 0xC9, (byte) 0x0B, (byte) 0x43 
							 };
		Transmit idea = new Transmit(key);
		printKey("Key: ", key);
		
		// Read the block of text to be encrypted
    	FileInputStream fileInputStream = null;
        File file = new File("Original.txt");
        long filesize = file.length();
		int dataSize = ((int) (file.length() + 7 ) / 8) * 8;
        byte[] data = new byte[(int) dataSize];
        
        try {
            //convert file into array of bytes
        	fileInputStream = new FileInputStream(file);
        	fileInputStream.read(data);
        	fileInputStream.close();
	       
        	System.out.println("Filesize = " + filesize + "; dataSize = " + dataSize);
        	printData("Original  Data: ", data);
        	printText("Original  Text: ", data);
        } catch(Exception e){
        	e.printStackTrace();
        }
		
		// Encrypt the text
    	System.out.println("");
		for (int i = 0; i < dataSize/8; i++) {
			idea.encrypt(data, i*8);
			printData("Encrypted Data: ", data);
		}
		printText("Encrypted Text: ", data);
		
        try {
        	FileOutputStream fileOuputStream = 
                  new FileOutputStream("Encrypted.txt"); 
        	fileOuputStream.write(data);
        	fileOuputStream.close();
        } catch(Exception e){
            e.printStackTrace();
        }

		// Decrypt the text and write to a file
    	System.out.println("");
		for (int i = 0; i < dataSize/8; i++) {
			idea.decrypt(data, i*8);
			printData("Decrypted Data: ", data);
		}
		printText("Decrypted Text: ", data);
		try {
        	FileOutputStream fileOuputStream = 
                    new FileOutputStream("Decrypted.txt"); 
          	fileOuputStream.write(data);
          	fileOuputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void printKey(String label, byte[] data) {
		System.out.print(label);
    	for (int i = 0; i < data.length; i++) {
    		System.out.printf("%02x", data[i]);
        }
    	System.out.println("\n");
	}

	private static void printText(String label, byte[] data) {
		System.out.print(label);
    	for (int i = 0; i < data.length; i++) {
    		System.out.print((char) data[i]);
        }
    	System.out.println("");
	}

	private static void printData(String label, byte[] data) {
		System.out.print(label);
    	for (int i = 0; i < data.length; i+=8) {
    		System.out.printf("%02x%02x %02x%02x %02x%02x %02x%02x | ", data[i], data[i+1], data[i+2], data[i+3],
    															data[i+4], data[i+5], data[i+6], data[i+7]);
        }
    	System.out.println("");
	}
}



