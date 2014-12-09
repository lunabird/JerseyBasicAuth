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
		Client c = Client.create();
		WebResource r = c.resource("http://localhost:8080/JerseyBasicAuth/rest/");
//		putChangePasswd(r);
//		putChangePasswd_json(r);
		postUpload(r);
	}
	
	public static void putChangePasswd(WebResource r){
		String response = r.path("OSBase/vmSysPwd")
                .queryParam("ip", "192.168.0.202")
                .queryParam("userName", "Administrator")
                .queryParam("passwd", "5413")
                .header("Authorization", "aHA6MTIzNDU2OjEz")
                .type(MediaType.APPLICATION_JSON)
                .put(String.class);
		System.out.println(response);
	}
	public static void putChangePasswd_json(WebResource r){
		JSONObject jo = new JSONObject();
		try {
			jo.put("ip", "192.168.0.202");
			jo.put("userName", "Administrator");
			jo.put("passwd", "5413");
			String response = r.path("OSBase/vmSysPwd_json")
					.entity(jo)
	                .header("Authorization", "aHA6MTIzNDU2OjEz")
	                .type(MediaType.APPLICATION_JSON)
	                .put(String.class);
			System.out.println(response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
				.header("Authorization", "aHA6MTIzNDU2OjQ3")
				.post(ClientResponse.class,multiPart);
		System.out.println("Response: " + clientResp.getClientResponseStatus()+"\n");
	}
	
	
	
	
}
