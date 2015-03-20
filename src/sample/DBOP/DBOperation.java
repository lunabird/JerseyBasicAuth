package sample.DBOP;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
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
import java.util.Calendar;
import java.util.List;

import edu.xidian.enc.AESUtil;
import edu.xidian.enc.MD5Util;
import edu.xidian.enc.SerializeUtil;
import edu.xidian.message.Message;
import edu.xidian.message.MsgType;

public class DBOperation {


	private Connection con = null;
	
	public DBOperation() {
		con = DBConnManager.getConnection();
		if (con == null) {
            System.out.println("Can't get connection");
            return;
        }
	}
	
	//�ͷ�����
	public void close() {
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
	 * 		�����������ã�2��(��ʼִ��start executing-->ִ�н����0x0000000)
	 * 		��װ��5��(��ʼִ��start executing-->��������downloading-->�������download completed-->���ڰ�װinstalling-->��װ�����0x0000000)
	 * 		ж�أ��ű�ִ�У�3��(��ʼִ��start executing-->����ִ��performing-->ִ�н����0x0000000)
	 * 		���£�5�����ݶ��밲װ��ͬ��
	 * 		�������ǵĵ�һ�����ǡ���ʼִ�С�-->��ΪҪ֧�ָ��ֲ�ͬ�Ĳ���ϵͳ���������ݿ�������Ӣ�Ĵ洢
	 * @param hostIP ִ������������IP
	 * @param opName ������
	 * @return opID ����ID
	 * @throws SQLException
	 */
	public int insertOperation(String hostIP,String opName,String version) throws SQLException{
		Statement stmt = null;
		stmt = con.createStatement();
		ResultSet rs = null;
		//����op��
		String sql = "INSERT INTO op (hostIP,opName,version) VALUES ('"+hostIP+"','"+opName+"','"+version+"')";
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
		//���status�ǡ�������ɡ�����Ҫ�Լ�����һ�������ڰ�װ��
		if(status.contains("download completed")){
			Calendar calendar = Calendar.getInstance();    
		    calendar.setTime(date);    
		    calendar.add(Calendar.SECOND, 1);    
		    date = calendar.getTime();
		    time = new Timestamp(date.getTime()); 
			String sqlq = "INSERT INTO opinfo (opID,status,time) VALUES (" + opID+ ",'installing','" + df.format(time) + "')";
			System.out.println(sqlq);
			stmt.execute(sqlq);
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
		return flag;
	}
	
	/**2015-1-20 ����
	 * @author Administrator
	 * ��opinfo������²���״̬��Ϣ����¼������Ϣ
	 * @param opID
	 * @param status
	 * @param detailinfo
	 * @return
	 * @throws SQLException
	 * 
	 */
	public boolean updateOpStatus(int opID, String status, String detailInfo) throws SQLException{
		Statement stmt = null;
		stmt = con.createStatement();
		// �����¼�¼��opinfo��
		// ����ʱ��
		java.util.Date date = new java.util.Date();
		Timestamp time = new Timestamp(date.getTime());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "INSERT INTO opinfo (opID,status,time,detailInfo) VALUES (" + opID+ ",'" + status + "','" + df.format(time) + "','"+ detailInfo+"')";
		boolean flag = stmt.execute(sql);
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
	public String getOpStatus(int opID,String flag) throws SQLException{
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
		
		// ��ð�װ������
		// **************************************************************//
		// ���Ȳ�ѯop�����hostIp��opName���õ��������
		String sql3 = "SELECT hostIp,opName FROM op WHERE opID=" + opID;
		rs = stmt.executeQuery(sql3);
		String hostIp = null;
		String sName = null;// �������
		String spName = null;
		if (rs.next()) {
			hostIp = rs.getString("hostIp");
			sName = rs.getString("opName").split("-")[1];
		}
		// �ٸ���������ֲ�rcinfo����ȡ�������Ӧ�� ��װ����spName
//		if(flag.equals("true")) {
//			String[] temp = getUpdateRCAddrByIP("hp", hostIp, sName);
//			spName = temp[1];
//		}else {
//		String[] temp = getRCAddrByIP("hp", hostIp, sName);
//		spName = temp[1];
//		}
		
		//����opID��ð汾��,��ѯ����op��
		String sql4 = "SELECT version FROM op WHERE opID=" + opID;
		rs = stmt.executeQuery(sql4);
		String version ="";
		if (rs.next()) {
			version = rs.getString("version");			
		}
		//����hostip����������Լ�����汾��ú��ʵİ�װ������
		String[] temp = getRCAddrByIP("hp", hostIp, sName,version);
		spName = temp[1];
		
		// **************************************************************//
			
		
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
		} //���status�ǡ����ڰ�װ������Ҫ�Լ����㰲װ���ȡ���Ϊ���ݿ������rcinfo���д洢�˾��鰲װʱ�䣬��λΪ��(s).
		else if(status.contains("installing")){
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
	/**2015-1-21����
	 * ��ò�������ϸ��Ϣ
	 * @param opID
	 * @return
	 * @throws SQLException
	 */
	public String getOpDetailInfo(int opID) throws SQLException {
		String detailInfo = "";
		Statement stmt = null;
		ResultSet rs = null;
		stmt = con.createStatement();
		//��ѯopinfo��,��ȡ���µ�һ����¼
		String sql = "SELECT status FROM opinfo WHERE opID='"+opID+"' ORDER BY time DESC LIMIT 1";
		System.out.println(sql);
		rs = stmt.executeQuery(sql);
		if(rs.next()){
			detailInfo = rs.getString(1);
		}
		return detailInfo;
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
			//����
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str;
			try {
				str = AESUtil.encrypt(datatemp,ip);
				//����
				ObjectOutputStream oos = new ObjectOutputStream(
						socket.getOutputStream());
				oos.writeObject(str);
				//��÷�����Ϣ
				ObjectInputStream ois = new ObjectInputStream(
						socket.getInputStream());
				byte[] rcvstr = (byte[])ois.readObject();
				//����
				byte[] str2 = AESUtil.decrypt(rcvstr,ip);
				String str1 = new String(str2,"iso-8859-1");
				msg = (Message) SerializeUtil.deserialize(str1);
				if (msg.getType().equals(MsgType.getDownloadStatus)) {
					String ret = (String) msg.getValues();
					System.out.println(ret);
					alreadyDown = Integer.parseInt(ret);
				}
				socket.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return alreadyDown;
	}
	/**
	 * 
	 * @author huangpeng
	 * @date 2015-3-17
	 * @brief ��hostapp�����ѯ����汾��
	 */
	public String queryHostappTableForSoftwareVersion(String hostip,String software) throws SQLException{
		String version = "";
		Statement stmt = null;
		stmt = con.createStatement();
		ResultSet rs = null;
		String sql = "SELECT version FROM hostapp WHERE hostip = '"+hostip+"' and software='"+software+"'";
		rs = stmt.executeQuery(sql);
		while(rs.next()){
			version = rs.getString(1);
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
		return version;
	}
	/**
	 * 
	 * @author huangpeng
	 * @date 2015-3-17
	 * @brief ��hostapp�������һ����¼��hostapp��洢����Ϣ��ÿ̨������ϰ�װ���������汾��
	 * 		    ÿ���������װ�ɹ��󣬻���hostapp��������һ����¼��
	 */
	public void insertHostappTable(String hostip,String software,String version) throws SQLException{
		Statement stmt = null;
		stmt = con.createStatement();
		String sql = "INSERT INTO  hostapp(hostip,software,version) VALUES ('"+hostip+"','"+software+"','"+version+"')";
		stmt.executeUpdate(sql);
		if(stmt!=null)
		{
			try{
				stmt.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * @author huangpeng
	 * @date 2015-3-17
	 * @brief ��hostapp����ɾ��һ����¼��hostapp��洢����Ϣ��ÿ̨������ϰ�װ���������汾��
	 * 		    ÿ�������ж�سɹ��󣬻���hostapp����ɾ��һ����¼��
	 */
	public void deleteHostappTable(String hostip,String software) throws SQLException{
		Statement stmt = null;
		stmt = con.createStatement();
		String sql = "DELETE FROM hostapp WHERE (hostip='"+hostip+"' and software='"+software+"')";
		stmt.executeUpdate(sql);
		if(stmt!=null)
		{
			try{
				stmt.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * @author huangpeng
	 * @date 2015-3-17
	 * @brief ��hostapp�������һ����¼��hostapp��洢����Ϣ��ÿ̨������ϰ�װ���������汾��
	 * 		    ÿ����������³ɹ��󣬻���hostapp�������һ����¼��
	 */
	public void updateHostappTable(String hostip,String software,String version) throws SQLException{
		Statement stmt = null;
		stmt = con.createStatement();
		String sql = "UPDATE  hostapp  SET version ='"+version+"' WHERE (hostip='"+hostip+"' and software='"+software+"')";
		stmt.executeUpdate(sql);
		if(stmt!=null)
		{
			try{
				stmt.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * @author huangpeng
	 * @date 2015-3-17
	 * @brief ����hostip��ѯ��Ӧ�Ĳ���ϵͳ
	 */
	public String getHostOSByIP(String hostip) throws SQLException{
		String os = "";
		Statement stmt = null;
		ResultSet rs = null;
		stmt = con.createStatement();
		String sql = "SELECT os FROM hostinfo WHERE hostIP='"+hostip+"'";
		rs=stmt.executeQuery(sql);
		while(rs.next()){
			os = rs.getString(1);
		}
		return os;
	}
	/**
	 * 
	 * @author huangpeng
	 * @date 2015-3-17
	 * @brief ��ѯ���ݿ���ĳ������µİ汾��,�������µİ汾��
	 */
	public String queryAppNewestVersion(String software,String os) throws SQLException{
		Statement stmt = null;
		ResultSet rs = null;
		stmt = con.createStatement();
		String sql = "SELECT softVersion FROM rcinfo WHERE (softName='"+software+"' and OS='"+os+"')";
		System.out.println("*********************************************************"+sql);
		rs=stmt.executeQuery(sql);
		ArrayList<String> versionList = new ArrayList<String>();
		while(rs.next()) {
			System.out.println(rs.getString(1));
			versionList.add(rs.getString(1));
		}
		if(versionList.size()==0){
			System.out.println("�������Ĳ����ڸ�����ļ�¼��");
		}else if(versionList.size()==1){
			return versionList.get(0);
		}else{
			//�ж���һ���汾������
			AppVersionMng avm = new AppVersionMng();
			String newest = avm.pickNewestVerionNumber(versionList);
			return newest;
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
		return null;
	}
	/**
	 * 
	 * @author huangpeng
	 * @date 2015-3-20
	 * @brief ����op������opID��ѯversion
	 */
	public String queryOpTableForVersion(int opId) throws SQLException{
		String version = "";
		Statement stmt = null;
		stmt = con.createStatement();
		ResultSet rs = null;
		String sql = "SELECT version FROM op WHERE opID = "+opId;
		rs = stmt.executeQuery(sql);
		while(rs.next()){
			version = rs.getString(1);
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
		return version;
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
		String sql = "SELECT hostID FROM userhost where userID='"+userID+"'";
		rs = stmt.executeQuery(sql);
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
				sql = "SELECT hostIP FROM hostinfo where hostID="+hostID+"' LIMIT 1";
				rs = stmt.executeQuery(sql);
				if(rs.next()) {
					System.out.println("hostIP:" + rs.getString(1));
					result[0] = rs.getString(1);
				}
				//��ø��������ڵ�������ĵ�ַ
				sql = "SELECT RCAddress FROM hostrc where hostID='"+hostID+"'";
				rs = stmt.executeQuery(sql);
				if(rs.next()) {
					System.out.println("RCAddress:" + rs.getString(1));
					result[1] = rs.getString(1);
				}
				resultList.add(result);
			}
		}
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
		String sql = "SELECT hostID FROM userhost where userID='"+userID+"'";
		rs = stmt.executeQuery(sql);
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
			sql = "SELECT hostIP,OS FROM hostinfo where hostID='"+hostID+"' LIMIT 1";
			rs =stmt.executeQuery(sql);
			if(rs.next()) {
				System.out.println("hostIP:" + rs.getString(1));
				result[0] = rs.getString(1);
				OS = rs.getString(2);
				System.out.println("OS:" + OS);
			}
			//���Ҫ�����������ڵ�������ĵĵ�ַ 
			sql = "SELECT RCAddress FROM hostrc where hostID='"+hostID+"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				System.out.println("RCAddress:" + rs.getString(1));
				RCAddress = rs.getString(1);
				result[1] = RCAddress;
			}
			//���Ҫ��װ�������λ��
			sql = "SELECT softPath FROM rcinfo where RCAddress= '"+RCAddress+"' and softName='"+softName.toLowerCase()+"' and OS='"+OS+"'";
			rs =stmt.executeQuery(sql);
			if(rs.next()) {
				System.out.println("softPath:" + rs.getString(1));
				result[2] = rs.getString(1);
			}
		}
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
	
	
	/**
	 * 
	 * @author huangpeng
	 * @throws SQLException 
	 * @date 2015-3-17
	 * @brief ��ѯrcinfo������softPath���version
	 */
	public String queryVersionBySoftPath(String softpath) throws SQLException{
		String version = "";
		Statement stmt = null;
		ResultSet rs = null;
		stmt = con.createStatement();
		String sql = "SELECT softVersion FROM rcinfo WHERE softPath='"+softpath+"'";
		rs=stmt.executeQuery(sql);
		while(rs.next()){
			version = rs.getString(1);
		}
		return version;
	}
	/**@author XQ
	 * �����û���������IP��ѯ���ݿ��ø���������Ӧ��������ĵ�ַ
	 * @param userID
	 * @param hostIP
	 * @return
	 * @throws SQLException
	 */
	public String[] getRCAddrByIP(String userID,String hostIP,String softName,String version) throws SQLException {
		String[] result = null;
		String hostID = null;
		String RCAddress = null;
		String OS = null;
		ArrayList<String>  hostIDList = new ArrayList<String>();
		//��������ip��ø�������hostID
		Statement stmt = null;
		ResultSet rs = null;
		stmt = con.createStatement();
		String sql = "SELECT hostID,OS FROM hostinfo where hostIP='"+hostIP+"'";
		rs = stmt.executeQuery(sql);
		if(rs.next()) {
			System.out.println("hostID:" + rs.getString(1));
			hostID = rs.getString(1);
			OS = rs.getString(2);
			System.out.println("OS:" + OS);
		}
		//����userID��ø��û��ɲ�������������
		sql = "SELECT hostID FROM userhost where userID='"+userID+"'";
		rs = stmt.executeQuery(sql);
		System.out.println("user can operator hostList:");
		while(rs.next()) {
			System.out.println(rs.getString(1));
			hostIDList.add(rs.getString(1));
			
		}
		if(hostIDList.contains(hostID)) {
			//�����������û��ɲ���������������
			result = new String[2];
			//��ø��������ڵ�������ĵĵ�ַ
			sql = "SELECT RCAddress FROM hostrc where hostID='"+hostID+"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				System.out.println("RCAddress:" + rs.getString(1));
				RCAddress = rs.getString(1);
				result[0] = RCAddress;
				
			}
			//���Ҫ��װ�����·��
			
			sql = "SELECT softPath FROM rcinfo where RCAddress= '"+RCAddress+"' and softName='"+softName.toLowerCase()+"' and OS='"+OS+"' and softVersion='"+ version+"'" ;
			rs =stmt.executeQuery(sql);
			if(rs.next()) {
				System.out.println("softPath:" + rs.getString(1));
				result[1] = rs.getString(1);
			}
			
		}
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
	
	
	
//	/**@author XQ
//	 * ��ø��µ��������
//	 * @param userID
//	 * @param hostIP
//	 * @return
//	 * @throws SQLException
//	 */
//	public String[] getUpdateRCAddrByIP(String userID,String hostIP,String softName) throws SQLException {
//		String[] result = null;
//		String hostID = null;
//		String RCAddress = null;
//		String OS = null;
//		ArrayList<String>  hostIDList = new ArrayList<String>();
//		//��������ip��ø�������hostID
//		Statement stmt = null;
//		ResultSet rs = null;
//		stmt = con.createStatement();
//		String sql = "SELECT hostID,OS FROM hostinfo where hostIP='"+hostIP+"'";
//		rs =stmt.executeQuery(sql);
//		if(rs.next()) {
//			System.out.println("hostID:" + rs.getString(1));
//			hostID = rs.getString(1);
//			OS = rs.getString(2);
//			System.out.println("OS:" + OS);
//		}
//		//����userID��ø��û��ɲ�������������
//		sql = "SELECT hostID FROM userhost where userID='"+userID+"'";
//		rs = stmt.executeQuery(sql);
//		System.out.println("user can operator hostList:");
//		while(rs.next()) {
//			System.out.println(rs.getString(1));
//			hostIDList.add(rs.getString(1));
//			
//		}
//		if(hostIDList.contains(hostID)) {
//			//�����������û��ɲ���������������
//			result = new String[2];
//			//��ø��������ڵ�������ĵĵ�ַ
//			sql = "SELECT RCAddress FROM hostrc where hostID='"+hostID+"'";;
//			rs =stmt.executeQuery(sql);
//			if(rs.next()) {
//				System.out.println("RCAddress:" + rs.getString(1));
//				RCAddress = rs.getString(1);
//				result[0] = RCAddress;
//				
//			}
//			//���Ҫ��װ�����·��
//			sql = "SELECT softPath FROM rcinfo where RCAddress= '"+RCAddress+"' and softName='"+softName.toLowerCase()+"' and OS='"+OS+"' ORDER BY softVersion  DESC LIMIT 1";
//			rs =stmt.executeQuery(sql);
//			while(rs.next()) {
//				System.out.println("softPath:" + rs.getString(1));
//				result[1] = rs.getString(1);
//			}
//			
//		}
//		
//		if(rs!=null)
//		{
//			try{
//				rs.close();
//			}catch(Exception e)
//			{
//				e.printStackTrace();
//			}
//		}
//		if(stmt!=null)
//		{
//			try{
//				stmt.close();
//			}catch(Exception e)
//			{
//				e.printStackTrace();
//			}
//		}
//		return result;
//	}
//	
	
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
	/**@author Administrator
	 * ��ñ�����Ϣ
	 * @param codeID
	 * @return
	 * @throws SQLException
	 */
	public  String getCode(String codeID) throws SQLException {
		String result = new String("");
		ResultSet rs = null;
		Statement stmt = null;
		stmt = con.createStatement();
		//�����û�ID��ø��û�������
		String sql = "SELECT codeInfo FROM code where codeID='"+codeID+"'";
		rs = stmt.executeQuery(sql);
		while(rs.next()) {			
			result = rs.getString(1);
		}
		
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
//			dbop.getUpdateRCAddrByIP("hp", "119.90.140.59","tomcat");
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
////		
//		try {
//			dbop.updateHostIP("192.168.0.555", "192.168.0.122");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		try {
////			dbop.insertOperation("192.168.0.555", "setupOracle");
//			dbop.updateOpStatus(1, "downloading");
//			//dbop.getOpStatus(3);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		try {
//			dbop.insertHostappTable("192.168.0.2", "Tomcat", "1.0.0");
//			dbop.deleteHostappTable("192.168.0.2", "Tomcat");
			dbop.updateHostappTable("192.168.0.2", "Tomcat", "1.0.1");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
