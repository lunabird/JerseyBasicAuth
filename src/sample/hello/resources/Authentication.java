package sample.hello.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import sample.DBOP.DBOperation;

import Decoder.BASE64Encoder;

public class Authentication extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Authentication() {
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
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String userID= (String)request.getParameter("username");
		String pwd = (String)request.getParameter("pwd");
		String authentication = null;
		DBOperation opt = new DBOperation();
		try {
			String resultPwd = opt.judge(userID)[1];
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
			String time = dateFormat.format(now); 
			
			Random  rand = new Random();
			int num = rand.nextInt(99);
			
			int flag = opt.updateAuthentication(userID, time, num);//flag为1表示写入成功
			System.out.println("flag:"+flag);
			if(flag == 1){
				String resultRandom = opt.judge(userID)[3];
				if(pwd.equals(resultPwd)){	
		        	String auth = userID + ":" + pwd +":" + resultRandom;
		        	authentication = new BASE64Encoder().encode(auth.getBytes());
		        	System.out.println(authentication);
		        	request.getSession().setAttribute("authentication",  authentication);
		        	JSONObject ja = new JSONObject();
		        	ja.put("authentication", authentication);
		        	out.print(ja);
		        	out.flush();
		        	out.close();
//		        	request.getRequestDispatcher("/subdir/login.jsp?authentication="+authentication).forward(request, response);
				}
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
