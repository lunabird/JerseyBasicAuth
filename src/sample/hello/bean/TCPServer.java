package sample.hello.bean;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import sample.DBOP.DBOperation;
import edu.xidian.enc.AESUtil;
import edu.xidian.enc.SerializeUtil;
import edu.xidian.message.Message;
import edu.xidian.message.MsgType;
import edu.xidian.message.XmlUtils;

/**
 * �����������������������͹������޸��¼�״̬����Ϣ��
 * ���¼�״̬��Ϣ���µ����ݿ���
 * @author hp
 * 
 */
public class TCPServer extends Thread {
	// �����˿�
	private static int listenPort;
	private Socket socket;// ����Ŀͻ���Socket
	private ServerSocket serverSocket;

	public static void main(String[] args) throws IOException {
		TCPServer ts = new TCPServer();
		ts.start();
	}

	@Override
	public void run() {
		try {
			Document doc = XmlUtils.getDocument();
			Element root = doc.getRootElement();// �õ����ڵ�
			Element pathEle = root.element("listen-port");
			listenPort = Integer.parseInt(pathEle.getText());
			serverSocket = new ServerSocket(listenPort);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("##### TCPServer start to listen " + listenPort
				+ "...");
		while (true) {
			// while (!isInterrupted()) {
			try {
				socket = serverSocket.accept();
				System.out
						.println("##### There's a client/other nodes connect: "
								+ socket.getInetAddress().getHostAddress());
				new ResponseThread(socket);
				// System.out.println("�пͻ���/�����ڵ����: "+
				// socket.getInetAddress().getHostAddress());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}

class ResponseThread extends Thread {
	private Socket socket;

	public ResponseThread(Socket socket) {
		this.socket = socket;
		start();
	}

	@Override
	public void run() {
		System.out.println("listening....");
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			byte[] rcvstr = (byte[]) ois.readObject();
			// ����
			byte[] str2 = AESUtil.decrypt(rcvstr, socket.getInetAddress()
					.getHostAddress());
			String str1 = new String(str2, "iso-8859-1");
			if (str1.equals("NoSuchAlgorithmException")
					|| str1.equals("NoSuchPaddingException")
					|| str1.equals("InvalidKeyException")
					|| str1.equals("BadPaddingException")
					|| str1.equals("IllegalBlockSizeException")) {
				System.out.println("JAVA security, error key");
			} else {
				Message msg = (Message) SerializeUtil.deserialize(str1);
				// Message msg = (Message) ois.readObject();//
				// Message outMes = null;
				// ObjectOutputStream oos = new ObjectOutputStream(
				// socket.getOutputStream());
				// ���������Tomcat�����¼�ִ��״̬����Ϣ
				System.out
						.println("received change op status msg from Agent to Tomcat...");
				MsgType opName = msg.getType();
				int opId = Integer.parseInt(msg.getOpID());
				System.out.println("opName:" + opName.name() + "***opID:"
						+ opId + "***");

				/***
				 * 2015-1-20 15:31����
				 */
				if (opName.equals(MsgType.executeVMScript)) {
					/**
					 * �����ִ�нű�������status��ִ�н����
					 */
					String[] info = (String[]) msg.getValues();
					System.out.println("*****opID:" + opId + "\t status:"
							+ info[0] + "\t opName:" + opName + "*****");
					DBOperation dbop = new DBOperation();
					// �����ݿ����ѯopID��Ӧ�ļ�¼���ı���status
					dbop.updateOpStatus(opId, info[0], info[1]);
					dbop.close();
					System.out.println("outMes OK!");

				} else if (opName.equals(MsgType.setupOracle10gInterface)
						|| opName.equals(MsgType.setupOracle11gInterface)
						|| opName.equals(MsgType.setupSQLServer2000Interface)
						|| opName.equals(MsgType.setupSQLServer2008R2Interface)) {
					/**
					 * ���氲װ
					 */
					String status = (String) msg.getValues();
					// �����ȡ����msg��Ϣ��������
					System.out.println("*****opID:" + opId + "\t status:"
							+ status + "\t opName:" + opName + "*****");
					DBOperation dbop = new DBOperation();
					String softVersion = dbop.queryOpTableForVersion(opId);
					// ����������װ�ɹ����صı��룬����һ����¼��hostapp����
					String regEx1 = "0x0100.00";
					if (Pattern.matches(regEx1, status)) {
						dbop.insertHostappTable(
								socket.getInetAddress().getHostAddress(),
								opName.toString().substring(5,
										opName.toString().length()-9),
								softVersion);
					}
					//����opinfo��
					dbop.updateOpStatus(opId, status);
					dbop.close();
					System.out.println("outMes OK!");
					
				} else {
					/**
					 * ���������ֻ����status
					 */
					String status = (String) msg.getValues();
					// �����ȡ����msg��Ϣ��������
					System.out.println("*****opID:" + opId + "\t status:"
							+ status + "\t opName:" + opName + "*****");

					DBOperation dbop = new DBOperation();
					String softVersion = dbop.queryOpTableForVersion(opId);
					// ����������װ�ɹ����صı��룬����һ����¼��hostapp����
					String regEx1 = "0x0100.00";
					String regEx2 = "0x0200.00";
					String regEx3 = "0x0400.00";
					if (Pattern.matches(regEx1, status)) {
						dbop.insertHostappTable(
								socket.getInetAddress().getHostAddress(),
								opName.toString().substring(5,
										opName.toString().length()),
								softVersion);
					}
					// ��������ж�سɹ����صı��룬ɾ��һ����¼��hostapp����
					else if (Pattern.matches(regEx2, status)) {
						dbop.deleteHostappTable(
								socket.getInetAddress().getHostAddress(),
								opName.toString().substring(9,
										opName.toString().length()));
					}
					// �����������³ɹ����صı��룬����һ����¼��hostapp����
					else if (Pattern.matches(regEx3, status)) {
						dbop.updateHostappTable(
								socket.getInetAddress().getHostAddress(),
								opName.toString().substring(6,
										opName.toString().length()),
								softVersion);
					}

					// Linux Oracle��װ
					if (opName.equals(MsgType.setupOracle11g)
							&& status.equals("0x0100C10")) {
						ChangeService cep = new ChangeService("wenyanqi",
								"123456");
						if (cep.isHasError()) {
							System.out.println(cep.getErrorMessage());
							cep = null;
							return;
						}
						cep.setservice();
						if (cep.isHasError()) {
							System.out.println(cep.getErrorMessage());
							cep = null;
							return;
						}

						if (cep.isSuccessfully()) {
							System.out.println(cep.getSystemMessage());
						}
						System.out.println("outMes OK!");
					} else {

						// �����ݿ����ѯopID��Ӧ�ļ�¼���ı���status
						// DBOperation dbop = new DBOperation();
						dbop.updateOpStatus(opId, status);
						dbop.close();

						// ����Agent�ķ�����Ϣ��
						// outMes = new Message();
						// oos.writeObject("OK.I've receiced your message.");
						// oos.flush();
						System.out.println("outMes OK!");
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
