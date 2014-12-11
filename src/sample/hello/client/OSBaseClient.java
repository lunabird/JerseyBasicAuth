package sample.hello.client;
import java.io.File;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

public class OSBaseClient {
		
	public static void main(String[] args){
//		Client c = Client.create();
//		WebResource r = c.resource("http://119.90.140.60:8080/JerseyBasicAuth/rest/");
//		putChangePasswd(r);
//		putChangePasswd_json(r);
//		postUpload(r);
		getToken("hp","123456");
	}
	/**
	 * 用户获取认证
	 * @param uid 数据库cloudhost的userinfo表的userID字段
	 * @param pwd 数据库cloudhost的userinfo表的pwd字段
	 */
	public static void getToken(String uid,String pwd){
		Client c = Client.create();
		WebResource r = c.resource("http://localhost:8080/JerseyBasicAuth/Authentication");
		String response = r
                .queryParam("username", uid)
                .queryParam("pwd", pwd)
                .type(MediaType.APPLICATION_JSON)
                .get(String.class);
		System.out.println("token:"+response);
	}
	/**
	 * 使用queryParam发送数据执行修改密码操作
	 * @param r Client WebResource
	 */
	public static void putChangePasswd(WebResource r){
		String response = r.path("OSBase/vmSysPwd")
                .queryParam("ip", "192.168.0.202")
                .queryParam("userName", "Administrator")
                .queryParam("passwd", "5413")
                .header("Authentication", "aHA6MTIzNDU2OjEz")
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
}
