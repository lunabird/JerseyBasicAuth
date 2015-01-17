package sample.hello.resources;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import sample.hello.bean.TCPServer;

/**
 * ����Tomcat����ʱ�����й���
 * @author huangpeng
 *
 */
public class ServerSocketListener implements ServletContextListener {
	
	TCPServer ts;
	
	//tomcat�ر�ʱ���ر��̣߳��ͷŶ˿�
	@SuppressWarnings("deprecation")
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		ts.stop();
	}

	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		ts = new TCPServer();
		ts.start();		
	}

}
