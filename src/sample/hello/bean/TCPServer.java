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
 * 监听所有虚拟机代理软件发送过来的修改事件状态的信息，将事件状态信息更新到数据库中
 * @author hp
 *
 */
public class TCPServer extends Thread{
	// 监听端口
	private static int listenPort;
	private Socket socket;//接入的客户端Socket
	private ServerSocket serverSocket;
	public static void main(String[] args) throws IOException {
		TCPServer ts = new TCPServer();
		ts.start();
	}
	@Override
	public void run() {			
		try {
			Document doc = XmlUtils.getDocument();
			Element root = doc.getRootElement();// 得到根节点
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
		System.out.println("##### TCPServer start to listen "+listenPort+"...");
		while (true) {
//		while (!isInterrupted()) {
			try {
				socket = serverSocket.accept();
				System.out.println("##### There's a client/other nodes connect: "+ socket.getInetAddress().getHostAddress());
				new ResponseThread(socket);
//				System.out.println("有客户端/其他节点接入: "+ socket.getInetAddress().getHostAddress());
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
			byte[] rcvstr = (byte[])ois.readObject();
			//解密
			byte[] str2 = AESUtil.decrypt(rcvstr,socket.getInetAddress().getHostAddress());
			String str1 = new String(str2,"iso-8859-1");
			if(str1.equals("NoSuchAlgorithmException")||str1.equals("NoSuchPaddingException")||str1.equals("InvalidKeyException")||str1.equals("BadPaddingException")||str1.equals("IllegalBlockSizeException")){
				System.out.println("JAVA security, error key");
			}else{
				Message msg = (Message) SerializeUtil.deserialize(str1);
				// Message msg = (Message) ois.readObject();//
				// Message outMes = null;
				// ObjectOutputStream oos = new ObjectOutputStream(
				// socket.getOutputStream());
				// 代理软件给Tomcat发送事件执行状态的信息
				System.out.println("received change op status msg from Agent to Tomcat...");
				MsgType opName = msg.getType();
				int opId = Integer.parseInt(msg.getOpID());
				System.out.println("opName:"+opName.name()+"***opID:"+opId+"***");
				/***
				 * 2015-1-20 15:31更改
				 */
				if(opName.equals(MsgType.executeVMScript)) {
					/**
					 * 虚拟机执行脚本（返回status和执行结果）
					 */
					String[]  info = (String[]) msg.getValues();
					System.out.println("*****opID:" + opId + "\t status:" + info[0]
							+ "\t opName:" + opName + "*****");
					DBOperation dbop = new DBOperation();
					// 在数据库里查询opID对应的记录，改变其status
					dbop.updateOpStatus(opId, info[0],info[1]);
					dbop.close();
					System.out.println("outMes OK!");
					
					
				}else if(opName.equals(MsgType.setupOracle10gInterface)||opName.equals(MsgType.setupOracle11gInterface)
						||opName.equals(MsgType.setupSQLServer2000Interface)||opName.equals(MsgType.setupSQLServer2008R2Interface)){
					/**
					 * 界面安装（返回status和图片）
					 */
					String status = (String) msg.getValues();
					
					//返回的是编码
					if(status.contains("0x0100")) {
						// 在数据库里查询opID对应的记录，改变其status
						DBOperation dbop = new DBOperation();
						dbop.updateOpStatus(opId, status);
						dbop.close();	
						System.out.println("outMes OK!");
						
//					if(status.equals("0x0100A00")||status.equals("0x0100B00")||status.equals("0x0100C00")||status.equals("0x0100D00")) {
//						//安装成功，插入数据库
//						System.out.println("*****opID:" + opId + "\t status:" + status
//								+ "\t opName:" + opName.name() + "*****");
//
//						// 在数据库里查询opID对应的记录，改变其status
//						DBOperation dbop = new DBOperation();
//						dbop.updateOpStatus(opId, status);
//						dbop.close();
//						System.out.println("outMes OK!");	
//						
//					}else {
//						//安装失败，接收文件并存储图片路径
//						System.out.println("*****start reveive picture******");
//						 byte[] inputByte = null;
//					        int length = 0;
//					        DataInputStream dis = null;
//					        FileOutputStream fos = null;
//					        try {
//					            try {
//					                dis = new DataInputStream(socket.getInputStream());
//					                fos = new FileOutputStream(new File("F:/cc.jpg"));
//					                inputByte = new byte[1024];
//					                System.out.println("开始接收数据...");
//					                while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {
//					                    System.out.println(length);
//					                    fos.write(inputByte, 0, length);
//					                    fos.flush();
//					                }
//					                System.out.println("完成接收");
//					            } finally {
//					                if (fos != null)
//					                    fos.close();
//					                if (dis != null)
//					                    dis.close();
//					                if (socket != null)
//					                    socket.close();
//					            }
//					        } catch (Exception e) {
//					        }
//					        System.out.println("*****reveive picture end ******");
//					    	DBOperation dbop = new DBOperation();
//							dbop.updateOpStatus(opId, "F:/cc.jpg");
//							dbop.close();
//							System.out.println("outMes OK!");      
//						}
				} 
				else {
						//返回的是下载完成(无编码信息)
						// 输出获取到的msg信息，测试用
						System.out.println("*****opID:" + opId + "\t status:" + status
								+ "\t opName:" + opName + "*****");
						// 在数据库里查询opID对应的记录，改变其status
						DBOperation dbop = new DBOperation();
						dbop.updateOpStatus(opId, status);
						dbop.close();	
						System.out.println("outMes OK!");
					}	
				}
				else {
					/**
					 * 其他情况，只返回status
					 */
				String status = (String) msg.getValues();
				// 输出获取到的msg信息，测试用
				System.out.println("*****opID:" + opId + "\t status:" + status
						+ "\t opName:" + opName + "*****");
				
				//Linux Oracle安装
				if(opName.equals(MsgType.setupOracle11g)&&status.equals("0x0100C10")) {
					ChangeService cep = new ChangeService("wenyanqi", "123456");  
			        if(cep.isHasError()){  
			            System.out.println(cep.getErrorMessage());  
			            cep = null;  
			            return;  
			        }  
			        cep.setservice();  
			        if(cep.isHasError()){  
			            System.out.println(cep.getErrorMessage());  
			            cep = null;  
			            return;  
			        }  
			        
			     if(cep.isSuccessfully()){  
			            System.out.println(cep.getSystemMessage());  
			        }
			        System.out.println("outMes OK!");
				} else {

				// 在数据库里查询opID对应的记录，改变其status
				DBOperation dbop = new DBOperation();
				dbop.updateOpStatus(opId, status);
				dbop.close();

				// 不给Agent的反馈信息了
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
