package edu.xidian.enc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;

import sample.DBOP.DBOperation;

import edu.xidian.message.Message;
import edu.xidian.message.MsgType;

public class AESUtil {
	//private final  static String filePath = "src/key.txt";
	/**@author WZY
	 * @throws SQLException 
	 * @content 要加密的内容
	 * @password 加密共享秘钥
	 * show加解密算法
	 *
	 */
	public static byte[] encrypt(String content, String hostIP) throws SQLException {  
        try {   
        		DBOperation db = new DBOperation();        		
        	    String pwd = db.getAgentKey(hostIP);//得到的密文秘钥
        	    String password = null;
				try {
					password = new String(new BASE64Decoder().decodeBuffer(pwd));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//得到明文秘钥
                KeyGenerator kgen = KeyGenerator.getInstance("AES");  
                kgen.init(128, new SecureRandom(password.getBytes()));  
                SecretKey secretKey = kgen.generateKey();  
                byte[] enCodeFormat = secretKey.getEncoded();  
                SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");  
                Cipher cipher = Cipher.getInstance("AES");// 创建密码器  
                byte[] byteContent = content.getBytes("iso-8859-1");  
                cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化  
                byte[] result = cipher.doFinal(byteContent);  
                return result; // 加密  
        } catch (NoSuchAlgorithmException e) {  
                e.printStackTrace();  
        } catch (NoSuchPaddingException e) {  
                e.printStackTrace();  
        } catch (InvalidKeyException e) {  
                e.printStackTrace();  
        } catch (UnsupportedEncodingException e) {  
                e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
                e.printStackTrace();  
        } catch (BadPaddingException e) {  
                e.printStackTrace();  
        }  
        return null;  
} 
	
	/**@author WZY
	 * @content 要解密的内容
	 * @password 加密共享秘钥，在这个地方先写死了
	 * show加解密算法
	 *
	 */
	public static byte[] decrypt(byte[] content, String hostIP) {  
        try {  
	        	DBOperation db = new DBOperation();        		
	    	    String pwd = null;
				try {
					pwd = db.getAgentKey(hostIP);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//得到的密文秘钥
	    	    String password = null;
				try {
					password = new String(new BASE64Decoder().decodeBuffer(pwd));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//得到明文秘钥
                 KeyGenerator kgen = KeyGenerator.getInstance("AES");  
                 kgen.init(128, new SecureRandom(password.getBytes()));  
                 SecretKey secretKey = kgen.generateKey();  
                 byte[] enCodeFormat = secretKey.getEncoded();  
                 SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");              
                 Cipher cipher = Cipher.getInstance("AES");// 创建密码器  
                cipher.init(Cipher.DECRYPT_MODE, key);// 初始化  
                byte[] result = cipher.doFinal(content);  
                return result; // 加密  
        } catch (NoSuchAlgorithmException e) {  
                e.printStackTrace();  
        } catch (NoSuchPaddingException e) {  
                e.printStackTrace();  
        } catch (InvalidKeyException e) {  
                e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
                e.printStackTrace();  
        } catch (BadPaddingException e) {  
                e.printStackTrace();  
        }  
        return null;  
}  
	
	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws Exception {
		
		/*Message msg = new Message(MsgType.setupTomcat, "12","12345678");
		String content = SerializeUtil.serialize(msg);  
		//String password = "12345678"; 
		String hostIP = "127.0.0.1";
		//加密  
		System.out.println("加密前：" + content);  
		byte[] encryptResult = encrypt(content, hostIP);  
		String b = new String(encryptResult,"ISO-8859-1");
		System.out.println("加密后："+b);
		//解密  
		byte[] bb = b.getBytes("ISO-8859-1");
		byte[] decryptResult = decrypt(bb,hostIP);  
		System.out.println("解密后：" + new String(decryptResult,"ISO-8859-1"));
		msg = (Message)SerializeUtil.deserialize( new String(decryptResult,"ISO-8859-1"));
		System.out.println(msg.getType().name());*/
		String a = "123456";
		
		System.out.println(convertMD5(string2MD5(a)));
		//System.out.println(convertMD5(convertMD5(a)));
	}

	private static String convertMD5(String inStr) {

		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;

	}
	public static String string2MD5(Object  obj) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = obj.toString().toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();

	}

}
