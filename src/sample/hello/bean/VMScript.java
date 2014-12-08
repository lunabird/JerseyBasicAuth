package sample.hello.bean;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import edu.xidian.enc.MD5Util;
import edu.xidian.enc.SerializeUtil;
import edu.xidian.message.Message;
import edu.xidian.message.MsgType;

public class VMScript {

	public boolean sendExeVmScriptMsg(String uid, String ip, File file) {
		try {
			Socket socket = new Socket(ip, 9400);

			Message msg = new Message(MsgType.executeVMScript, uid,
					file.getName());
			// 加密
			String datatemp = SerializeUtil.serialize(msg);
			String str = MD5Util.convertMD5(datatemp);
			// 传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			oos.flush();
			// 发送文件
			System.out.println("文件长度为：" + file.length());
			DataOutputStream dos = new DataOutputStream(
					socket.getOutputStream());
			FileInputStream fis = new FileInputStream(file.getPath());
			// 传送文件长度
			dos.writeLong((long) file.length());
			dos.flush();
			System.out.println(file.length());
			System.out.println("开始发送文件" + file.getName() + "...");

			// 缓冲区大小
			int bufferSize = 8192;
			// 缓冲区
			byte[] buf = new byte[bufferSize];
			// 传输文件
			while (true) {
				int read = 0;
				if (fis != null) {
					read = fis.read(buf);

				}
				if (read == -1) {
					break;
				}
				dos.write(buf, 0, read);

			}
			dos.flush();
			System.out.println("传输完成");

			// 获得反馈信息
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			str = (String) ois.readObject();
			// 解密
			String str2 = MD5Util.convertMD5(str);
			msg = (Message) SerializeUtil.deserialize(str2);
			if (msg.getType().equals(MsgType.executeVMScript)) {
				String ret = (String) msg.getValues();
				if (ret.equals("success") || ret.equals("executing")) {
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
