package sample.hello.client;
import java.io.File;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

public class OSBaseClient1 {
		
	public static void main(String[] args){
		Client c = Client.create();
		WebResource r = c.resource("http://localhost:8080/JerseyBasicAuth/rest/");
		putChangePasswd(r);
//		putChangePasswd_json(r);
//		postUpload(r);
		
		//getToken("hp",pwd,time);
	}
	/**
	 * 用户获取认证
	 * @param uid 数据库cloudhost的userinfo表的userID字段
	 * @param pwd 数据库cloudhost的userinfo表的pwd字段
	 */
	/*public static void getToken(String uid,String pwd){
		Client c = Client.create();
		WebResource r = c.resource("http://localhost:8080/JerseyBasicAuth/Authentication");
		String response = r
                .queryParam("username", uid)
                .queryParam("pwd", pwd)
                .type(MediaType.APPLICATION_JSON)
                .get(String.class);
		System.out.println("token:"+response);
	}*/
	/**
	 * 使用queryParam发送数据执行修改密码操作
	 * @param r Client WebResource
	 */
	public static void putChangePasswd(WebResource r){
		String password = "123456";
		String time = "";
		password = convertMD5(string2MD5(password));//发送前加密
		time = CurrentTime();//时间戳
		System.out.println("password="+password);
		System.out.println("time = "+time);
		String response = r.path("OSBase/vmSysPwd")
                .queryParam("ip", "192.168.0.202")
                .queryParam("userName", "Administrator")
                .queryParam("passwd", "5413")
                .header("password", password)
                .header("time", time)
                .type(MediaType.APPLICATION_JSON)
                .put(String.class);
		System.out.println(response);
	}
	/**
	 * 使用json对象发送数据执行修改密码操作
	 * @param r Client WebResource
	 */
	public static void putChangePasswd_json(WebResource r){
		JSONObject jo = new JSONObject();
		try {
			jo.put("ip", "192.168.0.202");
			jo.put("userName", "Administrator");
			jo.put("passwd", "5413");
			String response = r.path("OSBase/vmSysPwd_json")
					.entity(jo)
	                .header("Authentication", "aHA6MTIzNDU2OjEz")
	                .type(MediaType.APPLICATION_JSON)
	                .put(String.class);
			System.out.println(response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 发送虚拟机执行脚本请求
	 * @param r Client WebResource
	 */
	public static void postUpload(WebResource r){
		final File fileToUpload = new File("E:/cloudhost.sql");
		final FormDataMultiPart multiPart = new FormDataMultiPart();
		String ip = "119.90.140.59";
		if (fileToUpload != null) 
		{
			((FormDataMultiPart) multiPart.bodyPart(new FileDataBodyPart("upload", fileToUpload,
					MediaType.APPLICATION_OCTET_STREAM_TYPE)))
					.field("ip",ip, MediaType.TEXT_PLAIN_TYPE);
		}
		final ClientResponse clientResp = r.path("vmScript/upload")
				.type(MediaType.MULTIPART_FORM_DATA_TYPE)
				.header("Authentication", "aHA6MTIzNDU2OjQ3")
				.post(ClientResponse.class,multiPart);
		System.out.println("Response: " + clientResp.getClientResponseStatus()+"\n");
	}	
	/**
	 * MD5加码 生成32位md5码
	 */
	private static String string2MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
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

	/**
	 * 加密解密算法 执行一次加密，两次解密
	 */
	private static String convertMD5(String inStr) {

		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;

	}
	
	/**
	 *获取当前时间
	 */
	private static String CurrentTime(){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String time = df.format(now);
		return time;
	}
}
