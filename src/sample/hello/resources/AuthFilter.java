package sample.hello.resources;

import java.sql.SQLException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import sample.DBOP.DBOperation;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class AuthFilter implements ContainerRequestFilter {
	/**
	 * Apply the filter : check input request, validate or not with user auth
	 * @param containerRequest The request from Tomcat server
	 * @return 
	 */
	public ContainerRequest filter(ContainerRequest containerRequest) throws WebApplicationException {
		//GET, POST, PUT, DELETE, ...
		String method = containerRequest.getMethod();
		// myresource/get/56bCA for example
		String path = containerRequest.getPath(true);

		//We do allow wadl to be retrieve
//		if(method.equals("GET") && (path.equals("application.wadl") || path.equals("application.wadl/xsd0.xsd"))){
//			return containerRequest;
//		}

		//Get the authentification passed in HTTP headers parameters
		String auth = containerRequest.getHeaderValue("authorization");
		
		//If the user does not have the right (does not provide any HTTP Basic Auth)
		if(auth == null){
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}

		//lap : loginAndPassword
		String[] lap = BasicAuth.decode(auth);
		System.out.println(lap[0]+"===="+lap[1]);
		//If login or password fail
		if(lap == null || lap.length != 2){
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}

		//DO YOUR DATABASE CHECK HERE (replace that line behind)...
		//User authentificationResult =  AuthentificationThirdParty.authentification(lap[0], lap[1]);
		/*String authentificationResult = "ok"; 
		//Our system refuse login and password
				if(authentificationResult == null){
					throw new WebApplicationException(Status.UNAUTHORIZED);
				}*/
				
		DBOperation opt = new DBOperation();
        String result=null;
		try {
			result = opt.judge(lap[0]);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(lap[1].equals(result)){
        	System.out.println("success+===============");
        	return containerRequest;
        }else{
        	System.out.println("401");
        	System.out.println("Ö´ÐÐ´íÎó");
        	return null;
        }
		
		
		

		//TODO : HERE YOU SHOULD ADD PARAMETER TO REQUEST, TO REMEMBER USER ON YOUR REST SERVICE...
		
		
	}
}
