package sample.hello.bean;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.ws.rs.QueryParam;

import sample.DBOP.DBOperation;

import edu.xidian.enc.AESUtil;
import edu.xidian.enc.MD5Util;
import edu.xidian.enc.SerializeUtil;
import edu.xidian.message.Message;
import edu.xidian.message.MsgType;

public class ApplicationBase {
	/**
	 * 将安装软件事件插入到数据库中
	 * @param hostIp
	 * @param opName
	 */
	public int insertEvent(String hostIp,String opName){
		int opID = -1;
		DBOperation dbop = new DBOperation();
		try {
			opID = dbop.insertOperation(hostIp,opName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opID;
	}
	/**
	 * 安装tomcat
	 * @param uid
	 * @param ip
	 * @param scIPAddr
	 * @param installPath
	 * @return
	 */
	public int sendSetupTomcatMsg(String ip,String[] scIPAddr,String installPath,String jdkPath){
		int opID = insertEvent(ip,"setupTomcat");
		//发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9100);
			String[] values = new String[4];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = installPath;
			values[3] = jdkPath;
			Message msg = new Message(MsgType.setupTomcat, opID+"",values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
			//传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			byte[] rcvstr = (byte[])ois.readObject();
			//解密
			byte[] str2 = AESUtil.decrypt(rcvstr,ip);
			String str1 = new String(str2,"iso-8859-1");
			msg = (Message)SerializeUtil.deserialize(str1); 
			if (msg.getType().equals(MsgType.setupTomcat)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")||ret.equals("executing")){
					return opID;
				}
				System.out.println(ret);
			}
			socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opID;
	}
	/**
	 * 安装tomcat on Linux
	 * @param uid
	 * @param ip
	 * @param scIPAddr
	 * @param installPath
	 * @return
	 */
	public int sendSetupTomcatOnLinuxMsg(String ip,String[] scIPAddr,String installPath,String jdkName,String jdkPath){
		int opID = insertEvent(ip,"setupTomcat");
		//发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9100);
			String[] values = new String[5];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = installPath;
			values[3] = jdkName;
			values[4] = jdkPath;
			Message msg = new Message(MsgType.setupTomcat, opID+"",values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
			//传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			byte[] rcvstr = (byte[])ois.readObject();
			//解密
			byte[] str2 = AESUtil.decrypt(rcvstr,ip);
			String str1 = new String(str2,"iso-8859-1");
			msg = (Message)SerializeUtil.deserialize(str1); 
			if (msg.getType().equals(MsgType.setupTomcat)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")||ret.equals("executing")){
					return opID;
				}
				System.out.println(ret);
			}
			socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opID;
	}
	/**
	 * 安装Mysql
	 * @param uid
	 * @param ip
	 * @param scIPAddr
	 * @param installPath
	 * @return
	 */
	public int sendSetupMySqlMsg(String ip,String[] scIPAddr,String installPath,String pswd){
		int opID = insertEvent(ip,"setupMySql");
		//发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9100);
			String[] values = new String[4];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = installPath;
			values[3] = pswd;
			Message msg = new Message(MsgType.setupMySql, opID+"",values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
			//传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			byte[] rcvstr = (byte[])ois.readObject();
			//解密
			byte[] str2 = AESUtil.decrypt(rcvstr,ip);
			String str1 = new String(str2,"iso-8859-1");
			msg = (Message)SerializeUtil.deserialize(str1); 
			if (msg.getType().equals(MsgType.setupMySql)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")||ret.equals("executing")){
					return opID;
				}
				System.out.println(ret);
			}
			socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opID;
	}
	/**
	 * 安装 Mysql on Linux
	 * @param uid
	 * @param ip
	 * @param scIPAddr
	 * @param installPath
	 * @return
	 */
	public int sendSetupMySqlOnLinuxMsg(String ip,String[] scIPAddr,String pswd){
		int opID = insertEvent(ip,"setupMySql");
		//发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9100);
			String[] values = new String[3];
			values[0] = scIPAddr[0];
//			String temps = scIPAddr[1];
			values[1] = scIPAddr[1];
			values[2] = pswd;
//			values[3] = pswd;
			Message msg = new Message(MsgType.setupMySql, opID+"",values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
			//传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			byte[] rcvstr = (byte[])ois.readObject();
			//解密
			byte[] str2 = AESUtil.decrypt(rcvstr,ip);
			String str1 = new String(str2,"iso-8859-1");
			msg = (Message)SerializeUtil.deserialize(str1); 
			if (msg.getType().equals(MsgType.setupMySql)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")||ret.equals("executing")){
					return opID;
				}
				System.out.println(ret);
			}
			socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opID;
	}
	/**
	 * 安装Jdk
	 * @param uid
	 * @param ip
	 * @param scIPAddr
	 * @param installPath
	 * @return
	 */
	public int sendSetupJdkMsg(String ip,String[] scIPAddr,String installPath){
		int opID = insertEvent(ip,"setupJdk");
		//发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9100);
			String[] values = new String[3];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = installPath;
			Message msg = new Message(MsgType.setupJdk, opID+"",values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
			//传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			byte[] rcvstr = (byte[])ois.readObject();
			//解密
			byte[] str2 = AESUtil.decrypt(rcvstr,ip);
			String str1 = new String(str2,"iso-8859-1");
			msg = (Message)SerializeUtil.deserialize(str1); 
			if (msg.getType().equals(MsgType.setupJdk)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")||ret.equals("executing")){
					return opID;
				}
				System.out.println(ret);
			}
			socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opID;
	}
	/**
	 * 安装Apache
	 * @param uid
	 * @param ip
	 * @param scIPAddr
	 * @param installPath
	 * @return
	 */
	public int sendSetupApacheMsg(String ip,String[] scIPAddr,String installPath){
		int opID = insertEvent(ip,"setupApache");
		//发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9100);
			String[] values = new String[3];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = installPath;
			Message msg = new Message(MsgType.setupApache, opID+"",values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
			//传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			byte[] rcvstr = (byte[])ois.readObject();
			//解密
			byte[] str2 = AESUtil.decrypt(rcvstr,ip);
			String str1 = new String(str2,"iso-8859-1");
			msg = (Message)SerializeUtil.deserialize(str1); 
			if (msg.getType().equals(MsgType.setupApache)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")||ret.equals("executing")){
					return opID;
				}
				System.out.println(ret);
			}
			socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opID;
	}
	/**
	 * 安装Nginx
	 * @param uid
	 * @param ip
	 * @param scIPAddr
	 * @param installPath
	 * @return
	 */
	public int sendSetupNginxMsg(String ip,String[] scIPAddr,String installPath){
		int opID = insertEvent(ip,"setupNginx");
		//发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9100);
			String[] values = new String[3];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = installPath;
			Message msg = new Message(MsgType.setupNginx, opID+"",values);
			System.out.println("values:"+values[0]+","+values[1]+","+values[2]);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
			//传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			byte[] rcvstr = (byte[])ois.readObject();
			//解密
			byte[] str2 = AESUtil.decrypt(rcvstr,ip);
			String str1 = new String(str2,"iso-8859-1");
			msg = (Message)SerializeUtil.deserialize(str1); 
			/*ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(msg);
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			msg = (Message) ois.readObject();*/

			if (msg.getType().equals(MsgType.setupNginx)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")||ret.equals("executing")){
					return opID;
				}
				System.out.println(ret);
			}
			socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opID;
	}
	/**
	 * 安装ZendGuardLoader
	 * @param uid
	 * @param ip
	 * @param scIPAddr
	 * @param installPath
	 * @return
	 */
	public int sendSetupZendGuardLoaderMsg(String ip,String[] scIPAddr,String phpPath){
		int opID = insertEvent(ip,"setupZendGuardLoader");
		//发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9100);
			String[] values = new String[4];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[3] = phpPath;
			Message msg = new Message(MsgType.setupZendGuardLoader, opID+"",values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
			//传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			byte[] rcvstr = (byte[])ois.readObject();
			//解密
			byte[] str2 = AESUtil.decrypt(rcvstr,ip);
			String str1 = new String(str2,"iso-8859-1");
			msg = (Message)SerializeUtil.deserialize(str1); 
			if (msg.getType().equals(MsgType.setupZendGuardLoader)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")||ret.equals("executing")){
					return opID;
				}
				System.out.println(ret);
			}
			socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opID;
	}
	/**
	 * 安装ZendGuardLoader Linux
	 * @param uid
	 * @param ip
	 * @param scIPAddr
	 * @param installPath
	 * @return
	 */
	public int sendSetupZendGuardLoaderMsgOnLinux(String ip,String[] scIPAddr,String installPath,String phpPath){
		int opID = insertEvent(ip,"setupZendGuardLoader");
		//发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9100);
			String[] values = new String[4];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = installPath;
			values[3] = phpPath;
			Message msg = new Message(MsgType.setupZendGuardLoader, opID+"",values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
			//传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			byte[] rcvstr = (byte[])ois.readObject();
			//解密
			byte[] str2 = AESUtil.decrypt(rcvstr,ip);
			String str1 = new String(str2,"iso-8859-1");
			msg = (Message)SerializeUtil.deserialize(str1); 
			if (msg.getType().equals(MsgType.setupZendGuardLoader)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")||ret.equals("executing")){
					return opID;
				}
				System.out.println(ret);
			}
			socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opID;
	}
	/**
	 * 安装Python
	 * @param uid
	 * @param ip
	 * @param scIPAddr
	 * @param installPath
	 * @return
	 */
	public int sendSetupPythonMsg(String ip,String[] scIPAddr){
		int opID = insertEvent(ip,"setupPython");
		//发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9100);
			String[] values = new String[2];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			Message msg = new Message(MsgType.setupPython, opID+"",values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
			//传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			byte[] rcvstr = (byte[])ois.readObject();
			//解密
			byte[] str2 = AESUtil.decrypt(rcvstr,ip);
			String str1 = new String(str2,"iso-8859-1");
			msg = (Message)SerializeUtil.deserialize(str1); 
			if (msg.getType().equals(MsgType.setupPython)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")||ret.equals("executing")){
					return opID;
				}
				System.out.println(ret);
			}
			socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opID;
	}
	/**
	 * 安装Python Linux
	 * @param uid
	 * @param ip
	 * @param scIPAddr
	 * @param installPath
	 * @return
	 */
	public int sendSetupPythonMsgOnLinux(String ip,String[] scIPAddr,String installPath){
		int opID = insertEvent(ip,"setupPython");
		//发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9100);
			String[] values = new String[3];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = installPath;
			Message msg = new Message(MsgType.setupPython, opID+"",values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
			//传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			byte[] rcvstr = (byte[])ois.readObject();
			//解密
			byte[] str2 = AESUtil.decrypt(rcvstr,ip);
			String str1 = new String(str2,"iso-8859-1");
			msg = (Message)SerializeUtil.deserialize(str1); 
			if (msg.getType().equals(MsgType.setupPython)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")||ret.equals("executing")){
					return opID;
				}
				System.out.println(ret);
			}
			socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opID;
	}
	/**
	 * 安装Memcached
	 * @param uid
	 * @param ip
	 * @param scIPAddr
	 * @param installPath
	 * @return
	 */
	public int sendSetupMemcachedMsg(String ip,String[] scIPAddr){
		int opID = insertEvent(ip,"setupMemcached");
		//发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9100);
			String[] values = new String[2];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			
			Message msg = new Message(MsgType.setupMemcached, opID+"",values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
			//传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			byte[] rcvstr = (byte[])ois.readObject();
			//解密
			byte[] str2 = AESUtil.decrypt(rcvstr,ip);
			String str1 = new String(str2,"iso-8859-1");
			msg = (Message)SerializeUtil.deserialize(str1); 
			if (msg.getType().equals(MsgType.setupMemcached)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")||ret.equals("executing")){
					return opID;
				}
				System.out.println(ret);
			}
			socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opID;
	}
	/**
	 * 安装Memcached Linux
	 * @param uid
	 * @param ip
	 * @param scIPAddr
	 * @param installPath
	 * @return
	 */
	public int sendSetupMemcachedMsgOnLinux(String ip,String[] scIPAddr,String installPath){
		int opID = insertEvent(ip,"setupMemcached");
		//发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9100);
			String[] values = new String[3];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = installPath;
			Message msg = new Message(MsgType.setupMemcached, opID+"",values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
			//传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			byte[] rcvstr = (byte[])ois.readObject();
			//解密
			byte[] str2 = AESUtil.decrypt(rcvstr,ip);
			String str1 = new String(str2,"iso-8859-1");
			msg = (Message)SerializeUtil.deserialize(str1); 
			if (msg.getType().equals(MsgType.setupMemcached)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")||ret.equals("executing")){
					return opID;
				}
				System.out.println(ret);
			}
			socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opID;
	}
	/**
	 * 安装IISRewrite
	 * @param uid
	 * @param ip
	 * @param scIPAddr
	 * @param installPath
	 * @return
	 */
	public int sendSetupIISRewriteMsg(String ip,String[] scIPAddr,String installPath){
		int opID = insertEvent(ip,"setupIISRewrite");
		//发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9100);
			String[] values = new String[3];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = installPath;
			Message msg = new Message(MsgType.setupIISRewrite, opID+"",values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
			//传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			byte[] rcvstr = (byte[])ois.readObject();
			//解密
			byte[] str2 = AESUtil.decrypt(rcvstr,ip);
			String str1 = new String(str2,"iso-8859-1");
			msg = (Message)SerializeUtil.deserialize(str1); 
			if (msg.getType().equals(MsgType.setupIISRewrite)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")||ret.equals("executing")){
					return opID;
				}
				System.out.println(ret);
			}
			socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opID;
	}
	/**
	 * 安装ASP
	 * @param uid
	 * @param ip
	 * @param scIPAddr
	 * @param installPath
	 * @return
	 */
	/*public int sendSetupASPMsg(String ip,String[] scIPAddr,String installPath){
		int opID = insertEvent(ip,"setupASP");
		//发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9100);
			String[] values = new String[3];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = installPath;
			Message msg = new Message(MsgType.setupASP, opID+"",values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
			//传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			byte[] rcvstr = (byte[])ois.readObject();
			//解密
			byte[] str2 = AESUtil.decrypt(rcvstr,ip);
			String str1 = new String(str2,"iso-8859-1");
			msg = (Message)SerializeUtil.deserialize(str1); 
			if (msg.getType().equals(MsgType.setupASP)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")||ret.equals("executing")){
					return opID;
				}
				System.out.println(ret);
			}
			socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return opID;
	}*/
	/**
	 * 安装FTP
	 * @param uid
	 * @param ip
	 * @param scIPAddr
	 * @param installPath
	 * @return
	 */
	public int sendSetupFTPMsg(String ip,String[] scIPAddr,String installPath){
		int opID = insertEvent(ip,"setupFTP");
		//发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9100);
			String[] values = new String[3];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = installPath;
			Message msg = new Message(MsgType.setupFTP, opID+"",values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
			//传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			byte[] rcvstr = (byte[])ois.readObject();
			//解密
			byte[] str2 = AESUtil.decrypt(rcvstr,ip);
			String str1 = new String(str2,"iso-8859-1");
			msg = (Message)SerializeUtil.deserialize(str1); 
			if (msg.getType().equals(MsgType.setupFTP)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")||ret.equals("executing")){
					return opID;
				}
				System.out.println(ret);
			}
			socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opID;
	}
	/**
	 * 安装FTP Linux
	 * @param uid
	 * @param ip
	 * @param scIPAddr
	 * @param installPath
	 * @return
	 */
	public int sendSetupFTPMsgOnLinux(String ip,String[] scIPAddr){
		int opID = insertEvent(ip,"setupFTP");
		//发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9100);
//			String[] values = new String[3];
//			values[0] = scIPAddr[0];
//			values[1] = scIPAddr[1];
			Message msg = new Message(MsgType.setupFTP, opID+"",null);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
			//传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			byte[] rcvstr = (byte[])ois.readObject();
			//解密
			byte[] str2 = AESUtil.decrypt(rcvstr,ip);
			String str1 = new String(str2,"iso-8859-1");
			msg = (Message)SerializeUtil.deserialize(str1); 
			if (msg.getType().equals(MsgType.setupFTP)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")||ret.equals("executing")){
					return opID;
				}
				System.out.println(ret);
			}
			socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opID;
	}
	/**
	 * 安装ASPNET
	 * @param uid
	 * @param ip
	 * @param scIPAddr
	 * @param installPath
	 * @return
	 */
	/*public int sendSetupASPNETMsg(String ip,String[] scIPAddr,String installPath){
		int opID = insertEvent(ip,"setupASPNET");
		//发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9100);
			String[] values = new String[3];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = installPath;
			Message msg = new Message(MsgType.setupASPNET, opID+"",values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
			//传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			byte[] rcvstr = (byte[])ois.readObject();
			//解密
			byte[] str2 = AESUtil.decrypt(rcvstr,ip);
			String str1 = new String(str2,"iso-8859-1");
			msg = (Message)SerializeUtil.deserialize(str1); 
			if (msg.getType().equals(MsgType.setupASPNET)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")||ret.equals("executing")){
					return opID;
				}
				System.out.println(ret);
			}
			socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return opID;
	}*/
	/**
	 * 安装SQLServer2008R2
	 * @param uid
	 * @param ip
	 * @param scIPAddr
	 * @param installPath
	 * @param rootPswd
	 * @param hostName
	 * @param userName
	 * @return
	 */
	public int sendSetupSQLServer2008R2Msg(String ip,String[] scIPAddr,String installPath,String rootPswd,String hostName,String userName){
		int opID = insertEvent(ip,"setupSQLServer2008R2");
		//发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9100);
			String[] values = new String[6];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = installPath;
			values[3] = rootPswd;
			values[4] = hostName;
			values[5] = userName;
			Message msg = new Message(MsgType.setupSQLServer2008R2, opID+"",values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
			//传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			byte[] rcvstr = (byte[])ois.readObject();
			//解密
			byte[] str2 = AESUtil.decrypt(rcvstr,ip);
			String str1 = new String(str2,"iso-8859-1");
			msg = (Message)SerializeUtil.deserialize(str1); 
			if (msg.getType().equals(MsgType.setupSQLServer2008R2)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")||ret.equals("executing")){
					return opID;
				}
				System.out.println(ret);
			}
			socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opID;
	}
	/**
	 * 安装SQLServer2000
	 * @param uid
	 * @param ip
	 * @param scIPAddr
	 * @param installPath
	 * @return
	 */
	public int sendSetupSQLServer2000Msg(String ip,String[] scIPAddr,String installPath){
		int opID = insertEvent(ip,"setupSQLServer2000");
		//发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9100);
			String[] values = new String[3];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = installPath;
			Message msg = new Message(MsgType.setupSQLServer2000, opID+"",values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
			//传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			byte[] rcvstr = (byte[])ois.readObject();
			//解密
			byte[] str2 = AESUtil.decrypt(rcvstr,ip);
			String str1 = new String(str2,"iso-8859-1");
			msg = (Message)SerializeUtil.deserialize(str1); 
			if (msg.getType().equals(MsgType.setupSQLServer2000)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")||ret.equals("executing")){
					return opID;
				}
				System.out.println(ret);
			}
			socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opID;
	}
	/**
	 * 安装Oracle10g
	 * @param uid
	 * @param ip
	 * @param scIPAddr
	 * @param installPath
	 * @return
	 */
	public int sendSetupOracle10gMsg(String ip,String[] scIPAddr,String installPath){
		int opID = insertEvent(ip,"setupOracle10g");
		//发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9100);
			String[] values = new String[3];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = installPath;
			Message msg = new Message(MsgType.setupOracle10g, opID+"",values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
			//传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			byte[] rcvstr = (byte[])ois.readObject();
			//解密
			byte[] str2 = AESUtil.decrypt(rcvstr,ip);
			String str1 = new String(str2,"iso-8859-1");
			msg = (Message)SerializeUtil.deserialize(str1); 
			if (msg.getType().equals(MsgType.setupOracle10g)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")||ret.equals("executing")){
					return opID;
				}
				System.out.println(ret);
			}
			socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opID;
	}
	/**
	 * 安装Oracle11g
	 * @param uid
	 * @param ip
	 * @param scIPAddr
	 * @param hostname
	 * @param inventorypath
	 * @param installPath
	 * @param oraclehome
	 * @param rootPswd
	 * @return
	 */
	public int sendSetupOracle11gMsg(String ip,String[] scIPAddr,String hostname,String inventorypath,
			String oraclebase,String oraclehome, String rootPswd){
		int opID = insertEvent(ip,"setupOracle11g");
		//发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9100);
			String[] values = new String[7];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = hostname;
			values[3] = inventorypath;
			values[4] = oraclebase;
			values[5] = oraclehome;
			values[6] = rootPswd;
			Message msg = new Message(MsgType.setupOracle11g, opID+"",values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
			//传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			byte[] rcvstr = (byte[])ois.readObject();
			//解密
			byte[] str2 = AESUtil.decrypt(rcvstr,ip);
			String str1 = new String(str2,"iso-8859-1");
			msg = (Message)SerializeUtil.deserialize(str1); 
			if (msg.getType().equals(MsgType.setupOracle11g)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")||ret.equals("executing")){
					return opID;
				}
				System.out.println(ret);
			}
			socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opID;
	}
	/**
	 * 安装360
	 * @param uid
	 * @param ip
	 * @param scIPAddr
	 * @param installPath
	 * @return
	 */
	public int sendSetup360Msg(String ip,String[] scIPAddr,String installPath){
		int opID = insertEvent(ip,"setup360");
		//发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9100);
			String[] values = new String[3];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = installPath;
			Message msg = new Message(MsgType.setup360, opID+"",values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
			//传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			byte[] rcvstr = (byte[])ois.readObject();
			//解密
			byte[] str2 = AESUtil.decrypt(rcvstr,ip);
			String str1 = new String(str2,"iso-8859-1");
			msg = (Message)SerializeUtil.deserialize(str1); 
			if (msg.getType().equals(MsgType.setup360)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")||ret.equals("executing")){
					return opID;
				}
				System.out.println(ret);
			}
			socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opID;
	}
	
}
