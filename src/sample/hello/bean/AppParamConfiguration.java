package sample.hello.bean;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import sample.DBOP.DBOperation;

import edu.xidian.enc.AESUtil;
import edu.xidian.enc.MD5Util;
import edu.xidian.enc.SerializeUtil;
import edu.xidian.message.Message;
import edu.xidian.message.MsgType;

public class AppParamConfiguration {
	
	/**
	 * 将配置软件参数事件插入到数据库中
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
	 * 更新配置软件参数事件的状态，操作的是opinfo表
	 * @param opID
	 * @param status
	 * @return
	 */
	public boolean updateOpStatus(int opID,String status){
		boolean flag = false;
		DBOperation dbop = new DBOperation();
		try {
			flag = dbop.updateOpStatus(opID,status);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 修改mysql的参数
	 * 
	 * @param uid
	 * @param ip
	 * @param paramName
	 * @param paramValue
	 * @return
	 */
	public String sendConfigMySqlMsg(String ip,String cfgFilePath, String paramName,
			String paramValue) {
		int opID = insertEvent(ip,"configMySql");
		// 发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configMySql, opID+"", values);
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
			if (msg.getType().equals(MsgType.configMySql)) {
				String ret = (String) msg.getValues();
				if (ret.equals("0x320")) {
					updateOpStatus(opID,"0x320");
					return "0x320";
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
		updateOpStatus(opID,"0x321");
		return "0x321";
	}

	public String sendConfigTomcatMsg(String ip,String cfgFilePath, String paramName,
			String paramValue) {
		int opID = insertEvent(ip,"configTomcat");
		// 发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configTomcat, opID+"", values);
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
			if (msg.getType().equals(MsgType.configTomcat)) {
				String ret = (String) msg.getValues();
				if (ret.equals("0x300")) {
					updateOpStatus(opID,"0x300");
					return "0x300";
				}
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
		updateOpStatus(opID,"0x301");
		return "0x301";
	}

	public String sendConfigJdkMsg(String ip,String cfgFilePath, String paramName,
			String paramValue) {
		int opID = insertEvent(ip,"configJdk");
		// 发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configJdk, opID+"", values);
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
			if (msg.getType().equals(MsgType.configJdk)) {
				String ret = (String) msg.getValues();
				if (ret.equals("0x310")) {
					updateOpStatus(opID,"0x310");
					return "0x310";
				}
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
		updateOpStatus(opID,"0x311");
		return "0x311";
	}

	public String sendConfigApacheMsg(String ip,String cfgFilePath, String paramName,
			String paramValue) {
		int opID = insertEvent(ip,"configApache");
		// 发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configApache, opID+"", values);
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
			if (msg.getType().equals(MsgType.configApache)) {
				String ret = (String) msg.getValues();
				if (ret.equals("0x330")) {
					updateOpStatus(opID,"0x330");
					return "0x330";
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
		updateOpStatus(opID,"0x331");
		return "0x331";
	}

	public String sendConfigNginxMsg(String ip,String cfgFilePath, String paramName,
			String paramValue) {
		int opID = insertEvent(ip,"configNginx");
		// 发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configNginx, opID+"", values);
			
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
			
			if (msg.getType().equals(MsgType.configNginx)) {
				String ret = (String) msg.getValues();
				if (ret.equals("0x340")) {
					updateOpStatus(opID,"0x340");
					return "0x340";
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
		updateOpStatus(opID,"0x341");
		return "0x341";
	}

	public String sendConfigZendGuardLoaderMsg(String ip,String cfgFilePath,
			String paramName, String paramValue) {
		int opID = insertEvent(ip,"configZendGuardLoader");
		// 发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configZendGuardLoader, opID+"", values);
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
			if (msg.getType().equals(MsgType.configZendGuardLoader)) {
				String ret = (String) msg.getValues();
				if (ret.equals("0x350")) {
					updateOpStatus(opID,"0x350");
					return "0x350";
				}
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
		updateOpStatus(opID,"0x351");
		return "0x351";
	}

	public String sendConfigPythonMsg(String ip, String cfgFilePath,String paramName,
			String paramValue) {
		int opID = insertEvent(ip,"configPython");
		// 发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configPython, opID+"", values);
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
			if (msg.getType().equals(MsgType.configPython)) {
				String ret = (String) msg.getValues();
				if (ret.equals("0x360")) {
					updateOpStatus(opID,"0x360");
					return "0x360";
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
		updateOpStatus(opID,"0x361");
		return "0x361";
	}

	public String sendConfigMemcachedMsg(String ip,String cfgFilePath,
			String paramName, String paramValue) {
		int opID = insertEvent(ip,"configMemcached");
		// 发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configMemcached, opID+"", values);
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
			if (msg.getType().equals(MsgType.configMemcached)) {
				String ret = (String) msg.getValues();
				if (ret.equals("0x370")) {
					updateOpStatus(opID,"0x370");
					return "0x370";
				}
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
		updateOpStatus(opID,"0x371");
		return "0x371";
	}

	public String sendConfigIISRewriteMsg(String ip,String cfgFilePath,
			String paramName, String paramValue) {
		int opID = insertEvent(ip,"configIISRewrite");
		// 发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configIISRewrite, opID+"", values);
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
			if (msg.getType().equals(MsgType.configIISRewrite)) {
				String ret = (String) msg.getValues();
				if (ret.equals("0x380")) {
					updateOpStatus(opID,"0x380");
					return "0x380";
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
		updateOpStatus(opID,"0x381");
		return "0x381";
	}

	/*public String sendConfigASPMsg(String ip,String cfgFilePath, String paramName,
			String paramValue) {
		int opID = insertEvent(ip,"configASP");
		// 发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configASP, opID+"", values);
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
			if (msg.getType().equals(MsgType.configASP)) {
				String ret = (String) msg.getValues();
				if (ret.equals("success") || ret.equals("executing")) {
				updateOpStatus(opID,"0x300");
					return true;
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
		return false;
	}*/

	public String sendConfigFTPMsg(String ip,String cfgFilePath, String paramName,
			String paramValue) {
		int opID = insertEvent(ip,"configFTP");
		// 发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configFTP, opID+"", values);
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
			if (msg.getType().equals(MsgType.configFTP)) {
				String ret = (String) msg.getValues();
				if (ret.equals("0x390")) {
					updateOpStatus(opID,"0x390");
					return "0x390";
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
		updateOpStatus(opID,"0x391");
		return "0x391";
	}

	/*public String sendConfigASPNETMsg(String ip,String cfgFilePath, String paramName,
			String paramValue) {
		int opID = insertEvent(ip,"configASPNET");
		// 发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configASPNET, opID+"", values);
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
			if (msg.getType().equals(MsgType.configASPNET)) {
				String ret = (String) msg.getValues();
				if (ret.equals("success") || ret.equals("executing")) {
				updateOpStatus(opID,"0x300");
					return true;
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
		return false;
	}*/

	public String sendConfigSQLServer2008R2Msg(String ip,String cfgFilePath,
			String paramName, String paramValue) {
		int opID = insertEvent(ip,"configSQLServer2008R2");
		// 发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configSQLServer2008R2, opID+"", values);
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
			if (msg.getType().equals(MsgType.configSQLServer2008R2)) {
				String ret = (String) msg.getValues();
				if (ret.equals("0x3A0")) {
					updateOpStatus(opID,"0x3A0");
					return "0x3A0";
				}
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
		updateOpStatus(opID,"0x3A1");
		return "0x3A1";
	}

	public String sendConfigSQLServer2000Msg(String ip,String cfgFilePath,
			String paramName, String paramValue) {
		int opID = insertEvent(ip,"configSQLServer2000");
		// 发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configSQLServer2000, opID+"", values);
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
			if (msg.getType().equals(MsgType.configSQLServer2000)) {
				String ret = (String) msg.getValues();
				if (ret.equals("0x3B0")) {
					updateOpStatus(opID,"0x3B0");
					return "0x3B0";
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
		updateOpStatus(opID,"0x3B1");
		return "0x3B1";
	}

	public String sendConfigOracle10gMsg(String ip,String cfgFilePath,
			String paramName, String paramValue) {
		int opID = insertEvent(ip,"configOracle10g");
		// 发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configOracle10g, opID+"", values);
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
			if (msg.getType().equals(MsgType.configOracle10g)) {
				String ret = (String) msg.getValues();
				if (ret.equals("0x3D0")) {
					updateOpStatus(opID,"0x3D0");
					return "0x3D0";
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
		updateOpStatus(opID,"0x3D1");
		return "0x3D1";
	}

	public String sendConfigOracle11gMsg(String ip,String cfgFilePath,
			String paramName, String paramValue) {
		int opID = insertEvent(ip,"configOracle11g");
		// 发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configOracle11g, opID+"", values);
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
			if (msg.getType().equals(MsgType.configOracle11g)) {
				String ret = (String) msg.getValues();
				if (ret.equals("0x3C0")) {
					updateOpStatus(opID,"0x3C0");
					return "0x3C0";
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
		updateOpStatus(opID,"0x3C1");
		return "0x3C1";
	}

	public String sendConfig360Msg(String ip,String cfgFilePath, String paramName,
			String paramValue) {
		int opID = insertEvent(ip,"config360");
		// 发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.config360, opID+"", values);
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
			if (msg.getType().equals(MsgType.config360)) {
				String ret = (String) msg.getValues();
				if (ret.equals("0x3E0")) {
					updateOpStatus(opID,"0x3E0");
					return "0x3E0";
				}
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
		updateOpStatus(opID,"0x3E1");
		return "0x3E1";
	}
}
