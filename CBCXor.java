package cbc;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.xml.bind.DatatypeConverter;

public class CBCXor {

	public static void main(String[] args) {
		String filename = "input.txt";
		byte[] first_block = null;
		byte[] encrypted = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			first_block = br.readLine().getBytes();
			encrypted = DatatypeConverter.parseHexBinary(br.readLine());
			br.close();
		} catch (Exception err) {
			System.err.println("Error handling file.");
			err.printStackTrace();
			System.exit(1);
		}
		String m = recoverMessage(first_block, encrypted);
		System.out.println("Recovered message: " + m);
	}

	/**
	 * Recover the encrypted message (CBC encrypted with XOR, block size = 12).
	 * 
	 * @param first_block
	 *            We know that this is the value of the first block of plain
	 *            text.
	 * @param encrypted
	 *            The encrypted text, of the form IV | C0 | C1 | ... where each
	 *            block is 12 bytes long.
	 */
	private static String recoverMessage(byte[] first_block, byte[] encrypted) {
		
		// the secret key
		byte [] key = new byte[12];
		// the message
		byte [] message = new byte [encrypted.length];
		int i = 0, j = 0, z = 0;
		
		/* Get the value of the key
			K = M1 + C0 + C1 
		 */
		for (byte cipher: encrypted){
			key [i] = (byte) (first_block[i] ^ cipher ^ encrypted[12+i]);
			i++;
			
			if (i == 12) break;
		}
		
		
		/* Get the message
		 * M1 = K + C1 + C0s
		 */
		for (i = 12; i < encrypted.length - 12 ; i++){
			message[j++] = (byte) (key[z++] ^ encrypted[12+i] ^ encrypted[i]);
			
			if (z == 12)	z = 0;
		}
		
		return new String(message);
	}
}