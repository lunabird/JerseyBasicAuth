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
	 * �޸�IP��������ݿ�,���ڻ�����������OSBase���޸�IP����
	 * @param oldIP
	 * @param changeToIP
	 * @throws SQLException 
	 */
	public boolean updateHostIP(String oldIP,String changeToIP) throws SQLException{
		Statement stmt = null;
		stmt = con.createStatement();
		//��ø��û��ɲ�������������
		String sql = "UPDATE  hostinfo  SET hostIP ='"+changeToIP+"' WHERE hostIP='"+oldIP+"'";
		boolean flag = stmt.execute(sql);
		//�ͷ���Դ
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
	 * �����µĲ���(op)�����ݿ⣬�Ȳ�һ��������Ϣ��op������opinfo�����Զ�����һ����ʼִ�еļ�¼��
	 * �����������ã�2��
	 * ��װ��5��(��ʼִ��-->��������-->�������-->���ڰ�װ-->��װ�����)
	 * ж�أ��ű�ִ�У�3��
	 * ���£�5�����밲װ��ͬ��
	 * �������ǵĵ�һ�����ǡ���ʼִ�С�-->��ΪҪ֧�ָ��ֲ�ͬ�Ĳ���ϵͳ���������ݿ�������Ӣ�Ĵ洢
	 * @param hostIP ִ������������IP
	 * @param opName ����������������MsgType�����ö������
	 * @return opID ����ID
	 * @throws SQLException
	 */
	public int insertOperation(String hostIP,String opName) throws SQLException{
		Statement stmt = null;
		stmt = con.createStatement();
		ResultSet rs = null;
		//����op��
		String sql = "INSERT INTO op (hostIP,opName) VALUES ('"+hostIP+"','"+opName+"')";
		stmt.executeUpdate(sql);
		//��ȡopID
		String sql1 = "select opID from op order by opID DESC limit 1";
		rs = stmt.executeQuery(sql1);
		int opID = -1;
		if(rs.next()){
			opID = rs.getInt(1);
		}
		//����ʱ��
		java.util.Date date=new java.util.Date();
		Timestamp time=new Timestamp(date.getTime());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//����opinfo��
		String sql2 = "INSERT INTO opinfo (opID,status,time) VALUES ("+opID+",'start executing','"+df.format(time)+"')";
		stmt.executeUpdate(sql2);	
		//�ͷ���Դ
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
	 * ��opinfo������²���״̬��Ϣ����̨�߳�TCPServerһֱ�ڼ���Agent������ò���״̬������Ǩ�ƣ�����opinfo����������һ����¼��
	 * @param opID
	 * @param status
	 * @return 
	 * @throws SQLException
	 */
	public boolean updateOpStatus(int opID,String status) throws SQLException{
		Statement stmt = null;
		stmt = con.createStatement();
		// �����¼�¼��opinfo��
		// ����ʱ��
		java.util.Date date = new java.util.Date();
		Timestamp time = new Timestamp(date.getTime());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "INSERT INTO opinfo (opID,status,time) VALUES (" + opID+ ",'" + status + "','" + df.format(time) + "')";
		boolean flag = stmt.execute(sql);
		//�ͷ���Դ
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
	 * ��ȡ����ִ�еĵ�ǰ״̬
	 * @param opID
	 * @return
	 * @throws SQLException
	 */
	public String getOpStatus(int opID) throws SQLException{
		Statement stmt = null;
		ResultSet rs = null;
		stmt = con.createStatement();
		String status = null;
		//��ѯopinfo��,��ȡ���µ�һ����¼
		String sql = "SELECT status FROM opinfo WHERE opID="+opID+" ORDER BY time DESC LIMIT 1";
		System.out.println(sql);
		rs = stmt.executeQuery(sql);
		if(rs.next()){
			status = rs.getString(1);
		}
		
		//��ð�װ������
		//**************************************************************//
		//���Ȳ�ѯop�����hostIp��opName���õ��������
		String sql3 = "SELECT hostIp,opName FROM op WHERE opID="+opID;
		rs = stmt.executeQuery(sql3);
		String hostIp = null;
		String sName = null;//�������
		if(rs.next()){
			hostIp = rs.getString("hostIp");
			sName = rs.getString("opName").substring(5);
		}
		//�ٸ���������ֲ�rcinfo����ȡ�������Ӧ�İ�װ����spName
		String[] temp = getRCAddrByIP("hp", hostIp, sName);
		String spName = temp[1];
		//**************************************************************//
			
		
		//���status�ǡ��������ء�����Ҫ��Agentѯ�����ؽ���
		if(status.contains("downloading")){			
			//��spName���͸�Agent,�õ���ǰ�Ѿ����صĴ�С
			int alreadyDown = sendGetDownloadProgressMsg(opID,hostIp,spName);
			//�������ؽ��ȵİٷֱ�
			//�Ȳ�ð�װ���Ĵ�С
			String q1 = "SELECT softSize FROM rcinfo WHERE softPath = '"+spName+"'";
			rs = stmt.executeQuery(q1);
			int softTotalSize = -1;
			if(rs.next()){
				softTotalSize = Integer.parseInt(rs.getString(1));
			}
			//��һ�³��� alreadyDown/softTotalSize ע�ⵥλҪͳһ������KB
			float progress = (float)alreadyDown/softTotalSize;
			status = status+","+progress;
		} else if(status.contains("installing")){//���status�ǡ����ڰ�װ������Ҫ�Լ����㰲װ����
			//���Ȳ�opinfo���õ���������ɵ�ʱ�䡱
			String q2 = "SELECT time FROM opinfo WHERE opID="+opID+" AND status = 'download completed'";
			rs = stmt.executeQuery(q2);
			Timestamp startTime = null;
			if(rs.next()){
				startTime = rs.getTimestamp("time"); 
			}
			System.out.println("*************************startTime:"+startTime+","+startTime.getTime());
			//�ٴ����ݱ�rcinfo���л��softTime,�������װ�ľ��鰲װʱ��
			String q3 = "SELECT softTime FROM rcinfo WHERE softPath='"+spName+"'";
			rs = stmt.executeQuery(q3);
			int totalInstallTime = -1;
			if(rs.next()){
				totalInstallTime = rs.getInt(1);
			}
			//���㰲װ���ȣ���һ�³��� (now-startTime)/totalInstallTime
			java.util.Date date = new java.util.Date();
			Long now = date.getTime();	
			System.out.println("*************************now:"+now);
			Long startT = startTime.getTime();
			float progress =  (now-startT)/(float)(totalInstallTime*1000);
			status = status+","+progress;
		}
		//�ͷ���Դ
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
	 * ��agent������Ϣ����ȡ��������Ľ��ȣ������Ѿ����ص�����Ĵ�С����λ��kb
	 * @param opID
	 * @param ip
	 * @param spName
	 * @return
	 */
	public int sendGetDownloadProgressMsg(int opID,String ip, String spName) {
		int alreadyDown = -1;
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9500);
			String values = spName;
			Message msg = new Message(MsgType.getDownloadStatus, opID+"", values);
			// ����
			String datatemp = SerializeUtil.serialize(msg);
			String str = MD5Util.convertMD5(datatemp);
			// ����
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			// ��÷�����Ϣ
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			str = (String) ois.readObject();
			// ����
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
	 * �����û�ID��ѯ���ݿ��ø��û����Բ�����������������Ӧ��������ĵ�ַ�б�
	 * @param userID �û�ID
	 * @return
	 * @throws SQLException
	 */
	public List<String[]> getRCAddr(String userID) throws SQLException {
		List<String[]> resultList = null;
		Statement stmt = null;
		ResultSet rs = null;
		stmt = con.createStatement();
		//��ø��û��ɲ�������������
		String sql = "SELECT hostID FROM userhost where userID=?";
		rs = dbcManager.query(sql, new String[]{userID});
		ArrayList<String> hostList = new ArrayList<String>();
		System.out.println("user contains hostID:::");
		while(rs.next()) {
			System.out.println(rs.getString(1));
			hostList.add(rs.getString(1));
		}
		if(hostList.size() > 0) {
			//���û��ɲ�����������������0
			resultList = new ArrayList<String[]>();
			for(String hostID: hostList) {
				String[] result = new String[2];
				System.out.println("hostID:" + hostID);
				//���ݸ�����ID���������IP
				sql = "SELECT hostIP FROM hostinfo where hostID=? LIMIT 1";
				rs = dbcManager.query(sql, new String[]{hostID});
				if(rs.next()) {
					System.out.println("hostIP:" + rs.getString(1));
					result[0] = rs.getString(1);
				}
				//��ø��������ڵ�������ĵ�ַ
				sql = "SELECT RCAddress FROM hostrc where hostID=?";
				rs = dbcManager.query(sql, new String[]{hostID});
				if(rs.next()) {
					System.out.println("RCAddress:" + rs.getString(1));
					result[1] = rs.getString(1);
				}
				resultList.add(result);
			}
		}
		//�ͷ���Դ
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
	 * �����û���������ID��ѯ���ݿ��ø���������Ӧ��������ĵ�ַ
	 * @param userID �û�ID
	 * @param hostID  ����ID
	 * @return
	 */
	public  String[] getRCAddrByID(String userID,String hostID,String softName) throws SQLException {
		String[] result = null;
		Statement stmt = null;
		ResultSet rs = null;
		String RCAddress = null;
		String OS = null;
		stmt = con.createStatement();
		//�����û�ID��ø��û��ɲ�������������
		String sql = "SELECT hostID FROM userhost where userID=?";
		rs = dbcManager.query(sql, new String[]{userID});
		ArrayList<String> hostList = new ArrayList<String>();
		System.out.println("user contains hostID:::");
		while(rs.next()) {
			System.out.println(rs.getString(1));
			hostList.add(rs.getString(1));
		}
		if(hostList.contains(hostID))  {
			//��Ҫ�������������û��ɲ���������������
			result = new String[3];
			//���Ҫ����������IP
			sql = "SELECT hostIP,OS FROM hostinfo where hostID=? LIMIT 1";
			rs = dbcManager.query(sql, hostID);
			if(rs.next()) {
				System.out.println("hostIP:" + rs.getString(1));
				result[0] = rs.getString(1);
				OS = rs.getString(2);
				System.out.println("OS:" + OS);
			}
			//���Ҫ�����������ڵ�������ĵĵ�ַ 
			sql = "SELECT RCAddress FROM hostrc where hostID=?";
			rs = dbcManager.query(sql, hostID);
			if(rs.next()) {
				System.out.println("RCAddress:" + rs.getString(1));
				RCAddress = rs.getString(1);
				result[1] = RCAddress;
			}
			//���Ҫ��װ�������λ��
			sql = "SELECT softPath FROM rcinfo where RCAddress= ? and softName=? and OS=?";
			rs = dbcManager.query(sql, RCAddress,softName.toLowerCase(),OS);
			if(rs.next()) {
				System.out.println("softPath:" + rs.getString(1));
				result[2] = rs.getString(1);
			}
		}
		//�ͷ���Դ
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
	 * �����û���������IP��ѯ���ݿ��ø���������Ӧ��������ĵ�ַ
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
		//��������ip��ø�������hostID
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
		//����userID��ø��û��ɲ�������������
		sql = "SELECT hostID FROM userhost where userID=?";
		rs = dbcManager.query(sql, userID);
		System.out.println("user can operator hostList:");
		while(rs.next()) {
			System.out.println(rs.getString(1));
			hostIDList.add(rs.getString(1));
			
		}
		if(hostIDList.contains(hostID)) {
			//�����������û��ɲ���������������
			result = new String[2];
			//��ø��������ڵ�������ĵĵ�ַ
			sql = "SELECT RCAddress FROM hostrc where hostID=?";
			rs = dbcManager.query(sql, hostID);
			if(rs.next()) {
				System.out.println("RCAddress:" + rs.getString(1));
				RCAddress = rs.getString(1);
				result[0] = RCAddress;
				
			}
			//���Ҫ��װ�����·��
			sql = "SELECT softPath FROM rcinfo where RCAddress=? and softName=? and OS=?";
			rs = dbcManager.query(sql, RCAddress,softName.toLowerCase(),OS);
			if(rs.next()) {
				System.out.println("softPath:" + rs.getString(1));
				result[1] = rs.getString(1);
			}
			
		}
		//�ͷ���Դ
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
	 * �����û�����ID�õ� ���ڵ�����
	 * @param userID �û�ID
	 * @param hostID  ����ID
	 * @return
	 */
	public  String[] judge() throws SQLException {
		String result[] = new String[2];
		ResultSet rs = null;
		Statement stmt = null;
		stmt = con.createStatement();
		//�����û�ID��ø��û�������
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
	 * �����û�����ID�õ� ���ڵ�����
	 * @param userID �û�ID
	 * @param time  ϵͳ��ǰʱ��
	 * @param num ���ɵ������
	 * @return
	 */
	public int  updateAuthentication(String userID,String time, int num) throws SQLException{
		Statement stmt = null;
		stmt = con.createStatement();
		//��ø��û��ɲ�������������
		String sql = "UPDATE  userinfo  SET  time = '"+time+"' , "+"random = "+num+" WHERE userID='"+userID+"'";
		System.out.println(sql);
		int  flag =  stmt.executeUpdate(sql);
		//�ͷ���Դ
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
	 * ����AgentIp�õ�����������agent������Կ
	 * @return
	 */
	public  String getAgentKey(String hostIP) throws SQLException {
		String result[] = new String[1];
		ResultSet rs = null;
		Statement stmt = null;
		stmt = con.createStatement();
		//�����û�ID��ø��û�������
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
