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
	 * ��������������¼����뵽���ݿ���
	 * @param hostIp
	 * @param opName
	 */
	public int insertEvent(String hostIp,String opName){
		int opID = -1;
		DBOperation dbop = new DBOperation();
		try {
			opID = dbop.insertOperation(hostIp,opName,"");
			dbop.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opID;
	}
	/**
	 * ����������������¼���״̬����������opinfo��
	 * @param opID
	 * @param status
	 * @return
	 */
	public boolean updateOpStatus(int opID,String status){
		boolean flag = false;
		DBOperation dbop = new DBOperation();
		try {
			flag = dbop.updateOpStatus(opID,status);
			dbop.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * �޸�mysql�Ĳ���
	 * 
	 * @param uid
	 * @param ip
	 * @param paramName
	 * @param paramValue
	 * @return
	 */
	public String sendConfigMySqlMsg(String ip,String cfgFilePath, String paramName,
			String paramValue) {
		int opID = insertEvent(ip,"config-MySql");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configMySql, opID+"", values);
			//����
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
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
			if(str1.equals("NoSuchAlgorithmException")||str1.equals("NoSuchPaddingException")||str1.equals("InvalidKeyException")||str1.equals("BadPaddingException")||str1.equals("IllegalBlockSizeException")){
				System.out.println("JAVA security, error key");
			}else{
				msg = (Message) SerializeUtil.deserialize(str1);
				if (msg.getType().equals(MsgType.configMySql)) {
					String ret = (String) msg.getValues();
					if (ret.equals("0x0300200")) {
						updateOpStatus(opID, "0x0300200");
						return "0x0300200";
					}
					
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
		updateOpStatus(opID,"0x0300201");
		return "0x0300201";
	}

	public String sendConfigTomcatMsg(String ip,String cfgFilePath, String paramName,
			String paramValue) {
		int opID = insertEvent(ip,"config-Tomcat");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configTomcat, opID+"", values);
			//����
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
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
			if(str1.equals("NoSuchAlgorithmException")||str1.equals("NoSuchPaddingException")||str1.equals("InvalidKeyException")||str1.equals("BadPaddingException")||str1.equals("IllegalBlockSizeException")){
				System.out.println("JAVA security, error key");
			}else{
				msg = (Message) SerializeUtil.deserialize(str1);
				if (msg.getType().equals(MsgType.configTomcat)) {
					String ret = (String) msg.getValues();
					if (ret.equals("0x0300000")) {
						updateOpStatus(opID, "0x0300000");
						return "0x0300000";
					}
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
		updateOpStatus(opID,"0x0300001");
		return "0x0300001";
	}

	public String sendConfigJdkMsg(String ip,String cfgFilePath, String paramName,
			String paramValue) {
		int opID = insertEvent(ip,"config-Jdk");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configJdk, opID+"", values);
			//����
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
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
			if(str1.equals("NoSuchAlgorithmException")||str1.equals("NoSuchPaddingException")||str1.equals("InvalidKeyException")||str1.equals("BadPaddingException")||str1.equals("IllegalBlockSizeException")){
				System.out.println("JAVA security, error key");
			}else{
				msg = (Message) SerializeUtil.deserialize(str1);
				if (msg.getType().equals(MsgType.configJdk)) {
					String ret = (String) msg.getValues();
					if (ret.equals("0x0300100")) {
						updateOpStatus(opID, "0x0300100");
						return "0x0300100";
					}
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
		updateOpStatus(opID,"0x0300101");
		return "0x0300101";
	}

	public String sendConfigApacheMsg(String ip,String cfgFilePath, String paramName,
			String paramValue) {
		int opID = insertEvent(ip,"config-Apache");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configApache, opID+"", values);
			//����
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
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
			if(str1.equals("NoSuchAlgorithmException")||str1.equals("NoSuchPaddingException")||str1.equals("InvalidKeyException")||str1.equals("BadPaddingException")||str1.equals("IllegalBlockSizeException")){
				System.out.println("JAVA security, error key");
			}else{
				msg = (Message) SerializeUtil.deserialize(str1);
				if (msg.getType().equals(MsgType.configApache)) {
					String ret = (String) msg.getValues();
					if (ret.equals("0x0300300")) {
						updateOpStatus(opID, "0x0300300");
						return "0x0300300";
					}
					
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
		updateOpStatus(opID,"0x0300301");
		return "0x0300301";
	}

	public String sendConfigNginxMsg(String ip,String cfgFilePath, String paramName,
			String paramValue) {
		int opID = insertEvent(ip,"config-Nginx");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configNginx, opID+"", values);
			
			//����
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
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
			if(str1.equals("NoSuchAlgorithmException")||str1.equals("NoSuchPaddingException")||str1.equals("InvalidKeyException")||str1.equals("BadPaddingException")||str1.equals("IllegalBlockSizeException")){
				System.out.println("JAVA security, error key");
			}else{
				msg = (Message) SerializeUtil.deserialize(str1);
				/*
				 * ObjectOutputStream oos = new ObjectOutputStream(
				 * socket.getOutputStream()); oos.writeObject(msg);
				 * ObjectInputStream ois = new ObjectInputStream(
				 * socket.getInputStream()); msg = (Message) ois.readObject();
				 */

				if (msg.getType().equals(MsgType.configNginx)) {
					String ret = (String) msg.getValues();
					if (ret.equals("0x0300400")) {
						updateOpStatus(opID, "0x0300400");
						return "0x0300400";
					}
					
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
		updateOpStatus(opID,"0x0300401");
		return "0x0300401";
	}

	public String sendConfigZendGuardLoaderMsg(String ip,String cfgFilePath,
			String paramName, String paramValue) {
		int opID = insertEvent(ip,"config-ZendGuardLoader");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configZendGuardLoader, opID+"", values);
			//����
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
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
			if(str1.equals("NoSuchAlgorithmException")||str1.equals("NoSuchPaddingException")||str1.equals("InvalidKeyException")||str1.equals("BadPaddingException")||str1.equals("IllegalBlockSizeException")){
				System.out.println("JAVA security, error key");
			}else{
				msg = (Message) SerializeUtil.deserialize(str1);
				if (msg.getType().equals(MsgType.configZendGuardLoader)) {
					String ret = (String) msg.getValues();
					System.out.println(ret);
					if (ret.equals("0x0300500")) {
						updateOpStatus(opID, "0x0300500");
						return "0x0300500";
					}else {
						updateOpStatus(opID, ret);
						return ret;
					}
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
		updateOpStatus(opID,"0x0300501");
		return "0x0300501";
	}

	public String sendConfigPythonMsg(String ip, String cfgFilePath,String paramName,
			String paramValue) {
		int opID = insertEvent(ip,"config-Python");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configPython, opID+"", values);
			//����
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
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
			if(str1.equals("NoSuchAlgorithmException")||str1.equals("NoSuchPaddingException")||str1.equals("InvalidKeyException")||str1.equals("BadPaddingException")||str1.equals("IllegalBlockSizeException")){
				System.out.println("JAVA security, error key");
			}else{
				msg = (Message) SerializeUtil.deserialize(str1);
				if (msg.getType().equals(MsgType.configPython)) {
					String ret = (String) msg.getValues();
					if (ret.equals("0x0300600")) {
						updateOpStatus(opID, "0x0300600");
						return "0x0300600";
					}
					
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
		updateOpStatus(opID,"0x0300601");
		return "0x0300601";
	}

	public String sendConfigMemcachedMsg(String ip,String cfgFilePath,
			String paramName, String paramValue) {
		int opID = insertEvent(ip,"config-Memcached");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configMemcached, opID+"", values);
			//����
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
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
			if(str1.equals("NoSuchAlgorithmException")||str1.equals("NoSuchPaddingException")||str1.equals("InvalidKeyException")||str1.equals("BadPaddingException")||str1.equals("IllegalBlockSizeException")){
				System.out.println("JAVA security, error key");
			}else{
				msg = (Message) SerializeUtil.deserialize(str1);
				if (msg.getType().equals(MsgType.configMemcached)) {
					String ret = (String) msg.getValues();
					if (ret.equals("0x0300700")) {
						updateOpStatus(opID, "0x0300700");
						return "0x0300700";
					}
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
		updateOpStatus(opID,"0x0300701");
		return "0x0300701";
	}

	public String sendConfigIISRewriteMsg(String ip,String cfgFilePath,
			String paramName, String paramValue) {
		int opID = insertEvent(ip,"config-IISRewrite");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configIISRewrite, opID+"", values);
			//����
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
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
			if(str1.equals("NoSuchAlgorithmException")||str1.equals("NoSuchPaddingException")||str1.equals("InvalidKeyException")||str1.equals("BadPaddingException")||str1.equals("IllegalBlockSizeException")){
				System.out.println("JAVA security, error key");
			}else{
				msg = (Message) SerializeUtil.deserialize(str1);
				if (msg.getType().equals(MsgType.configIISRewrite)) {
					String ret = (String) msg.getValues();
					if (ret.equals("0x0300800")) {
						updateOpStatus(opID, "0x0300800");
						return "0x0300800";
					}
					
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
		updateOpStatus(opID,"0x0300801");
		return "0x0300801";
	}

	/*public String sendConfigASPMsg(String ip,String cfgFilePath, String paramName,
			String paramValue) {
		int opID = insertEvent(ip,"config-ASP");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configASP, opID+"", values);
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
			if (msg.getType().equals(MsgType.configASP)) {
				String ret = (String) msg.getValues();
				if (ret.equals("success") || ret.equals("executing")) {
				updateOpStatus(opID,"0x300");
					return true;
				}
				
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
		int opID = insertEvent(ip,"config-FTP");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configFTP, opID+"", values);
			//����
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
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
			if(str1.equals("NoSuchAlgorithmException")||str1.equals("NoSuchPaddingException")||str1.equals("InvalidKeyException")||str1.equals("BadPaddingException")||str1.equals("IllegalBlockSizeException")){
				System.out.println("JAVA security, error key");
			}else{
				msg = (Message) SerializeUtil.deserialize(str1);
				if (msg.getType().equals(MsgType.configFTP)) {
					String ret = (String) msg.getValues();
					if (ret.equals("0x0300900")) {
						updateOpStatus(opID, "0x0300900");
						return "0x0300900";
					}
					
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
		updateOpStatus(opID,"0x0300901");
		return "0x0300901";
	}

	/*public String sendConfigASPNETMsg(String ip,String cfgFilePath, String paramName,
			String paramValue) {
		int opID = insertEvent(ip,"config-ASPNET");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configASPNET, opID+"", values);
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
			if (msg.getType().equals(MsgType.configASPNET)) {
				String ret = (String) msg.getValues();
				if (ret.equals("success") || ret.equals("executing")) {
				updateOpStatus(opID,"0x300");
					return true;
				}
				
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
		int opID = insertEvent(ip,"config-SQLServer2008R2");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configSQLServer2008R2, opID+"", values);
			//����
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
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
			if(str1.equals("NoSuchAlgorithmException")||str1.equals("NoSuchPaddingException")||str1.equals("InvalidKeyException")||str1.equals("BadPaddingException")||str1.equals("IllegalBlockSizeException")){
				System.out.println("JAVA security, error key");
			}else{
				msg = (Message) SerializeUtil.deserialize(str1);
				if (msg.getType().equals(MsgType.configSQLServer2008R2)) {
					String ret = (String) msg.getValues();
					if (ret.equals("0x0300A00")) {
						updateOpStatus(opID, "0x0300A00");
						return "0x0300A00";
					}
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
		updateOpStatus(opID,"0x0300A01");
		return "0x0300A01";
	}

	public String sendConfigSQLServer2000Msg(String ip,String cfgFilePath,
			String paramName, String paramValue) {
		int opID = insertEvent(ip,"config-SQLServer2000");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configSQLServer2000, opID+"", values);
			//����
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
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
			if(str1.equals("NoSuchAlgorithmException")||str1.equals("NoSuchPaddingException")||str1.equals("InvalidKeyException")||str1.equals("BadPaddingException")||str1.equals("IllegalBlockSizeException")){
				System.out.println("JAVA security, error key");
			}else{
				msg = (Message) SerializeUtil.deserialize(str1);
				if (msg.getType().equals(MsgType.configSQLServer2000)) {
					String ret = (String) msg.getValues();
					if (ret.equals("0x0300B00")) {
						updateOpStatus(opID, "0x0300B00");
						return "0x0300B00";
					}
					
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
		updateOpStatus(opID,"0x0300B01");
		return "0x0300B01";
	}

	public String sendConfigOracle10gMsg(String ip,String cfgFilePath,
			String paramName, String paramValue) {
		int opID = insertEvent(ip,"config-Oracle10g");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configOracle10g, opID+"", values);
			//����
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
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
			if(str1.equals("NoSuchAlgorithmException")||str1.equals("NoSuchPaddingException")||str1.equals("InvalidKeyException")||str1.equals("BadPaddingException")||str1.equals("IllegalBlockSizeException")){
				System.out.println("JAVA security, error key");
			}else{
				msg = (Message) SerializeUtil.deserialize(str1);
				if (msg.getType().equals(MsgType.configOracle10g)) {
					String ret = (String) msg.getValues();
					if (ret.equals("0x0300D00")) {
						updateOpStatus(opID, "0x0300D00");
						return "0x0300D00";
					}
					
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
		updateOpStatus(opID,"0x0300D01");
		return "0x0300D01";
	}

	public String sendConfigOracle11gMsg(String ip,String cfgFilePath,
			String paramName, String paramValue) {
		int opID = insertEvent(ip,"config-Oracle11g");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.configOracle11g, opID+"", values);
			//����
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
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
			if(str1.equals("NoSuchAlgorithmException")||str1.equals("NoSuchPaddingException")||str1.equals("InvalidKeyException")||str1.equals("BadPaddingException")||str1.equals("IllegalBlockSizeException")){
				System.out.println("JAVA security, error key");
			}else{
				msg = (Message) SerializeUtil.deserialize(str1);
				if (msg.getType().equals(MsgType.configOracle11g)) {
					String ret = (String) msg.getValues();
					if (ret.equals("0x0300C00")) {
						updateOpStatus(opID, "0x0300C00");
						return "0x0300C00";
					}
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
		updateOpStatus(opID,"0x0300C01");
		return "0x0300C01";
	}

	public String sendConfig360Msg(String ip,String cfgFilePath, String paramName,
			String paramValue) {
		int opID = insertEvent(ip,"config-360");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[3];
			values[0] = cfgFilePath;
			values[1] = paramName;
			values[2] = paramValue;
			Message msg = new Message(MsgType.config360, opID+"", values);
			//����
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
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
			if(str1.equals("NoSuchAlgorithmException")||str1.equals("NoSuchPaddingException")||str1.equals("InvalidKeyException")||str1.equals("BadPaddingException")||str1.equals("IllegalBlockSizeException")){
				System.out.println("JAVA security, error key");
			}else{
				msg = (Message) SerializeUtil.deserialize(str1);
				if (msg.getType().equals(MsgType.config360)) {
					String ret = (String) msg.getValues();
					if (ret.equals("0x0300E00")) {
						updateOpStatus(opID, "0x0300E00");
						return "0x0300E00";
					}
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
		updateOpStatus(opID,"0x0300E01");
		return "0x0300E01";
	}
}
