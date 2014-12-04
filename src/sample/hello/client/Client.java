package sample.hello.client;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Decoder.BASE64Encoder;


public class Client extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Client() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = null;
		BufferedReader in = null;
		//String urlStr = "http://localhost:8080/MyJersey/rest/OSBase/vmSysPwd?uid=1&ip=192.168.0.217&userName=zz&passwd=123456";
		String urlStr = (String)request.getParameter("url");
		String user = (String)request.getParameter("username");
		String pass = (String)request.getParameter("pwd");
//		System.out.println(urlStr);
		try{
			String result = "";
	        String authStr = user + ":" + pass;
//	        System.out.println();
	        String authEncoded = new BASE64Encoder().encode(authStr.getBytes());
	       
	        // URL url = new URL ("http://ip:port/download_url");
	        URL url = new URL(urlStr);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("PUT");
	        connection.setDoOutput(true);
	        connection.setRequestProperty("authorization", "Basic "+authEncoded);
	
	        connection.connect();
	        // 获取所有响应头字段
	         Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
	        InputStream content = (InputStream)connection.getInputStream();
            in   =  new BufferedReader (new InputStreamReader (content));
            String line; 
            while ((line = in.readLine()) != null) {
            	result += line;
                System.out.println(line);
            }
            response.setContentType("text/html;charset=UTF-8");  
            response.getWriter().println("<html>");  
            response.getWriter().println("<head>");     
            response.getWriter().println("<title>返回信息</title>");      
            response.getWriter().println("</head>");    
            response.getWriter().println("<body>");     
            response.getWriter().println("得到的信息如下：");    
            response.getWriter().println(result);
            response.getWriter().println("</body>");    
            response.getWriter().println("</html>"); 
	    }
		
	    catch (Exception e) {
	    	System.out.println("发送get请求出现异常！");
	    	  	response.setContentType("text/html;charset=UTF-8");  
	            response.getWriter().println("<html>");  
	            response.getWriter().println("<head>");     
	            response.getWriter().println("<title>返回信息</title>");      
	            response.getWriter().println("</head>");    
	            response.getWriter().println("<body>");     
	            response.getWriter().println("错误信息：401");    
	            response.getWriter().println("</body>");    
	            response.getWriter().println("</html>"); 
	    	
	        e.printStackTrace();
	    }
		 finally{
	            try{
	                if(out!=null){
	                    out.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException ex){
	                ex.printStackTrace();
	            }
	        }
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
