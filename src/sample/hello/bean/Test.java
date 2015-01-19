package sample.hello.bean;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import edu.xidian.message.Message;
import edu.xidian.message.MsgType;

public class Test {
	public static void main(String[] args) {
		System.out.println( ("setup-Tomcat").split("-")[1]);
		/*try {
			Socket socket = new Socket("127.0.0.1", 9000);
			String[] values=new String[2];
			values[0]=;
			values[1]=;
			Message msg = new Message(MsgType.changePasswd, userID,values);
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(msg);
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			msg = (Message) ois.readObject();
			if (msg.getType().equals(MsgType.changePasswd)) {
				String ret = (msg.getValues())[0];
				System.out.println(ret);
			}
			socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

}
