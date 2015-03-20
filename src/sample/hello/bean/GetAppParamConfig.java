package sample.hello.bean;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import sample.DBOP.DBOperation;

import edu.xidian.enc.AESUtil;
import edu.xidian.enc.SerializeUtil;
import edu.xidian.message.Message;
import edu.xidian.message.MsgType;

public class GetAppParamConfig {
	
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
	 * ���mysql�����ò���
	 * 
	 * @param uid
	 * @param ip
	 * @param paramName
	 * @param paramValue
	 * @return
	 */
	public String sendGetConfigMySqlMsg(String ip,String cfgFilePath, String paramName) {
		// ����Socket��Ϣ��Agent
		int opID=insertEvent(ip,"getMySqlConfig");
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[2];
			values[0] = cfgFilePath;
			values[1] = paramName;
			Message msg = new Message(MsgType.getMySqlConfig, opID+"", values);
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
				if (msg.getType().equals(MsgType.getMySqlConfig)) {
					String ret = (String) msg.getValues();
					updateOpStatus(opID, ret);
					return ret;
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
			e.printStackTrace();
		}
		updateOpStatus(opID, "0x0800201");
		return "0x0800201";
	}
	
	
	public String sendGetConfigMySqlLinuxMsg(String ip, String paramName) {
		// ����Socket��Ϣ��Agent
		int opID=insertEvent(ip,"getMySqlConfig");
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[1];
			values[0] = paramName;
			Message msg = new Message(MsgType.getMySqlConfig, opID+"", values);
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
				if (msg.getType().equals(MsgType.getMySqlConfig)) {
					String ret = (String) msg.getValues();
					updateOpStatus(opID, ret);
					return ret;
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
			e.printStackTrace();
		}
		updateOpStatus(opID, "0x0800201");
		return "0x0800201";
	}
	
	public String sendGetConfigTomcatMsg(String ip,String cfgFilePath, String paramName) {
		int opID=insertEvent(ip,"getTomcatConfig");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[2];
			values[0] = cfgFilePath;
			values[1] = paramName;
			Message msg = new Message(MsgType.getTomcatConfig, opID+"", values);
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
				if (msg.getType().equals(MsgType.getTomcatConfig)) {
					String ret = (String) msg.getValues();
					updateOpStatus(opID, ret);
					return ret;
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
		updateOpStatus(opID, "0x0800001");
		return "0x0800001";
	}
	
	
	public String sendGetConfigApacheMsg(String ip,String cfgFilePath, String paramName) {
		int opID=insertEvent(ip,"getApacheConfig");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[2];
			values[0] = cfgFilePath;
			values[1] = paramName;
			
			Message msg = new Message(MsgType.getApacheConfig, opID+"", values);
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
				if (msg.getType().equals(MsgType.getApacheConfig)) {
					String ret = (String) msg.getValues();
					updateOpStatus(opID, ret);
					return ret;
					
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
		updateOpStatus(opID, "0x0800301");
		return "0x0800301";
	}
	
	public String sendGetConfigNginxMsg(String ip,String cfgFilePath, String paramName) {
		int opID=insertEvent(ip,"getNginxConfig");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[2];
			values[0] = cfgFilePath;
			values[1] = paramName;
			Message msg = new Message(MsgType.getNginxConfig, opID+"", values);
			
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
				if (msg.getType().equals(MsgType.getNginxConfig)) {
					String ret = (String) msg.getValues();
					updateOpStatus(opID, ret);
					return ret;	
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
		updateOpStatus(opID, "0x0800401");
		return "0x0800401";
	}
	
	public String sendGetConfigZendGuardLoaderMsg(String ip,
			String paramName) {
		int opID=insertEvent(ip,"getZendGuardLoaderConfig");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[1];
			values[0] = paramName;
			
			Message msg = new Message(MsgType.getZendGuardLoaderConfig,opID+"", values);
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
				if (msg.getType().equals(MsgType.getZendGuardLoaderConfig)) {
					String ret = (String) msg.getValues();
					updateOpStatus(opID, ret);
					return ret;
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
		updateOpStatus(opID, "0x0800501");
		return "0x0800501";
	}
	
	
	public String sendGetConfigZendGuardLoaderLinuxMsg(String ip,String path,
			String paramName) {
		int opID=insertEvent(ip,"getZendGuardLoaderConfig");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[2];
			values[0] = path;
			values[1] = paramName;
			Message msg = new Message(MsgType.getZendGuardLoaderConfig,opID+"", values);
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
				if (msg.getType().equals(MsgType.getZendGuardLoaderConfig)) {
					String ret = (String) msg.getValues();
					updateOpStatus(opID, ret);
					return ret;
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
		updateOpStatus(opID, "0x0800501");
		return "0x0800501";
	}
	
	
	
	public String sendGetConfigPythonMsg(String ip, String cfgFilePath,String paramName) {
		int opID=insertEvent(ip,"getPythonConfig");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[2];
			values[0] = cfgFilePath;
			values[1] = paramName;
			Message msg = new Message(MsgType.getPythonConfig, opID+"", values);
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
				if (msg.getType().equals(MsgType.getPythonConfig)) {
					String ret = (String) msg.getValues();
					updateOpStatus(opID, ret);
					return ret;
					
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
		updateOpStatus(opID, "0x0800601");
		return "0x0800601";
	}
	
	public String sendGetConfigMemcachedMsg(String ip,String cfgFilePath,
			String paramName) {
		int opID=insertEvent(ip,"getMemcachedConfig");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[2];
			values[0] = cfgFilePath;
			values[1] = paramName;
			
			Message msg = new Message(MsgType.getMemcachedConfig,opID+"", values);
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
				if (msg.getType().equals(MsgType.getMemcachedConfig)) {
					String ret = (String) msg.getValues();
					updateOpStatus(opID, ret);
					return ret;
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
		updateOpStatus(opID, "0x0800701");
		return "0x0800701";
	}
	
	public String sendGetConfigIISRewriteMsg(String ip,String cfgFilePath,
			String paramName) {
		int opID=insertEvent(ip,"getIISRewriteConfig");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[2];
			values[0] = cfgFilePath;
			values[1] = paramName;
			Message msg = new Message(MsgType.getIISRewriteConfig, opID+"", values);
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
				if (msg.getType().equals(MsgType.getIISRewriteConfig)) {
					String ret = (String) msg.getValues();
					updateOpStatus(opID, ret);
					return ret;
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
		updateOpStatus(opID, "0x0800801");
		return "0x0800801";
	}
	
	public String sendGetConfigFTPMsg(String ip,String cfgFilePath, String paramName) {
		int opID=insertEvent(ip,"getFTPConfig");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[2];
			values[0] = cfgFilePath;
			values[1] = paramName;
		
			Message msg = new Message(MsgType.getFTPConfig,opID+"", values);
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
				if (msg.getType().equals(MsgType.getFTPConfig)) {
					String ret = (String) msg.getValues();
					updateOpStatus(opID, ret);
					return ret;
					
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
		updateOpStatus(opID, "0x0800901");
		return "0x0800901";
	}
	
	
	public String sendGetConfigSQLServer2008R2Msg(String ip,String cfgFilePath,String paramName) {
		int opID=insertEvent(ip,"getSQLServer2008R2Config");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[2];
			values[0] = cfgFilePath;
			values[1] = paramName;
		
			Message msg = new Message(MsgType.getSQLServer2008R2Config,opID+"", values);
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
				if (msg.getType().equals(MsgType.getSQLServer2008R2Config)) {
					String ret = (String) msg.getValues();
					updateOpStatus(opID, ret);
					return ret;
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
		updateOpStatus(opID, "0x0800A01");
		return "0x0800A01";
	}
	
	public String sendGetConfigSQLServer2000Msg(String ip,String cfgFilePath,String paramName) {
		int opID=insertEvent(ip,"getSQLServer2000Config");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[2];
			values[0] = cfgFilePath;
			values[1] = paramName;
			
			Message msg = new Message(MsgType.getSQLServer2000Config,opID+"", values);
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
				if (msg.getType().equals(MsgType.getSQLServer2000Config)) {
					String ret = (String) msg.getValues();
					updateOpStatus(opID, ret);
					return ret;
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
		updateOpStatus(opID, "0x0800B01");
		return "0x0800B01";
	}
	
	public String sendGetConfigOracle10gMsg(String ip,String cfgFilePath,
			String paramName) {
		int opID=insertEvent(ip,"getOracle10gConfig");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[2];
			values[0] = cfgFilePath;
			values[1] = paramName;
			
			Message msg = new Message(MsgType.getOracle10gConfig,opID+"", values);
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
				if (msg.getType().equals(MsgType.getOracle10gConfig)) {
					String ret = (String) msg.getValues();
					updateOpStatus(opID, ret);
					return ret;
					
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
		updateOpStatus(opID, "0x0800C01");
		return "0x0800C01";
	}
	
	public String sendGetConfigOracle11gMsg(String ip,String cfgFilePath,
			String paramName) {
		int opID=insertEvent(ip,"getOracle11gConfig");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[2];
			values[0] = cfgFilePath;
			values[1] = paramName;
			Message msg = new Message(MsgType.getOracle11gConfig, opID+"", values);
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
				if (msg.getType().equals(MsgType.getOracle11gConfig)) {
					String ret = (String) msg.getValues();
					updateOpStatus(opID, ret);
					return ret;
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
		updateOpStatus(opID, "0x0800D01");
		return "0x0800D01";
	}

	
	public String sendGetConfig360Msg(String ip,String cfgFilePath, String paramName) {
		int opID=insertEvent(ip,"get360Config");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9200);
			String[] values = new String[2];
			values[0] = cfgFilePath;
			values[1] = paramName;
			Message msg = new Message(MsgType.get360Config, opID+"", values);
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
				if (msg.getType().equals(MsgType.get360Config)) {
					String ret = (String) msg.getValues();
					updateOpStatus(opID, ret);
					return ret;
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
		updateOpStatus(opID, "0x0800E01");
		return "0x0800E01";
	}
}
