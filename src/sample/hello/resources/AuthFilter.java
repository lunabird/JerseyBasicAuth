package sample.hello.resources;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	private static boolean flag = false;
	@SuppressWarnings("null")
	public ContainerRequest filter(ContainerRequest containerRequest) {
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

		//判断时间是否过期
		String authentication = null;
		DBOperation opt = new DBOperation();
		String time = null;
		String pwd = null;
		String random = null;
		try {
			time = opt.judge(lap[0])[2];//从数据库中取出存放时间
			pwd = opt.judge(lap[0])[1];//从数据库中取出密码
			random = opt.judge(lap[0])[3];//从数据库中取出随机数
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String [] sql = time.split(" ");
		String [] sqltime = sql[1].split(":");
		int h1 = Integer.parseInt(sqltime[0]);
		int m1 = Integer.parseInt(sqltime[1]);
		
		int total1 = h1 * 60 + m1;
		//获取当前时间
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
		String tt = dateFormat.format(now);
		String [] t = tt.split(" ");
		String [] te = t[1].split(":");
		int h2 = Integer.parseInt(te[0]);
		int m2 = Integer.parseInt(te[1]);
	
		int total2 = h2 * 60 + m2;
		
		if(sql[0].equals(t[0])){
				if((total2-total1)<=10){
					System.out.println("不用重新登录");
					//判断密码与随机数是否匹配
					if(lap[1].equals(pwd)&&(lap[2].equals(random))){
						System.out.println("containerRequest:"+containerRequest.getAbsolutePath());
						containerRequest.setUris(containerRequest.getBaseUri(), containerRequest.getAbsolutePathBuilder().queryParam("uid",lap[0]).build());
						return containerRequest;
			        }else{
			        	System.out.println("401");
			        	System.out.println("执行错误");
			        	return null;
			        }
				}else{
					flag = true;
					System.out.println("Token已过期，请重新登录");	
					return null;
				}
		}else{
			flag = true;
			System.out.println("Token已过期，请重新登录");
			return null;
		}
		

		//DO YOUR DATABASE CHECK HERE (replace that line behind)...
		//User authentificationResult =  AuthentificationThirdParty.authentification(lap[0], lap[1]);
		
			
        
        }
		
		
		

		//TODO : HERE YOU SHOULD ADD PARAMETER TO REQUEST, TO REMEMBER USER ON YOUR REST SERVICE...
	 public void init(FilterConfig config) throws ServletException { 
	        //读取错误信息提示页面路径  
	       boolean flag = false;
	    } 
	 
	 public void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException{
					request.getRequestDispatcher("/subdir/authentication.jsp");
				}
}
