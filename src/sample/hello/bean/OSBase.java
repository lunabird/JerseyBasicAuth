package sample.hello.bean;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import sample.DBOP.DBOperation;
import edu.xidian.enc.MD5Util;
import edu.xidian.enc.SerializeUtil;
import edu.xidian.message.Message;
import edu.xidian.message.MsgType;

public class OSBase {
	String hostName;
	String IP;
	Map<String,String> userPasswd;
	Map<String,String> sysService;
	File[] sysLog;
	String[] affiIP;
	String[] mask;
	String[] dns;
	
	public String getHostName(){
		return hostName;
	}
	public void setHostName(String hostName){
		this.hostName = hostName;
	}
	public Map<String,String> getUserPasswd(){
		return userPasswd;
	}
	public void setUserPasswd(Map<String,String> userPasswd){
		this.userPasswd = userPasswd;
	}
	public Map<String,String> getSysService(){
		return sysService;
	}
	public void setSysService(Map<String,String> sysService){
		this.sysService = sysService;
	}
	public File[] getSysLog(){
		return sysLog;
	}
	public void setSysLog(File[] sysLog){
		this.sysLog = sysLog;
	}
	public String[] getAffiIP(){
		return affiIP;
	}
	public void setAffiIP(String[] affiIP){
		this.affiIP = affiIP;
	}
	public String[] getMask(){
		return mask;
	}
	public void setMask(String[] mask){
		this.mask = mask;
	}
	public String[] getDns(){
		return dns;
	}
	public void setDns(String[] dns){
		this.dns = dns;
	}
//	public static void main(String[] args){
//		OSBase ob = new OSBase();
//		ob.sendChangePasswdMsg("uid321321", "192.168.0.202", "Administrator", "5413");
//	}
	/**
	 * 修改密码
	 * @param uid
	 * @param ip
	 * @param cUserName
	 * @param cPasswd
	 * @return
	 */
	public boolean sendChangePasswdMsg(String uid,String ip,String cUserName,String cPasswd){
		//发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9000);
			String[] values=new String[2];
			values[0]=cUserName;
			values[1]=cPasswd;
			Message msg = new Message(MsgType.changePasswd, uid,values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
            String str = MD5Util.convertMD5(datatemp);
            //传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			str = (String)ois.readObject();
			//解密
			String str2 = MD5Util.convertMD5(str);
			msg = (Message)SerializeUtil.deserialize(str2); 
			if (msg.getType().equals(MsgType.changePasswd)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")){
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
	}
	/**
	 * 查看系统服务
	 * @param uid
	 * @param ip
	 */
	public void sendGetSysServiceMsg(String uid,String ip){
		try {
			Socket socket = new Socket(ip, 9000);
			String[] values=new String[2];

			Message msg = new Message(MsgType.getSysService, uid,null);
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
	}
	/**
	 * 启动/停止 系统服务
	 * @param uid
	 * @param ip
	 * @return
	 */
	public boolean sendchgSysServiceStateMsg(String uid,String ip,String serviceName,String operation){
		
		try {
			Socket socket = new Socket(ip, 9000);
			String[] values=new String[2];
			values[0] = serviceName;
			values[1] = operation;
			
			Message msg;
			if(operation.equals("start")){
				msg = new Message(MsgType.startService, uid,values[0]);
			}else{
				msg = new Message(MsgType.stopService, uid,values[0]);
			}

			//加密
			String datatemp = SerializeUtil.serialize(msg);  
            String str = MD5Util.convertMD5(datatemp);
            //传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			str = (String)ois.readObject();
			//解密
			String str2 = MD5Util.convertMD5(str);
			msg = (Message)SerializeUtil.deserialize(str2); 
			if (msg.getType().equals(MsgType.startService)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")){
					return true;
				}
				System.out.println(ret);
			}else if(msg.getType().equals(MsgType.stopService)){
				String ret = (String)msg.getValues();
				if(ret.equals("success")){
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
	}
	/**
	 * 查看系统日志
	 * @param uid
	 * @param ip
	 * @param logType
	 * @return
	 */
	public JSONObject sendViewSysLogMsg(String uid,String ip,String logType){
		JSONObject o = new JSONObject();
		//发送Socket消息给Agent
		/*try {
			Socket socket = new Socket(ip, 9000);
			String[] values=new String[1];
			values[0]=logType;
			Message msg = new Message(MsgType.viewErrLog, uid,values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
            String str = MD5Util.convertMD5(datatemp);
            //传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			str = (String)ois.readObject();
			//解密
			String str2 = MD5Util.convertMD5(str);
			msg = (Message)SerializeUtil.deserialize(str2); 
			if (msg.getType().equals(MsgType.viewErrLog)) {
				//此处应该返回一个日志文件 字符串
//				String ret = (msg.getValues())[0];
//				if(ret.equals("success")){
//					return true;
//				}
//				System.out.println(ret);
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
	 * 修改主机IP on Linux
	 * @param uid
	 * @param ip
	 * @param mac
	 * @param mask
	 * @param dns
	 * @return
	 */
	public boolean sendChangeIPOnLinuxMsg(String uid,String ip,String deviceName,String mask,String changeToIP){
		//发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9000);
			String[] values = new String[3];
			values[0]=deviceName;
			values[1]=mask;
			values[2]=changeToIP;
			Message msg = new Message(MsgType.changeIP, uid,values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
            String str = MD5Util.convertMD5(datatemp);
            //传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			socket.setSoTimeout(3000);
			try {
				ObjectInputStream ois = new ObjectInputStream(
						socket.getInputStream());
				str = (String) ois.readObject();
				// 解密
				String str2 = MD5Util.convertMD5(str);
				msg = (Message) SerializeUtil.deserialize(str2);
				if (msg.getType().equals(MsgType.changeAffiIP)) {
					// 此处应该返回执行结果失败
					System.out.println("modify ip addr failed!");
				}
			} catch (SocketTimeoutException ste) {
				// 修改数据库，将原来的ip改成changeToIP
				System.out.println("modify ip addr success!");
				DBOperation dbop = new DBOperation();
				dbop.updateHostIP(ip, changeToIP);
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
	}
	/**
	 * windows修改主机affi IP
	 * @param uid
	 * @param ip
	 * @param mac
	 * @param mask
	 * @param gateway
	 * @param dns
	 * @param affiIP
	 * @param affiMask
	 * @return
	 */
	public boolean sendChangeAffiIPMsg(String uid, String ip, String mac, String[] affiIP,
			String[] affiMask,String[] affiGateway) {
		// 发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9000);
			Object[] values = new Object[3];
			values[0] = affiIP;
			values[1] = affiMask;
			values[2] = affiGateway;
			Message msg = new Message(MsgType.changeAffiIP, uid, values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
            String str = MD5Util.convertMD5(datatemp);
            //传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			str = (String)ois.readObject();
			//解密
			String str2 = MD5Util.convertMD5(str);
			msg = (Message)SerializeUtil.deserialize(str2); 
			if (msg.getType().equals(MsgType.changeAffiIP)) {
				String ret = (String)msg.getValues();
				if(ret.equals("success")){
					System.out.println("windows change affi ip :"+ret);
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
	}
	/*public boolean sendChangeAffiIPMsg(String uid, String ip, String mac,String changeToIp,
			String mask,String gateway, String[] dns, String[] affiIP,
			String[] affiMask,String[] affiGateway) {
		// 发送Socket消息给Agent
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
			Message msg = new Message(MsgType.changeAffiIP, uid, values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
            String str = MD5Util.convertMD5(datatemp);
            //传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息  -------  -*-
			socket.setSoTimeout(3000);
			try {
				ObjectInputStream ois = new ObjectInputStream(
						socket.getInputStream());
				str = (String) ois.readObject();
				// 解密
				String str2 = MD5Util.convertMD5(str);
				msg = (Message) SerializeUtil.deserialize(str2);
				if (msg.getType().equals(MsgType.changeAffiIP)) {
					// 此处应该返回执行结果失败
					String ret = (String)msg.getValues();
					if(ret.equals("success")){
						return true;
					}else{
					System.out.println("modify ip addr failed!");
						return false;
					}
				}
			} catch (SocketTimeoutException ste) {
				// 修改数据库，将原来的ip改成changeToIP
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
	public boolean sendChangeIPMsg(String uid, String ip, String mac,String changeToIp,
			String mask,String gateway, String[] dns) {
		// 发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9000);
			Object[] values = new Object[4];
//			values[0] = mac;
			values[0] = changeToIp;
			values[1] = mask;
			values[2] = gateway;
			values[3] = dns;
			
			Message msg = new Message(MsgType.changeIP, uid, values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
            String str = MD5Util.convertMD5(datatemp);
            //传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息  -------  -*-
			socket.setSoTimeout(3000);
			try {
				ObjectInputStream ois = new ObjectInputStream(
						socket.getInputStream());
				str = (String) ois.readObject();
				// 解密
				String str2 = MD5Util.convertMD5(str);
				msg = (Message) SerializeUtil.deserialize(str2);
				if (msg.getType().equals(MsgType.changeIP)) {
					// 此处应该返回执行结果失败
					String ret = (String)msg.getValues();
					if(ret.equals("success")){
						return true;
					}else{
						System.out.println("modify ip addr failed!");
						return false;
					}
				}
			} catch (SocketTimeoutException ste) {
				// 修改数据库，将原来的ip改成changeToIP
				System.out.println("modify windows ip addr success!");
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
	}
	/**
	 * 修改主机附属IP on Linux
	 * @param uid
	 * @param ip
	 * @param deviceName
	 * @param mask
	 * @param changeToIp
	 * @return
	 */
	public boolean sendChangeAffiIPOnLinuxMsg(String uid, String ip, String deviceName,String mask,String changeToIp) {
		// 发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9000);
			String[] values = new String[3];
			values[0] = deviceName;
			values[1] = mask;
			values[2] = changeToIp;
			
			Message msg = new Message(MsgType.changeAffiIP, uid, values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
            String str = MD5Util.convertMD5(datatemp);
            //传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			str = (String)ois.readObject();
			//解密
			String str2 = MD5Util.convertMD5(str);
			msg = (Message)SerializeUtil.deserialize(str2); 
			if (msg.getType().equals(MsgType.changeAffiIP)) {
				String ret = (String)msg.getValues();
				if (ret.equals("success")) {
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
	}
	/**
	 * 磁盘格式化
	 * @param uid
	 * @param ip
	 * @return
	 */
	public boolean sendDiskFormatMsg(String uid, String ip) {
		// 发送Socket消息给Agent
		try {
			Socket socket = new Socket(ip, 9000);
			Message msg = new Message(MsgType.diskFormat, uid, null);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
            String str = MD5Util.convertMD5(datatemp);
            //传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			str = (String)ois.readObject();
			//解密
			String str2 = MD5Util.convertMD5(str);
			msg = (Message)SerializeUtil.deserialize(str2); 
			if (msg.getType().equals(MsgType.diskFormat)) {
				String ret = (String)msg.getValues();
				if (ret.equals("success")) {
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
	}
	/**
	 * 修改安全规则
	 * @param uid
	 * @param ip
	 * @param policyName
	 * @param protocol
	 * @param port
	 * @param addSecIP
	 * @return
	 */
	public boolean sendChangeSecRuleMsg(String uid,String ip,String policyName,String protocol, String port,String addSecIP){
		try {
			Socket socket = new Socket(ip, 9000);
			String[] values = new String[4];
			values[0] = policyName;
			values[1] = protocol;
			values[2] = port;
			values[3] = addSecIP;
			Message msg = new Message(MsgType.changeSecRule, uid, values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
            String str = MD5Util.convertMD5(datatemp);
            //传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			str = (String)ois.readObject();
			//解密
			String str2 = MD5Util.convertMD5(str);
			msg = (Message)SerializeUtil.deserialize(str2); 
			if (msg.getType().equals(MsgType.changeSecRule)) {
				String ret = (String)msg.getValues();
				if (ret.equals("success")) {
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
	}
	/**
	 * 修改安全规则 on linux
	 * @param uid
	 * @param ip
	 * @param policyName
	 * @param protocol
	 * @param port
	 * @param addSecIP
	 * @return
	 */
	public boolean sendChangeSecRuleOnLinuxMsg(String uid,String ip,String protocol,String sourceIP, String port){
		try {
			Socket socket = new Socket(ip, 9000);
			String[] values = new String[4];
			values[0] = protocol;
			values[1] = sourceIP;
			values[2] = port;
			Message msg = new Message(MsgType.changeSecRule, uid, values);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
            String str = MD5Util.convertMD5(datatemp);
            //传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			str = (String)ois.readObject();
			//解密
			String str2 = MD5Util.convertMD5(str);
			msg = (Message)SerializeUtil.deserialize(str2); 
			if (msg.getType().equals(MsgType.changeSecRule)) {
				String ret = (String)msg.getValues();
				if (ret.equals("success")) {
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
	}
	
	
	public boolean sendChangeUlimitOnLinuxMsg(String uid,String ip,String ulimit){
		try {
			Socket socket = new Socket(ip, 9000);
			Message msg = new Message(MsgType.changeUlimit, uid, ulimit);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
            String str = MD5Util.convertMD5(datatemp);
            //传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			//获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			str = (String)ois.readObject();
			//解密
			String str2 = MD5Util.convertMD5(str);
			msg = (Message)SerializeUtil.deserialize(str2); 
			if (msg.getType().equals(MsgType.changeUlimit)) {
				String ret = (String)msg.getValues();
				if (ret.equals("success")) {
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
	}
}
