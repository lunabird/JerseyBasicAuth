package sample.DBOP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBOperation {

	private DBConnManager dbcManager = null;
	private Connection con = null;
	
	public DBOperation() {
		dbcManager = DBConnManager.getInstance();
		con = dbcManager.getConnection();
		if (con == null) {
            System.out.println("Can't get connection");
            return;
        }
	}
	/**
	 *  修改IP后更新数据库
	 * @param oldIP
	 * @param changeToIP
	 * @throws SQLException 
	 */
	public boolean updateHostIP(String oldIP,String changeToIP) throws SQLException{
		Statement stmt = null;
		stmt = con.createStatement();
		//获得该用户可操作的所有主机
		String sql = "UPDATE  hostinfo  SET hostIP ='"+changeToIP+"' WHERE hostIP='"+oldIP+"'";
		boolean flag = stmt.execute(sql);
		//释放资源
		dbcManager.close();
		if(stmt!=null)
		{
			try{
				stmt.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return flag;
	}
	/**@author XQ
	 * 根据用户ID查询数据库获得该用户可以操作的主机及其所对应的软件中心地址列表
	 * @param userID 用户ID
	 * @return
	 * @throws SQLException
	 */
	public List<String[]> getRCAddr(String userID) throws SQLException {
		List<String[]> resultList = null;
		Statement stmt = null;
		ResultSet rs = null;
		stmt = con.createStatement();
		//获得该用户可操作的所有主机
		String sql = "SELECT hostID FROM userhost where userID=?";
		rs = dbcManager.query(sql, new String[]{userID});
		ArrayList<String> hostList = new ArrayList<String>();
		System.out.println("user contains hostID:::");
		while(rs.next()) {
			System.out.println(rs.getString(1));
			hostList.add(rs.getString(1));
		}
		if(hostList.size() > 0) {
			//若用户可操作的主机个数大于0
			resultList = new ArrayList<String[]>();
			for(String hostID: hostList) {
				String[] result = new String[2];
				System.out.println("hostID:" + hostID);
				//根据该主机ID获得主机的IP
				sql = "SELECT hostIP FROM hostinfo where hostID=? LIMIT 1";
				rs = dbcManager.query(sql, new String[]{hostID});
				if(rs.next()) {
					System.out.println("hostIP:" + rs.getString(1));
					result[0] = rs.getString(1);
				}
				//获得该主机所在的软件中心地址
				sql = "SELECT RCAddress FROM hostrc where hostID=?";
				rs = dbcManager.query(sql, new String[]{hostID});
				if(rs.next()) {
					System.out.println("RCAddress:" + rs.getString(1));
					result[1] = rs.getString(1);
				}
				resultList.add(result);
			}
		}
		//释放资源
		dbcManager.close();
		if(rs!=null)
		{
			try{
				rs.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		if(stmt!=null)
		{
			try{
				stmt.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return resultList;
	}
	
	
	
	/**@author ZXQ
	 * 根据用户名和主机ID查询数据库获得该主机所对应的软件中心地址
	 * @param userID 用户ID
	 * @param hostID  主机ID
	 * @return
	 */
	public  String[] getRCAddrByID(String userID,String hostID,String softName) throws SQLException {
		String[] result = null;
		Statement stmt = null;
		ResultSet rs = null;
		String RCAddress = null;
		String OS = null;
		stmt = con.createStatement();
		//根据用户ID获得该用户可操作的所有主机
		String sql = "SELECT hostID FROM userhost where userID=?";
		rs = dbcManager.query(sql, new String[]{userID});
		ArrayList<String> hostList = new ArrayList<String>();
		System.out.println("user contains hostID:::");
		while(rs.next()) {
			System.out.println(rs.getString(1));
			hostList.add(rs.getString(1));
		}
		if(hostList.contains(hostID))  {
			//若要操作的主机在用户可操作的所有主机中
			result = new String[3];
			//获得要操作主机的IP
			sql = "SELECT hostIP,OS FROM hostinfo where hostID=? LIMIT 1";
			rs = dbcManager.query(sql, hostID);
			if(rs.next()) {
				System.out.println("hostIP:" + rs.getString(1));
				result[0] = rs.getString(1);
				OS = rs.getString(2);
				System.out.println("OS:" + OS);
			}
			//获得要操作主机所在的软件中心的地址 
			sql = "SELECT RCAddress FROM hostrc where hostID=?";
			rs = dbcManager.query(sql, hostID);
			if(rs.next()) {
				System.out.println("RCAddress:" + rs.getString(1));
				RCAddress = rs.getString(1);
				result[1] = RCAddress;
			}
			//获得要安装的软件的位置
			sql = "SELECT softPath FROM rcinfo where RCAddress= ? and softName=? and OS=?";
			rs = dbcManager.query(sql, RCAddress,softName.toLowerCase(),OS);
			if(rs.next()) {
				System.out.println("softPath:" + rs.getString(1));
				result[2] = rs.getString(1);
			}
		}
		//释放资源
		dbcManager.close();
		if(rs!=null)
		{
			try{
				rs.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		if(stmt!=null)
		{
			try{
				stmt.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**@author XQ
	 * 根据用户名和主机IP查询数据库获得该主机所对应的软件中心地址
	 * @param userID
	 * @param hostIP
	 * @return
	 * @throws SQLException
	 */
	public String[] getRCAddrByIP(String userID,String hostIP,String softName) throws SQLException {
		String[] result = null;
		String hostID = null;
		String RCAddress = null;
		String OS = null;
		ArrayList<String>  hostIDList = new ArrayList<String>();
		//根据主机ip获得该主机的hostID
		Statement stmt = null;
		ResultSet rs = null;
		stmt = con.createStatement();
		String sql = "SELECT hostID,OS FROM hostinfo where hostIP=?";
		rs = dbcManager.query(sql, hostIP);
		if(rs.next()) {
			System.out.println("hostID:" + rs.getString(1));
			hostID = rs.getString(1);
			OS = rs.getString(2);
			System.out.println("OS:" + OS);
		}
		//根据userID获得该用户可操作的所有主机
		sql = "SELECT hostID FROM userhost where userID=?";
		rs = dbcManager.query(sql, userID);
		System.out.println("user can operator hostList:");
		while(rs.next()) {
			System.out.println(rs.getString(1));
			hostIDList.add(rs.getString(1));
			
		}
		if(hostIDList.contains(hostID)) {
			//若该主机在用户可操作的所有主机中
			result = new String[2];
			//获得该主机所在的软件中心的地址
			sql = "SELECT RCAddress FROM hostrc where hostID=?";
			rs = dbcManager.query(sql, hostID);
			if(rs.next()) {
				System.out.println("RCAddress:" + rs.getString(1));
				RCAddress = rs.getString(1);
				result[0] = RCAddress;
				
			}
			//获得要安装软件的路径
			sql = "SELECT softPath FROM rcinfo where RCAddress=? and softName=? and OS=?";
			rs = dbcManager.query(sql, RCAddress,softName.toLowerCase(),OS);
			if(rs.next()) {
				System.out.println("softPath:" + rs.getString(1));
				result[1] = rs.getString(1);
			}
			
		}
		//释放资源
		dbcManager.close();
		if(rs!=null)
		{
			try{
				rs.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		if(stmt!=null)
		{
			try{
				stmt.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public void test() throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		stmt = con.createStatement();
		String sql = "SELECT * FROM userinfo";
		rs = dbcManager.query(sql, null);
		while(rs.next()) {
			System.out.println(rs.getString(1));
		}
		
		sql = "SELECT * FROM hostinfo";
		rs = dbcManager.query(sql, null);
		while(rs.next()) {
			System.out.println(rs.getString(1)+":"+rs.getString(2));
		}
	
		sql = "SELECT * FROM hostrc";
		rs = dbcManager.query(sql, null);
		while(rs.next()) {
			System.out.println(rs.getString(1)+":"+rs.getString(2));
		}
		
		sql = "SELECT * FROM rcinfo";
		rs = dbcManager.query(sql, null);
		while(rs.next()) {
			System.out.println(rs.getString(1)+":"+rs.getString(2)+":" + rs.getString(3) + ":" + rs.getString(4));
		}
	
		sql = "SELECT * FROM userhost";
		rs = dbcManager.query(sql, null);
		while(rs.next()) {
			System.out.println(rs.getString(1)+":"+rs.getString(2));
		}
	}
	/**@author WZY
	 * 根据用户名的ID得到 用于的密码
	 * @param userID 用户ID
	 * @param hostID  主机ID
	 * @return
	 */
	public  String judge(String userID) throws SQLException {
		String result = null;
		ResultSet rs = null;
		Statement stmt = null;
		stmt = con.createStatement();
		//根据用户ID获得该用户的密码
		System.out.println(userID+"---------");
		String sql = "SELECT * FROM userinfo where userID = '"+userID+"'";
		rs = stmt.executeQuery(sql);
		while(rs.next()) {
			System.out.println(rs.getString(1));
			result = rs.getString("pwd");
		}
		
		dbcManager.close();
		if(rs!=null)
		{
			try{
				rs.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		if(stmt!=null)
		{
			try{
				stmt.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}

	public static void main(String[] args) {
		
		DBOperation dbop = new DBOperation();
		
//		try {
//			dbop.getRCAddr("2");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		
//		try {
//			dbop.getRCAddrByID("2","3");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		try {
//		dbop.getRCAddrByID("hp","wyqAgent","mysql");
//		
//		} catch (SQLException e) {
//		e.printStackTrace();
//		}	
		
//		try {
//		dbop.getRCAddrByIP("hp","192.168.0.215","mysql");
//		
//		} catch (SQLException e) {
//		e.printStackTrace();
//		}	
//		
		try {
			dbop.updateHostIP("192.168.0.555", "192.168.0.122");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
