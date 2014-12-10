package sample.hello.resources;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.DatatypeConverter;


import Decoder.BASE64Decoder;



public class BasicAuth {
	/**
	 * Decode the basic auth and convert it to array login/password
	 * @param auth The string encoded authentification
	 * @return The login (case 0), the password (case 1)
	 * @throws UnsupportedEncodingException 
	 */
	public static String[] decode(String auth)  {
		//Replacing "Basic THE_BASE_64" to "THE_BASE_64" directly
		auth = auth.replaceFirst("[B|b]asic ", "");

		//Decode the Base64 into byte[]
		String decodedBytes= null;
		try {
			System.out.println(auth+" +++++++");
			decodedBytes = new String(new BASE64Decoder().decodeBuffer(auth));
			System.out.println(decodedBytes+"--------");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//If the decode fails in any case
		if(decodedBytes.equals("null")  || decodedBytes.length() == 0){
			return null;
		}

		//Now we can convert the byte[] into a splitted array :
		//	- the first one is login,
		//	- the second one password
		return new String(decodedBytes).split(":", 3);
	}
}