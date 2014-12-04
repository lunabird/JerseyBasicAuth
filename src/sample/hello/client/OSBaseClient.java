package sample.hello.client;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class OSBaseClient {
		
	public static void main(String[] args){
		
		
		Client c = Client.create();
		WebResource r = c.resource("http://localhost:8080/MyJersey/rest/");
		putChangePasswd(r);
	}
	
	public static void putChangePasswd(WebResource r){
		String response = r.path("OSBase/chgPwd")
                .queryParam("uid", "hp0831")
                .queryParam("ip", "192.168.0.202")
                .queryParam("userName", "Administrator")
                .queryParam("passwd", "5413")
                .type(MediaType.APPLICATION_JSON)
                .put(String.class);
		System.out.println(response);
	}
}
