package edu.xidian.enc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import edu.xidian.message.Message;
import edu.xidian.message.MsgType;

public class ClientTest {
	
	/*public static void main(String[] args) {
		System.out.println("client is starting...");
		try {
			ArrayList<String> info = new ArrayList<String>();
			info.add("hello");
			info.add("world");
			Message outMes=new Message(MsgType.changeIP,info);
			//加密
			System.out.println("*************加密*************");
			String datatemp = SerializeUtil.serialize(outMes);  
            String str = MD5Util.convertMD5(datatemp);
			//System.out.println(str);
			 //传输
			Socket socket = new Socket("127.0.0.1",10000);
			ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(str);
			oos.flush();
			//获得信息
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			str = (String)ois.readObject();
			//解密
			String str2 = MD5Util.convertMD5(str);
			Message msg = (Message)SerializeUtil.deserialize(str2);  
		    if(msg.getType().equals(MsgType.SendFile)) {
		    	System.out.println(msg.getBody());
			}
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}*/
}
