package sample.hello.bean;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import sample.DBOP.DBOperation;
import edu.xidian.enc.AESUtil;
import edu.xidian.enc.MD5Util;
import edu.xidian.enc.SerializeUtil;
import edu.xidian.message.Message;
import edu.xidian.message.MsgType;

public class OSBase {
//	public static void main(String[] args){
//		OSBase ob = new OSBase();
//		ob.sendChangePasswdMsg("uid321321", "192.168.0.202", "Administrator", "5413");
//	}
	
	/**
	 * ���������������¼����뵽���ݿ���
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
	 * ���»����������õ�״̬����������opinfo��
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
	 * �޸�����
	 * @param uid
	 * @param ip
	 * @param cUserName
	 * @param cPasswd
	 * @return
	 * @throws SQLException 
	 */
	public String sendChangePasswdMsg(String ip,String cUserName,String cPasswd){
		int opID = insertEvent(ip,"changePasswd");
		//����Socket��Ϣ��Agent
		try {
			System.out.println("ip*************************"+ip);
			Socket socket = new Socket(ip, 9000);
			String[] values=new String[2];
			values[0]=cUserName;
			values[1]=cPasswd;
			Message msg = new Message(MsgType.changePasswd, opID+"",values);
			
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
				if (msg.getType().equals(MsgType.changePasswd)) {
					String ret = (String)msg.getValues();
					if(ret.equals("0x0000000")){
						//�����ݿ�����¸�op��״̬
						updateOpStatus(opID,"0x0000000");
						return "0x0000000";
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
		updateOpStatus(opID,"0x0000001");
		return "0x0000001";
	}
	/**
	 * �鿴ϵͳ����
	 * @param uid
	 * @param ip
	 */
	/*public void sendGetSysServiceMsg(String ip){
		try {
			Socket socket = new Socket(ip, 9000);
			String[] values=new String[2];

			Message msg = new Message(MsgType.getSysService, opID+"",null);
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(msg);
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			msg = (Message) ois.readObject();
			if (msg.getType().equals(MsgType.getSysService)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")){
					
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
	}*/
	/**
	 * ����/ֹͣ ϵͳ����
	 * @param uid
	 * @param ip
	 * @return
	 * @throws SQLException 
	 */
	public String sendchgSysServiceStateMsg(String ip,String serviceName,String operation){
		int opID = -1;
		try {
			Socket socket = new Socket(ip, 9000);
			String[] values=new String[2];
			values[0] = serviceName;
			values[1] = operation;
			
			Message msg;
			if(operation.equals("start")){
				opID = insertEvent(ip,"startService");
				msg = new Message(MsgType.startService, opID+"",values[0]);
			}else{
				opID = insertEvent(ip,"stopService");
				msg = new Message(MsgType.stopService, opID+"",values[0]);
			}

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
				if (msg.getType().equals(MsgType.startService)) {
					String ret = (String) msg.getValues();
					if (ret.equals("0x0000100")) {
						updateOpStatus(opID, "0x0000100");
						return "0x0000100";
					} else {
						updateOpStatus(opID, "0x0000101");
						return "0x0000101";
					}
				} else if (msg.getType().equals(MsgType.stopService)) {
					String ret = (String) msg.getValues();
					if (ret.equals("0x0000200")) {
						updateOpStatus(opID, "0x0000200");
						return "0x0000200";
					} else {
						updateOpStatus(opID, "0x0000201");
						return "0x0000201";
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
		return "";
	}
	/**
	 * �鿴ϵͳ��־
	 * @param uid
	 * @param ip
	 * @param logType
	 * @return
	 */
	public JSONObject sendViewSysLogMsg(String ip,String logType){
		JSONObject o = new JSONObject();
		int opID = insertEvent(ip,"viewErrLog");
		//����Socket��Ϣ��Agent
		/*try {
			Socket socket = new Socket(ip, 9000);
			String[] values=new String[1];
			values[0]=logType;
			Message msg = new Message(MsgType.viewErrLog, opID+"",values);
			//����
			String datatemp = SerializeUtil.serialize(msg);  
            String str = MD5Util.convertMD5(datatemp);
            //����
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//��÷�����Ϣ
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			str = (String)ois.readObject();
			//����
			String str2 = MD5Util.convertMD5(str);
			msg = (Message)SerializeUtil.deserialize(str2); 
			if (msg.getType().equals(MsgType.viewErrLog)) {
				//�˴�Ӧ�÷���һ����־�ļ� �ַ���
//				String ret = (msg.getValues())[0];
//				if(ret.equals("success")){
//					return true;
//				}
//				
				o.put(logType, "This is a windows log.");
			}
			socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		try {
			o.put(logType, "This is a windows log.");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}
	/**
	 * �޸�����IP on Linux
	 * @param uid
	 * @param ip
	 * @param mac
	 * @param mask
	 * @param dns
	 * @return
	 */
	public String sendChangeIPOnLinuxMsg(String ip,String deviceName,String mask,String changeToIP){
		int opID = insertEvent(ip,"changeIP");//����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9000);
			String[] values = new String[3];
			values[0]=deviceName;
			values[1]=mask;
			values[2]=changeToIP;
			Message msg = new Message(MsgType.changeIP, opID+"",values);
			//����
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
			//����
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//��÷�����Ϣ
			socket.setSoTimeout(3000);
			try {
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
					if (msg.getType().equals(MsgType.changeIP)) {
						// �˴�Ӧ�÷���ִ�н��ʧ��
						System.out.println("modify ip addr failed!");
					}
				}
			} catch (SocketTimeoutException ste) {
				// �޸����ݿ⣬��ԭ����ip�ĳ�changeToIP
				System.out.println("modify ip addr success!");
				DBOperation dbop = new DBOperation();
				dbop.updateHostIP(ip, changeToIP);
				dbop.close();
				updateOpStatus(opID,"0x0000500");
				return  "0x0000500";
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
		updateOpStatus(opID,"0x0000501");
		return "0x0000501";
	}
	/**
	 * windows�޸�����affi IP
	 * @param uid
	 * @param ip
	 * @param mac
	 * @param mask
	 * @param gateway
	 * @param dns
	 * @param affiIP
	 * @param affiMask
	 * @return
	 * @throws SQLException 
	 */
	public String sendChangeAffiIPMsg( String ip, String mac, String[] affiIP,
			String[] affiMask,String[] affiGateway){
		int opID = insertEvent(ip,"changeAffiIP");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9000);
			Object[] values = new Object[3];
			values[0] = affiIP;
			values[1] = affiMask;
			values[2] = affiGateway;
			Message msg = new Message(MsgType.changeAffiIP, opID+"", values);
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
				if (msg.getType().equals(MsgType.changeAffiIP)) {
					String ret = (String) msg.getValues();
					if (ret.equals("0x0000600")) {
						System.out.println("windows change affi ip :" + ret);
						updateOpStatus(opID, "0x0000600");
						return "0x0000600";
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
		updateOpStatus(opID,"0x0000601");
		return "0x0000601";
	}
	/*public boolean sendChangeAffiIPMsg( String ip, String mac,String changeToIp,
			String mask,String gateway, String[] dns, String[] affiIP,
			String[] affiMask,String[] affiGateway) {
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9000);
			Object[] values = new Object[7];
//			values[0] = mac;
			values[0] = changeToIp;
			values[1] = mask;
			values[2] = gateway;
			
			values[3] = dns;
			values[4] = affiIP;
			values[5] = affiMask;
			values[6] = affiGateway;
			Message msg = new Message(MsgType.changeAffiIP, opID+"", values);
			//����
			String datatemp = SerializeUtil.serialize(msg);  
            String str = MD5Util.convertMD5(datatemp);
            //����
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//��÷�����Ϣ  -------  -*-
			socket.setSoTimeout(3000);
			try {
				ObjectInputStream ois = new ObjectInputStream(
						socket.getInputStream());
				str = (String) ois.readObject();
				// ����
				String str2 = MD5Util.convertMD5(str);
				msg = (Message) SerializeUtil.deserialize(str2);
				if (msg.getType().equals(MsgType.changeAffiIP)) {
					// �˴�Ӧ�÷���ִ�н��ʧ��
					String ret = (String)msg.getValues();
					if(ret.equals("success")){
						return true;
					}else{
					System.out.println("modify ip addr failed!");
						return false;
					}
				}
			} catch (SocketTimeoutException ste) {
				// �޸����ݿ⣬��ԭ����ip�ĳ�changeToIP
				System.out.println("modify ip addr success!");
				DBOperation dbop = new DBOperation();
				dbop.updateHostIP(ip, changeToIp);
				return true;
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
		return false;
	}*/
	/**
	 * windows change ip
	 * @param uid
	 * @param ip
	 * @param mac
	 * @param changeToIp
	 * @param mask
	 * @param gateway
	 * @param dns
	 * @return
	 */
	public String sendChangeIPMsg( String ip, String mac,String changeToIp,
			String mask,String gateway, String[] dns) {
		int opID = insertEvent(ip,"changeIP");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9000);
			Object[] values = new Object[4];
//			values[0] = mac;
			values[0] = changeToIp;
			values[1] = mask;
			values[2] = gateway;
			values[3] = dns;
			Message msg = new Message(MsgType.changeIP, opID+"", values);
			//����
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
			//����
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			
			//��÷�����Ϣ  -------  -*-
			socket.setSoTimeout(3000);
			try {
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
					if (msg.getType().equals(MsgType.changeIP)) {
						//
						String ret = (String) msg.getValues();
						if (ret.equals("0x0000500")) {
							updateOpStatus(opID, "0x0000500");
							return "0x0000500";
						} else {
							System.out.println("modify ip addr failed!");
							updateOpStatus(opID, "0x0000501");
							return "0x0000501";
						}
					}
				}
			} catch (SocketTimeoutException ste) {
				// �޸����ݿ⣬��ԭ����ip�ĳ�changeToIP
				System.out.println("modify windows ip addr success!");
				DBOperation dbop = new DBOperation();
				dbop.updateHostIP(ip, changeToIp);
				dbop.close();
				updateOpStatus(opID,"0x0000500");
				return "0x0000500";
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
		updateOpStatus(opID,"0x0000501");
		return "0x0000501";
	}
	/**
	 * �޸���������IP on Linux
	 * @param uid
	 * @param ip
	 * @param deviceName
	 * @param mask
	 * @param changeToIp
	 * @return
	 * @throws SQLException 
	 */
	public String sendChangeAffiIPOnLinuxMsg( String ip, String deviceName,String mask,String changeToIp) {
		int opID = insertEvent(ip,"changeAffiIP");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9000);
			String[] values = new String[3];
			values[0] = deviceName;
			values[1] = mask;
			values[2] = changeToIp;
			Message msg = new Message(MsgType.changeAffiIP, opID+"", values);
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
				if (msg.getType().equals(MsgType.changeAffiIP)) {
					String ret = (String) msg.getValues();
					if (ret.equals("0x0000600")) {
						updateOpStatus(opID, "0x0000600");
						return "0x0000600";
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
		updateOpStatus(opID,"0x0000601");
		return "0x0000601";
	}
	/**
	 * ���̸�ʽ��
	 * @param uid
	 * @param ip
	 * @return
	 */
	public String sendDiskFormatMsg( String ip) {
		int opID = insertEvent(ip,"diskFormat");
		// ����Socket��Ϣ��Agent
		try {
			Socket socket = new Socket(ip, 9000);
			Message msg = new Message(MsgType.diskFormat, opID+"", null);
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
				if (msg.getType().equals(MsgType.diskFormat)) {
					String ret = (String) msg.getValues();
					if (ret.equals("0x0000300")) {
						updateOpStatus(opID, "0x0000300");
						return "0x0000300";
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
		updateOpStatus(opID,"0x0000301");
		return "0x0000301";
	}
	/**
	 * �޸İ�ȫ����
	 * @param uid
	 * @param ip
	 * @param policyName
	 * @param protocol
	 * @param port
	 * @param addSecIP
	 * @return
	 */
	public String sendChangeSecRuleMsg(String ip,String policyName,String protocol, String port,String addSecIP){
		int opID = insertEvent(ip,"changeSecRule");
		try {
			Socket socket = new Socket(ip, 9000);
			String[] values = new String[4];
			values[0] = policyName;
			values[1] = protocol;
			values[2] = port;
			values[3] = addSecIP;
			Message msg = new Message(MsgType.changeSecRule, opID+"", values);
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
				if (msg.getType().equals(MsgType.changeSecRule)) {
					String ret = (String) msg.getValues();
					if (ret.equals("0x0000400")) {
						updateOpStatus(opID, "0x0000400");
						return "0x0000400";
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
		updateOpStatus(opID,"0x0000401");
		return "0x0000401";
	}
	/**
	 * �޸İ�ȫ���� on linux
	 * @param uid
	 * @param ip
	 * @param policyName
	 * @param protocol
	 * @param port
	 * @param addSecIP
	 * @return
	 */
	public String sendChangeSecRuleOnLinuxMsg(String ip,String protocol,String sourceIP, String port){
		int opID = insertEvent(ip,"changeSecRule");
		try {
			Socket socket = new Socket(ip, 9000);
			String[] values = new String[4];
			values[0] = protocol;
			values[1] = sourceIP;
			values[2] = port;
			Message msg = new Message(MsgType.changeSecRule, opID+"", values);
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
				if (msg.getType().equals(MsgType.changeSecRule)) {
					String ret = (String) msg.getValues();
					if (ret.equals("0x0000400")) {
						updateOpStatus(opID, "0x0000400");
						return "0x0000400";
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
		updateOpStatus(opID,"0x0000401");
		return "0x0000401";
	}
	
	
	public String sendChangeUlimitOnLinuxMsg(String ip,String ulimit){
		int opID = insertEvent(ip,"changeUlimit");
		try {
			Socket socket = new Socket(ip, 9000);
			Message msg = new Message(MsgType.changeUlimit, opID+"", ulimit);
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
				if (msg.getType().equals(MsgType.changeUlimit)) {
					String ret = (String) msg.getValues();
					if (ret.equals("0x0000700")) {
						updateOpStatus(opID, "0x0000700");
						return "0x0000700";
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
		updateOpStatus(opID,"0x0000701");
		return "0x0000701";
	}
}
