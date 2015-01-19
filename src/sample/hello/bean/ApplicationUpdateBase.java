package sample.hello.bean;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import edu.xidian.enc.AESUtil;
import edu.xidian.enc.SerializeUtil;
import edu.xidian.message.Message;
import edu.xidian.message.MsgType;

import sample.DBOP.DBOperation;

public class ApplicationUpdateBase {
	/**
	 * �����£�����������¼����뵽���ݿ���
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
	public int sendUpdateMySqlMsg( String ip, String[] scIPAddr,
			String updatePath, String rootPswd) {
		// TODO Auto-generated method stub
		int opID = insertEvent(ip,"update-MySql");
		//����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9300);
			String[] values = new String[4];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = updatePath;
			values[3] = rootPswd;
			Message msg = new Message(MsgType.updateMySql, opID+"",values);
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
				if (msg.getType().equals(MsgType.updateMySql)) {
					String ret = (String) msg.getValues();
					if (ret.equals("success") || ret.equals("executing")) {
						return opID;
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
		return opID;
	}

	public int sendUpdateMySqlOnLinuxMsg( String ip,
			String[] scIPAddr, String rootPswd) {
		// TODO Auto-generated method stub
		int opID = insertEvent(ip,"update-MySql");
		//����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9300);
			String[] values = new String[3];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = rootPswd;
			Message msg = new Message(MsgType.updateMySql, opID+"",values);
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
			msg = (Message)SerializeUtil.deserialize(str1); 
			if(str1.equals("NoSuchAlgorithmException")||str1.equals("NoSuchPaddingException")||str1.equals("InvalidKeyException")||str1.equals("BadPaddingException")||str1.equals("IllegalBlockSizeException")){
				System.out.println("JAVA security, error key");
			}else{
				if (msg.getType().equals(MsgType.updateMySql)) {
					String ret = (String) msg.getValues();
					if (ret.equals("success") || ret.equals("executing")) {
						return opID;
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
		return opID;
	}

	public int sendUpdateTomcatMsg( String ip, String[] scIPAddr,
			String updatePath, String jdkPath) {
		// TODO Auto-generated method stub
		int opID = insertEvent(ip,"update-Tomcat");
		//����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9300);
			String[] values = new String[4];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = updatePath;
			values[3] = jdkPath;
			Message msg = new Message(MsgType.updateTomcat, opID+"",values);
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
				if (msg.getType().equals(MsgType.updateTomcat)) {
					String ret = (String) msg.getValues();
					if (ret.equals("success") || ret.equals("executing")) {
						return opID;
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
		return opID;
	}

	public int sendUpdateJdkMsg( String ip, String[] scIPAddr,
			String updatePath) {
		// TODO Auto-generated method stub
		int opID = insertEvent(ip,"update-Jdk");
		//����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9300);
			String[] values = new String[3];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = updatePath;
			Message msg = new Message(MsgType.updateJdk, opID+"",values);
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
				if (msg.getType().equals(MsgType.updateJdk)) {
					String ret = (String) msg.getValues();
					if (ret.equals("success") || ret.equals("executing")) {
						return opID;
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
		return opID;
	}

	public int sendUpdateApacheMsg( String ip, String[] scIPAddr,
			String updatePath) {
		// TODO Auto-generated method stub
		int opID = insertEvent(ip,"update-Apache");
		//����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9300);
			String[] values = new String[3];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = updatePath;
			Message msg = new Message(MsgType.updateApache, opID+"",values);
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
				if (msg.getType().equals(MsgType.updateApache)) {
					String ret = (String) msg.getValues();
					if (ret.equals("success") || ret.equals("executing")) {
						return opID;
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
		return opID;
	}

	public int sendUpdateNginxMsg( String ip, String[] scIPAddr,
			String updatePath) {
		// TODO Auto-generated method stub
		int opID = insertEvent(ip,"update-Nginx");
		//����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9300);
			String[] values = new String[3];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = updatePath;
			Message msg = new Message(MsgType.updateNginx, opID+"",values);
			System.out.println("values:"+values[0]+","+values[1]+","+values[2]);
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

				if (msg.getType().equals(MsgType.updateNginx)) {
					String ret = (String) msg.getValues();
					if (ret.equals("success") || ret.equals("executing")) {
						return opID;
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
		return opID;
	}

	public int sendUpdateZendGuardLoaderMsg( String ip,
			String[] scIPAddr, String phpPath) {
		// TODO Auto-generated method stub
		int opID = insertEvent(ip,"update-ZendGuardLoader");
		//����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9300);
			String[] values = new String[4];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[3] = phpPath;
			Message msg = new Message(MsgType.updateZendGuardLoader, opID+"",values);
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
				if (msg.getType().equals(MsgType.updateZendGuardLoader)) {
					String ret = (String) msg.getValues();
					if (ret.equals("success") || ret.equals("executing")) {
						return opID;
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
		return opID;
	}

	public int sendUpdateZendGuardLoaderMsgOnLinux( String ip,
			String[] scIPAddr, String updatePath, String phpPath) {
		// TODO Auto-generated method stub
		int opID = insertEvent(ip,"update-ZendGuardLoader");
		//����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9300);
			String[] values = new String[4];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = updatePath;
			values[3] = phpPath;
			Message msg = new Message(MsgType.updateZendGuardLoader, opID+"",values);
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
				if (msg.getType().equals(MsgType.updateZendGuardLoader)) {
					String ret = (String) msg.getValues();
					if (ret.equals("success") || ret.equals("executing")) {
						return opID;
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
		return opID;
	}

	public int sendUpdatePythonMsg( String ip, String[] scIPAddr) {
		// TODO Auto-generated method stub
		int opID = insertEvent(ip,"update-Python");
		//����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9300);
			String[] values = new String[2];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			Message msg = new Message(MsgType.updatePython, opID+"",values);
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
				if (msg.getType().equals(MsgType.updatePython)) {
					String ret = (String) msg.getValues();
					if (ret.equals("success") || ret.equals("executing")) {
						return opID;
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
		return opID;
	}

	public int sendUpdatePythonMsgOnLinux( String ip,
			String[] scIPAddr, String updatePath) {
		// TODO Auto-generated method stub
		int opID = insertEvent(ip,"update-Python");
		//����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9300);
			String[] values = new String[3];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = updatePath;
			Message msg = new Message(MsgType.updatePython, opID+"",values);
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
				if (msg.getType().equals(MsgType.updatePython)) {
					String ret = (String) msg.getValues();
					if (ret.equals("success") || ret.equals("executing")) {
						return opID;
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
		return opID;
	}

	public int sendUpdateMemcachedMsg( String ip, String[] scIPAddr) {
		// TODO Auto-generated method stub
		int opID = insertEvent(ip,"update-Memcached");
		//����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9300);
			String[] values = new String[2];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			
			Message msg = new Message(MsgType.updateMemcached, opID+"",values);
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
				if (msg.getType().equals(MsgType.updateMemcached)) {
					String ret = (String) msg.getValues();
					if (ret.equals("success") || ret.equals("executing")) {
						return opID;
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
		return opID;
	}

	public int sendUpdateMemcachedMsgOnLinux( String ip,
			String[] scIPAddr, String updatePath) {
		// TODO Auto-generated method stub
		int opID = insertEvent(ip,"update-Memcached");
		//����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9300);
			String[] values = new String[3];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = updatePath;
			Message msg = new Message(MsgType.updateMemcached, opID+"",values);
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
				if (msg.getType().equals(MsgType.updateMemcached)) {
					String ret = (String) msg.getValues();
					if (ret.equals("success") || ret.equals("executing")) {
						return opID;
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
		return opID;
	}

	public int sendUpdateIISRewriteMsg( String ip,
			String[] scIPAddr, String updatePath) {
		// TODO Auto-generated method stub
		int opID = insertEvent(ip,"update-IISRewrite");
		//����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9300);
			String[] values = new String[3];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = updatePath;
			Message msg = new Message(MsgType.updateIISRewrite, opID+"",values);
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
				if (msg.getType().equals(MsgType.updateIISRewrite)) {
					String ret = (String) msg.getValues();
					if (ret.equals("success") || ret.equals("executing")) {
						return opID;
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
		return opID;
	}

	public int sendUpdateFTPMsg( String ip, String[] scIPAddr,
			String updatePath) {
		// TODO Auto-generated method stub
		int opID = insertEvent(ip,"update-FTP");
		//����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9300);
			String[] values = new String[3];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = updatePath;
			Message msg = new Message(MsgType.updateFTP, opID+"",values);
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
				if (msg.getType().equals(MsgType.updateFTP)) {
					String ret = (String) msg.getValues();
					if (ret.equals("success") || ret.equals("executing")) {
						return opID;
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
		return opID;
	}

	public int sendUpdateFTPMsgOnLinux( String ip, String[] scIPAddr) {
		// TODO Auto-generated method stub
		int opID = insertEvent(ip,"update-FTP");
		//����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9300);
//			String[] values = new String[3];
//			values[0] = scIPAddr[0];
//			values[1] = scIPAddr[1];
			Message msg = new Message(MsgType.updateFTP, opID+"",null);
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
				if (msg.getType().equals(MsgType.updateFTP)) {
					String ret = (String) msg.getValues();
					if (ret.equals("success") || ret.equals("executing")) {
						return opID;
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
		return opID;
	}

	public int sendUpdateSQLServer2008R2Msg( String ip,
			String[] scIPAddr, String updatePath, String rootPswd,
			String hostName, String userName) {
		// TODO Auto-generated method stub
		int opID = insertEvent(ip,"update-SQLServer2008R2");
		//����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9300);
			String[] values = new String[6];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = updatePath;
			values[3] = rootPswd;
			values[4] = hostName;
			values[5] = userName;
			Message msg = new Message(MsgType.updateSQLServer2008R2, opID+"",values);
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
			msg = (Message)SerializeUtil.deserialize(str1); 
				if (msg.getType().equals(MsgType.updateSQLServer2008R2)) {
					String ret = (String) msg.getValues();
					if (ret.equals("success") || ret.equals("executing")) {
						return opID;
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
		return opID;
	}

	public int sendUpdateSQLServer2000Msg( String ip,
			String[] scIPAddr, String updatePath) {
		// TODO Auto-generated method stub
		int opID = insertEvent(ip,"update-SQLServer2000");
		//����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9300);
			String[] values = new String[3];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = updatePath;
			Message msg = new Message(MsgType.updateSQLServer2000, opID+"",values);
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
				if (msg.getType().equals(MsgType.updateSQLServer2000)) {
					String ret = (String) msg.getValues();
					if (ret.equals("success") || ret.equals("executing")) {
						return opID;
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
		return opID;
	}

	public int sendUpdateOracle10gMsg( String ip, String[] scIPAddr,
			String updatePath) {
		// TODO Auto-generated method stub
		int opID = insertEvent(ip,"update-Oracle10g");
		//����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9300);
			String[] values = new String[3];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = updatePath;
			Message msg = new Message(MsgType.updateOracle10g, opID+"",values);
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
				if (msg.getType().equals(MsgType.updateOracle10g)) {
					String ret = (String) msg.getValues();
					if (ret.equals("success") || ret.equals("executing")) {
						return opID;
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
		return opID;
	}

	public int sendUpdateOracle11gMsg( String ip, String[] scIPAddr,
			String hostname, String inventorypath, String oraclebase,
			String oraclehome, String rootPswd) {
		// TODO Auto-generated method stub
		int opID = insertEvent(ip,"update-Oracle11g");
		//����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9300);
			String[] values = new String[7];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = hostname;
			values[3] = inventorypath;
			values[4] = oraclebase;
			values[5] = oraclehome;
			values[6] = rootPswd;
			Message msg = new Message(MsgType.updateOracle11g, opID+"",values);
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
				if (msg.getType().equals(MsgType.updateOracle11g)) {
					String ret = (String) msg.getValues();
					if (ret.equals("success") || ret.equals("executing")) {
						return opID;
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
		return opID;
	}

	public int sendUpdate360Msg( String ip, String[] scIPAddr,
			String updatePath) {
		// TODO Auto-generated method stub
		int opID = insertEvent(ip,"update-360");
		//����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9300);
			String[] values = new String[3];
			values[0] = scIPAddr[0];
			values[1] = scIPAddr[1];
			values[2] = updatePath;
			Message msg = new Message(MsgType.update360, opID+"",values);
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
				if (msg.getType().equals(MsgType.update360)) {
					String ret = (String) msg.getValues();
					if (ret.equals("success") || ret.equals("executing")) {
						return opID;
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
		return opID;
	}

}
