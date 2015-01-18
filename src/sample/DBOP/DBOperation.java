package sample.DBOP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.xidian.enc.MD5Util;
import edu.xidian.enc.SerializeUtil;
import edu.xidian.message.Message;
import edu.xidian.message.MsgType;

public class DBOperation {

	private DBConnManager dbcManager = null;
	private Connection con = null;
	
	public DBOperation() {
		dbcManager = (new DBConnManager()).getInstance();
		con = dbcManager.getConnection();
		if (con == null) {
            System.out.println("Can't get connection");
            return;
        }
	}
	/**@author hp
	 * 修改IP后更新数据库,用于基础环境配置OSBase的修改IP操作
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
	/**
	 * 插入新的操作(op)到数据库，先插一条操作信息到op表，再在opinfo表里自动插入一条开始执行的记录。
	 * 基础环境配置，2步
	 * 安装，5步(开始执行-->正在下载-->下载完成-->正在安装-->安装结果码)
	 * 卸载，脚本执行，3步
	 * 更新，5步（与安装相同）
	 * 但是它们的第一步都是“开始执行”-->因为要支持各种不同的操作系统，所以数据库里面用英文存储
	 * @param hostIP 执行命令的虚拟机IP
	 * @param opName 操作名，必须是是MsgType里面的枚举名字
	 * @return opID 操作ID
	 * @throws SQLException
	 */
	public int insertOperation(String hostIP,String opName) throws SQLException{
		Statement stmt = null;
		stmt = con.createStatement();
		ResultSet rs = null;
		//插入op表
		String sql = "INSERT INTO op (hostIP,opName) VALUES ('"+hostIP+"','"+opName+"')";
		stmt.executeUpdate(sql);
		//获取opID
		String sql1 = "select opID from op order by opID DESC limit 1";
		rs = stmt.executeQuery(sql1);
		int opID = -1;
		if(rs.next()){
			opID = rs.getInt(1);
		}
		//计算时间
		java.util.Date date=new java.util.Date();
		Timestamp time=new Timestamp(date.getTime());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//插入opinfo表
		String sql2 = "INSERT INTO opinfo (opID,status,time) VALUES ("+opID+",'start executing','"+df.format(time)+"')";
		stmt.executeUpdate(sql2);	
		//释放资源
		dbcManager.close();
		if(stmt!=null)
		{
			try{
				stmt.close();
				rs.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return opID;
	}
	/**
	 * 在opinfo表里更新操作状态信息。后台线程TCPServer一直在监听Agent，如果该操作状态发生了迁移，就在opinfo表里面增加一条记录。
	 * @param opID
	 * @param status
	 * @return 
	 * @throws SQLException
	 */
	public boolean updateOpStatus(int opID,String status) throws SQLException{
		Statement stmt = null;
		stmt = con.createStatement();
		// 插入新纪录到opinfo表
		// 计算时间
		java.util.Date date = new java.util.Date();
		Timestamp time = new Timestamp(date.getTime());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "INSERT INTO opinfo (opID,status,time) VALUES (" + opID+ ",'" + status + "','" + df.format(time) + "')";
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
	/**
	 * 获取操作执行的当前状态
	 * @param opID
	 * @return
	 * @throws SQLException
	 */
	public String getOpStatus(int opID) throws SQLException{
		Statement stmt = null;
		ResultSet rs = null;
		stmt = con.createStatement();
		String status = null;
		//查询opinfo表,获取最新的一条记录
		String sql = "SELECT status FROM opinfo WHERE opID="+opID+" ORDER BY time DESC LIMIT 1";
		System.out.println(sql);
		rs = stmt.executeQuery(sql);
		if(rs.next()){
			status = rs.getString(1);
		}
		
		//获得安装包名字
		//**************************************************************//
		//首先查询op表，获得hostIp和opName，得到该软件名
		String sql3 = "SELECT hostIp,opName FROM op WHERE opID="+opID;
		rs = stmt.executeQuery(sql3);
		String hostIp = null;
		String sName = null;//软件名字
		if(rs.next()){
			hostIp = rs.getString("hostIp");
			sName = rs.getString("opName").substring(5);
		}
		//再根据软件名字查rcinfo表，获取该软件对应的安装包名spName
		String[] temp = getRCAddrByIP("hp", hostIp, sName);
		String spName = temp[1];
		//**************************************************************//
			
		
		//如果status是“正在下载”，则要向Agent询问下载进度
		if(status.contains("downloading")){			
			//将spName发送给Agent,得到当前已经下载的大小
			int alreadyDown = sendGetDownloadProgressMsg(opID,hostIp,spName);
			//计算下载进度的百分比
			//先查该安装包的大小
			String q1 = "SELECT softSize FROM rcinfo WHERE softPath = '"+spName+"'";
			rs = stmt.executeQuery(q1);
			int softTotalSize = -1;
			if(rs.next()){
				softTotalSize = Integer.parseInt(rs.getString(1));
			}
			//算一下除法 alreadyDown/softTotalSize 注意单位要统一，都是KB
			float progress = (float)alreadyDown/softTotalSize;
			status = status+","+progress;
		} else if(status.contains("installing")){//如果status是“正在安装”，则要自己计算安装进度
			//首先查opinfo表，得到“下载完成的时间”
			String q2 = "SELECT time FROM opinfo WHERE opID="+opID+" AND status = 'download completed'";
			rs = stmt.executeQuery(q2);
			Timestamp startTime = null;
			if(rs.next()){
				startTime = rs.getTimestamp("time"); 
			}
			System.out.println("*************************startTime:"+startTime+","+startTime.getTime());
			//再从数据表rcinfo表中获得softTime,即软件安装的经验安装时间
			String q3 = "SELECT softTime FROM rcinfo WHERE softPath='"+spName+"'";
			rs = stmt.executeQuery(q3);
			int totalInstallTime = -1;
			if(rs.next()){
				totalInstallTime = rs.getInt(1);
			}
			//计算安装进度，做一下除法 (now-startTime)/totalInstallTime
			java.util.Date date = new java.util.Date();
			Long now = date.getTime();	
			System.out.println("*************************now:"+now);
			Long startT = startTime.getTime();
			float progress =  (now-startT)/(float)(totalInstallTime*1000);
			status = status+","+progress;
		}
		//释放资源
		dbcManager.close();
		if(stmt!=null)
		{
			try{
				stmt.close();
				rs.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		System.out.println("*************************get status:"+status);
		return status;
	}
	/**
	 * 向agent发送消息，获取下载软件的进度，返回已经下载的软件的大小，单位是kb
	 * @param opID
	 * @param ip
	 * @param spName
	 * @return
	 */
	public int sendGetDownloadProgressMsg(int opID,String ip, String spName) {
		int alreadyDown = -1;
		// 发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9500);
			String values = spName;
			Message msg = new Message(MsgType.getDownloadStatus, opID+"", values);
			// 加密
			String datatemp = SerializeUtil.serialize(msg);
			String str = MD5Util.convertMD5(datatemp);
			// 传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			// 获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			str = (String) ois.readObject();
			// 解密
			String str2 = MD5Util.convertMD5(str);
			msg = (Message) SerializeUtil.deserialize(str2);
			if (msg.getType().equals(MsgType.getDownloadStatus)) {
				String ret = (String) msg.getValues();
				System.out.println(ret);
				alreadyDown = Integer.parseInt(ret);
			}
			socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return alreadyDown;
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
	public  String[] judge() throws SQLException {
		String result[] = new String[2];
		ResultSet rs = null;
		Statement stmt = null;
		stmt = con.createStatement();
		//根据用户ID获得该用户的密码
		String sql = "SELECT pwd,userID  FROM userinfo where userID='hp'";
		rs = stmt.executeQuery(sql);
		
		while(rs.next()) {
			
			result[0] = rs.getString("userID");
			result[1] = rs.getString("pwd");
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
	
	/**@author WZY
	 * 根据用户名的ID得到 用于的密码
	 * @param userID 用户ID
	 * @param time  系统当前时间
	 * @param num 生成的随机数
	 * @return
	 */
	public int  updateAuthentication(String userID,String time, int num) throws SQLException{
		Statement stmt = null;
		stmt = con.createStatement();
		//获得该用户可操作的所有主机
		String sql = "UPDATE  userinfo  SET  time = '"+time+"' , "+"random = "+num+" WHERE userID='"+userID+"'";
		System.out.println(sql);
		int  flag =  stmt.executeUpdate(sql);
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

	/**@author WZY
	 * 根据AgentIp得到服务器端与agent共享秘钥
	 * @return
	 */
	public  String getAgentKey(String hostIP) throws SQLException {
		String result[] = new String[1];
		ResultSet rs = null;
		Statement stmt = null;
		stmt = con.createStatement();
		//根据用户ID获得该用户的密码
		String sql = "SELECT * FROM hostinfo where hostIP='"+hostIP+"'";
		rs = stmt.executeQuery(sql);
		
		while(rs.next()) {			
			result[0] = rs.getString("key");
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
		return result[0];
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
//		try {
//			dbop.updateHostIP("192.168.0.555", "192.168.0.122");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		try {
//			dbop.insertOperation("192.168.0.555", "setupOracle");
//			dbop.updateOpStatus(3, "installing");
			dbop.getOpStatus(3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
