package sample.DBOP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnManager {
	
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/cloudhost";
	private static String user = "root";
	private static String password = "123456";
	
	//数据库管理对象
	private static DBConnManager dbcMananger = null; 
	//数据库连接对象
	private static Connection conn=null;
	//数据库预编译对象
    private PreparedStatement ps=null;
	//结果集
	private ResultSet rs=null;
	
	
	public DBConnManager() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println("数据库driver出错");
		}
		try {
			conn =  DriverManager.getConnection(url,user,password);
		} catch (SQLException e) {
			System.out.println("数据库connection出错");
		}
	}
	
	/**@author ZXQ
	 * 获取数据库管理对象实例
	 * @return
	 */
	 public static DBConnManager getInstance() {
		if(dbcMananger == null) {
			dbcMananger = new DBConnManager();
		}
		return dbcMananger;
	}
	
	/**@author ZXQ
	 * 获取数据库连接对象
	 * @return
	 */
	public static Connection getConnection() {
		return conn;
	}
	
	/**@author ZXQ
	 * @param sql 数据库查询语句
	 * @param paras 参数列表（可以传参数,也可以不传参数,不传即为NULL,以字符串数组形式传递）
	 * @return 返回查询的结果集
	 */
	public ResultSet query(String sql,String... paras) {
		
		try {
			ps = conn.prepareStatement(sql);
			if(paras != null) {
				for(int i=0;i<paras.length;i++) {
					ps.setString(i+1, paras[i]);
				}
			}
			rs = ps.executeQuery();//执行query
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;	
	}
	
//	/**@author ZXQ
//	 * @param sql 数据库增、删、改语句
//	 * @param paras 参数列表（可以传参数,也可以不传参数,不传即为NULL,以字符串数组形式传递）
//	 * @return 返回受影响的行数
//	 **/
//	public int update(String sql,String...paras) {
//		int result = 0;
//		try {
//			ps = conn.prepareStatement(sql);
//			
//			if(paras != null) {
//				for(int i=0;i<paras.length;i++) {
//					ps.setString(i+1, paras[i]);
//				}
//			}
//			
//			result = ps.executeUpdate();//执行update
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		if(ps != null) {
//			try {
//				ps.close(); //释放资源
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}			
//		}	
//		return result;
//	}
	
	
	/**@author ZXQ
	 * 释放资源（释放ResultSet和PreparedStatement）
	 */ 
	public void close() {
		if(rs!=null)
		{
			try{
				rs.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		if(ps!=null)
		{
			try{
				ps.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**@author ZXQ
	 * 释放资源（释放ResultSet,PreparedStatement和Connection）
	 */ 
	public void closeAll() {
		if(rs!=null)
		{
			try{
				rs.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		if(ps!=null)
		{
			try{
				ps.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		if(conn!=null)
		{
			try{
				conn.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
