package sample.hello.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

public class ClientLinuxTest {
	
	
	public static void main(String[] args) {
		String time = CurrentTime();
		System.out.println(time);
		Client c = Client.create();
		WebResource r = c
				.resource("http://119.90.140.60:8080/JerseyBasicAuth/rest/");
		
		//uninstallOracle11gLinux(r,time);
		//installOracle11gLinux(r,time);
		//installMemcachedLinux(r,time);
		//updateMemcachedLinux(r,time);
		//uninstallMemcachedLinux(r,time);
		//uninstallApacheLinux(r,time);
		//installApacheLinux(r,time);
		//installZendGuardLoaderLinux(r,time);
		//ZendGuardLoaderParamLinux(r,time);
		//updateZendGuardLoaderLinux(r,time);
		//uninstallZendGuardLoaderLinux(r,time);
		//installNginxLinux(r,time);
		//updateNginxLinux(r,time);
		//uninstallNginxLinux(r,time);
		//installMysqlLinux(r,time);
		//updateMysqlLinux(r,time);
		//uninstallMysqlLinux(r,time);
		//installFTPLinux(r,time);
		//installPythonLinux(r,time);
		//installApacheLinux(r,time);
		//installTomcatLinux(r,time);
		//updateFTPLinux(r,time);
		//uninstallFTPLinux(r,time);
		//updatePythonLinux(r,time);
		//uninstallPythonLinux(r,time);
		//updateApacheLinux(r,time);
		//uninstallApacheLinux(r,time);
		//updateTomcatLinux(r,time);
		//uninstallTomcatLinux(r,time);
		//installTomcatLinux(r,time);
		//changePWDLinux(r,time);
		//uninstallFTPLinux(r,time);
		//uninstallPythonLinux(r,time);
		//uninstallZendGuardLoaderLinux(r,time);
		//uninstallNginxLinux(r,time);
		//uninstallMemcachedLinux(r,time);
		//uninstallApacheLinux(r,time);
		//getParamApacheLinux(r,time);
		//QueryProgress(r,time,"C:\\1.jpg","190","false");
		//getCodeInfo(r,time,"0x0300502");
	}
	
	
	/**
	 * ��ȡ��ǰʱ��
	 */
	private static String CurrentTime() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String time = df.format(now);
		return time;
	}
	

	/**
	 * @author Administrator ��װtomcat
	 * @param r
	 * @param time
	 */
	private static void installTomcatLinux(WebResource r, String time) {
		String response = r.path("AppInstallation/Tomcat")
				.queryParam("ip", "119.90.140.253")
				.queryParam("installPath", "/home")
				.queryParam("jdkPath", "/usr/java/jdk1.7.0_75")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);

	}

	/**
	 * @author Administrator ж�صͰ汾tomcat
	 * @param r
	 * @param time
	 */
	private static void uninstallLTomcatLinux(WebResource r, String time) {
		String response = r.path("AppUninstall/Tomcat_Linux")
				.queryParam("ip", "119.90.140.253")
				.queryParam("path", "/home/apache-tomcat-6.0.41")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);
		
	}
	
	/**
	 * @author Administrator ж�ظ߰汾tomcat
	 * @param r
	 * @param time
	 */
	private static void uninstallHTomcatLinux(WebResource r, String time) {
		String response = r.path("AppUninstall/Tomcat_Linux")
				.queryParam("ip", "119.90.140.253")
				.queryParam("path", "/home/apache-tomcat-7.0.57")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);
		
	}
	

	/**
	 * @author Administrator ����tomcat
	 * @param r
	 * @param time
	 */
	private static void updateTomcatLinux(WebResource r, String time) {
		String response = r.path("AppUpdate/Tomcat_Linux")
				.queryParam("ip", "119.90.140.253")
				.queryParam("unistallPath", "/home/apache-tomcat-6.0.41")
				.queryParam("updatePath", "/home")
				.queryParam("jdkPath", "/usr/java/jdk1.7.0_75")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);
	}

	/**
	 * @author Administrator tomcat��������
	 * @param r
	 * @param time
	 */
	private static void TomcatParamLinux(WebResource r, String time) {
		String response = r.path("AppParamConfiguration/Tomcat")
				.queryParam("ip", "119.90.140.253")
				.queryParam("paramName", "Port")
				.queryParam("paramValue", "8080")
				.queryParam("cfgFilePath", "/home/apache-tomcat-6.0.41")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);
	}

	/**
	 * @author Administrator ��ȡtomcat����
	 * @param r
	 * @param time
	 */
	private static void getParamTomcatLinux(WebResource r, String time) {
		String response = r.path("GetAppParamConfig/Tomcat")
				.queryParam("ip", "119.90.140.253")
				.queryParam("cfgFilePath", "/home/apache-tomcat-6.0.41")
				.queryParam("paramName", "Port")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);
	}

	/**
	 * @author Administrator ��װmysql
	 * @param r
	 * @param time
	 */
	private static void installMysqlLinux(WebResource r, String time) {
		String response = r.path("AppInstallation/MySql_Linux")
				.queryParam("ip", "119.90.140.253")
				.queryParam("rootPswd", "repace")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);
	}

	/**
	 * @author Administrator ж��mysql
	 * @param r
	 * @param time
	 */
	private static void uninstallMysqlLinux(WebResource r, String time) {
		String response = r.path("AppUninstall/MySql_Linux")
				.queryParam("ip", "119.90.140.253")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);
	}

	/**
	 * @author Administrator ����mysql
	 * @param r
	 * @param time
	 */
	private static void updateMysqlLinux(WebResource r, String time) {
		String response = r.path("AppUpdate/MySql_Linux")
				.queryParam("ip", "119.90.140.253")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);
	}

	/**
	 * @author Administrator mysql��������
	 * @param r
	 * @param time
	 */
	private static void MysqlParamLinux(WebResource r, String time) {
		String response = r.path("AppParamConfiguration/MySql")
				.queryParam("ip", "119.90.140.253")
				.queryParam("paramName", "port")
				.queryParam("paramValue", "5413")
				.queryParam("cfgFilePath", "/etc/my.cnf")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);
	}

	/**
	 * @author Administrator ��ȡmysql����
	 * @param r
	 * @param time
	 */
	private static void getParamMySqlLinux(WebResource r, String time) {
		String response = r.path("GetAppParamConfig/MySql_Linux")
				.queryParam("ip", "119.90.140.253")
				.queryParam("paramName", "user")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);
	}

	/**
	 * @author Administrator ��װapache
	 * @param r
	 * @param time
	 */
	private static void installApacheLinux(WebResource r, String time) {
		String response = r.path("AppInstallation/Apache")
				.queryParam("ip", "119.90.140.253")
				.queryParam("installPath", "/home")
				.queryParam("emailAddress", "123@123.com")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);
	}

	/**
	 * @author Administrator ж��apache
	 * @param r
	 * @param time
	 */
	private static void uninstallApacheLinux(WebResource r, String time) {
		String response = r.path("AppUninstall/Apache_Linux")
				.queryParam("ip", "119.90.140.253")
				.queryParam("path", "/home/httpd")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);
	}

	/**
	 * @author Administrator ����apache
	 * @param r
	 * @param time
	 */
	private static void updateApacheLinux(WebResource r, String time) {
		String response = r.path("AppUpdate/Apache_Linux")
				.queryParam("ip", "119.90.140.253")
				.queryParam("unistallPath", "/home")
				.queryParam("updatePath", "/home")
				.queryParam("emailAddress", "123@123.com")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);

	}

	/**
	 * @author Administrator apache��������
	 * @param r
	 * @param time
	 */
	private static void ApacheParamLinux(WebResource r, String time) {
		String response = r.path("AppParamConfiguration/Apache")
				.queryParam("ip", "119.90.140.253")
				.queryParam("paramName", "Listen")
				.queryParam("paramValue", "90")
				.queryParam("cfgFilePath", "/home/httpd")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);
	}

	/**
	 * @author Administrator ��ȡapache����
	 * @param r
	 * @param time
	 */
	private static void getParamApacheLinux(WebResource r, String time) {
		String response = r.path("GetAppParamConfig/Apache")
				.queryParam("ip", "119.90.140.253")
				.queryParam("cfgFilePath", "/home/httpd")
				.queryParam("paramName", "Listen")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);
	}

	/**
	 * @author Administrator ��װnginx
	 * @param r
	 * @param time
	 */
	private static void installNginxLinux(WebResource r, String time) {
		String response = r.path("AppInstallation/Nginx")
				.queryParam("ip", "119.90.140.253")
				.queryParam("installPath", "/home")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);

	}

	/**
	 * @author Administrator ж��nginx
	 * @param r
	 * @param time
	 */
	private static void uninstallNginxLinux(WebResource r, String time) {
		String response = r.path("AppUninstall/Nginx")
				.queryParam("ip", "119.90.140.253")
				.queryParam("path", "/home/nginx")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);

	}

	/**
	 * @author Administrator ����nginx
	 * @param r
	 * @param time
	 */
	private static void updateNginxLinux(WebResource r, String time) {
		String response = r.path("AppUpdate/Nginx_Linux")
				.queryParam("ip", "119.90.140.253")
				.queryParam("updatePath", "/home/nginx")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);

	}

	/**
	 * @author Administrator nginx��������
	 * @param r
	 * @param time
	 */
	private static void NginxParamLinux(WebResource r, String time) {
		String response = r.path("AppParamConfiguration/Nginx")
				.queryParam("ip", "119.90.140.253")
				.queryParam("paramName", "listen")
				.queryParam("paramValue", "5555")
				.queryParam("cfgFilePath", "/home/nginx")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);
	}

	/**
	 * @author Administrator ��ȡnginx����
	 * @param r
	 * @param time
	 */
	private static void getParamNginxLinux(WebResource r, String time) {
		String response = r.path("GetAppParamConfig/Nginx")
				.queryParam("ip", "119.90.140.253")
				.queryParam("cfgFilePath", "/home/nginx")
				.queryParam("paramName", "listen")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);
	}

	/**
	 * @author Administrator ��װZendGuardLoader
	 * @param r
	 * @param time
	 */
	private static void installZendGuardLoaderLinux(WebResource r, String time) {
		String response = r.path("AppInstallation/ZendGuardLoader")
				.queryParam("ip", "119.90.140.253")
				.queryParam("phpPath", "/home/php")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);

	}

	/**
	 * @author Administrator ж��ZendGuardLoader
	 * @param r
	 * @param time
	 */
	private static void uninstallZendGuardLoaderLinux(WebResource r, String time) {
		String response = r.path("AppUninstall/ZendGuardLoader")
				.queryParam("ip", "119.90.140.253")
				.queryParam("path", "/home/php")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);

	}

	/**
	 * @author Administrator ����ZendGuardLoader
	 * @param r
	 * @param time
	 */
	private static void updateZendGuardLoaderLinux(WebResource r, String time) {
		String response = r.path("AppUpdate/ZendGuardLoader")
				.queryParam("ip", "119.90.140.253")
				.queryParam("phpPath", "/home/php")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);

	}

	/**
	 * @author Administrator ZendGuardLoader��������
	 * @param r
	 * @param time
	 */
	private static void ZendGuardLoaderParamLinux(WebResource r, String time) {
		String response = r.path("AppParamConfiguration/ZendGuardLoader")
				.queryParam("ip", "119.90.140.253")
				.queryParam("paramName", "zend_loader.enable")
				.queryParam("paramValue", "0")
				.queryParam("cfgFilePath", "/home/php")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);
	}

	/**
	 * @author Administrator ��ȡZendGuardLoader����
	 * @param r
	 * @param time
	 */
	private static void getParamZendGuardLoaderLinux(WebResource r, String time) {
		String response = r.path("GetAppParamConfig/ZendGuardLoader_Linux")
				.queryParam("ip", "119.90.140.253")
				.queryParam("path", "/home/php")
				.queryParam("paramName", "zend_extension")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);
	}

	/**
	 * @author Administrator ��װmemcached
	 * @param r
	 * @param time
	 */
	private static void installMemcachedLinux(WebResource r, String time) {
		String response = r.path("AppInstallation/Memcached_Linux")
				.queryParam("ip", "119.90.140.253")
				.queryParam("installPath", "/home")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);

	}

	/**
	 * @author Administrator ж��memcached
	 * @param r
	 * @param time
	 */
	private static void uninstallMemcachedLinux(WebResource r, String time) {
		String response = r.path("AppUninstall/Memcached_Linux")
				.queryParam("ip", "119.90.140.253")
				.queryParam("softName", "libevent-2.0.21-stable")
				.queryParam("path", "/home")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);

	}

	/**
	 * @author Administrator ����memcached
	 * @param r
	 * @param time
	 */
	private static void updateMemcachedLinux(WebResource r, String time) {
		String response = r.path("AppUpdate/Memcached_Linux")
				.queryParam("ip", "119.90.140.253")
				.queryParam("uninstallPath", "/home")
				.queryParam("path", "/home")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);

	}

	/**
	 * @author Administrator ��װpython
	 * @param r
	 * @param time
	 */
	private static void installPythonLinux(WebResource r, String time) {
		String response = r.path("AppInstallation/Python_Linux")
				.queryParam("ip", "119.90.140.253")
				.queryParam("installPath", "/home")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);

	}

	/**
	 * @author Administrator ж��python
	 * @param r
	 * @param time
	 */
	private static void uninstallPythonLinux(WebResource r, String time) {
		String response = r.path("AppUninstall/Python_Linux")
				.queryParam("ip", "119.90.140.253")
				.queryParam("path", "/home/Python-2.7.3")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);

	}

	/**
	 * @author Administrator ����python
	 * @param r
	 * @param time
	 */
	private static void updatePythonLinux(WebResource r, String time) {
		String response = r.path("AppUpdate/Python_Linux")
				.queryParam("ip", "119.90.140.253")
				.queryParam("unistallPath", "/home/Python-2.7.3")
				.queryParam("updatePath", "/home")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);

	}

	/**
	 * @author Administrator ��װftp
	 * @param r
	 * @param time
	 */
	private static void installFTPLinux(WebResource r, String time) {
		String response = r.path("AppInstallation/FTP_Linux")
				.queryParam("ip", "119.90.140.253")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);

	}

	/**
	 * @author Administrator ж��ftp
	 * @param r
	 * @param time
	 */
	private static void uninstallFTPLinux(WebResource r, String time) {
		String response = r.path("AppUninstall/FTP_Linux")
				.queryParam("ip", "119.90.140.253")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);

	}

	/**
	 * @author Administrator ����ftp
	 * @param r
	 * @param time
	 */
	private static void updateFTPLinux(WebResource r, String time) {
		String response = r.path("AppUpdate/FTP_Linux")
				.queryParam("ip", "119.90.140.253")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);

	}

	/**
	 * @author Administrator FTP��������
	 * @param r
	 * @param time
	 */
	private static void FTPParamLinux(WebResource r, String time) {
		String response = r.path("AppParamConfiguration/FTP")
				.queryParam("ip", "119.90.140.253")
				.queryParam("paramName", "Port")
				.queryParam("paramValue", "8080")
				.queryParam("cfgFilePath", "/etc/vsftpd")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);
	}

	/**
	 * @author Administrator ��ȡftp����
	 * @param r
	 * @param time
	 */
	private static void getParamFTPLinux(WebResource r, String time) {
		String response = r.path("GetAppParamConfig/FTP")
				.queryParam("ip", "119.90.140.253")
				.queryParam("cfgFilePath", "/etc/vsftpd")
				.queryParam("paramName", "listen")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);
	}

	/**
	 * @author Administrator ��װoracle
	 * @param r
	 * @param time
	 */
	private static void installOracle11gLinux(WebResource r, String time) {
		// String oraclebase="/u01/app/oracle";
		// String oracleinventory="/u01/app/oraInventory";
		// String oraclehome="/u01/app/oracle/product";
		// String oracle_sid = "orcl";
		String response = r
				.path("AppInstallation/Oracle11g_Linux")
				.queryParam("ip", "119.90.140.253")
				.queryParam("username", "wenyanqi")
				.queryParam("oraclebase", "/u01/app/oracle")
				.queryParam("oracleinventory", "/u01/app/oraInventory")
				.queryParam("oraclehome", "/u01/app/oracle/product")
				.queryParam("oracle_sid", "orcl")
				.queryParam("rootPswd", "123456QWq")
				.queryParam("oradata", "/u01/app/oradata")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);
	}
	
	
	/**
	 * @author Administrator ж��oracle
	 * @param r
	 * @param time
	 */
	private static void uninstallOracle11gLinux(WebResource r, String time) {
		String response = r
				.path("AppUninstall/Oracle11g_Linux")
				.queryParam("ip", "119.90.140.253")
				.queryParam("oraclepath", "/u01/app")
				.queryParam("user", "wenyanqi")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);
	}

	/**
	 * @author Administrator �����ִ�нű�
	 * @param r
	 * @param time
	 * @param FilePath
	 */
	private static void vmScriptLinux(WebResource r, String time,
			String FilePath) {
		final File fileToUpload = new File(FilePath);
		FormDataMultiPart multiPart = new FormDataMultiPart();
		String ip = "119.90.140.253";
		if (fileToUpload != null) {
			multiPart = ((FormDataMultiPart) multiPart
					.bodyPart(new FileDataBodyPart("upload", fileToUpload,
							MediaType.APPLICATION_OCTET_STREAM_TYPE)));
		}
		final ClientResponse clientResp = r.path("vmScript/upload")
				.queryParam("ip", ip).type(MediaType.MULTIPART_FORM_DATA_TYPE)
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).post(ClientResponse.class, multiPart);
		System.out.println(clientResp.getEntity(JSONObject.class));
	}

	/**
	 * @author Administrator ������������--�޸�����
	 * @param r
	 * @param time
	 */
	private static void changePWDLinux(WebResource r, String time) {
		String response = r.path("OSBase/vmSysPwd")
				.queryParam("ip", "119.90.140.253")
				.queryParam("userName", "root").queryParam("passwd", "Dianzikeda2015#")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.put(String.class);
		System.out.println(response);
	}

	/**
	 * @author Administrator ������������--��ͣ����
	 * @param r
	 * @param time
	 */
	private static void startOrStopServiceLinux(WebResource r, String time) {
		String response = r.path("OSBase/sysServiceConfig")
				.queryParam("ip", "119.90.140.253")
				.queryParam("serviceName", "auditd")
				.queryParam("operation", "start")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.put(String.class);
		System.out.println(response);
	}

	/**
	 * @author Administrator ������������--�޸İ�ȫ����
	 * @param r
	 * @param time
	 */
	private static void changeSecRulesLinux(WebResource r, String time) {
		String response = r.path("OSBase/secRule_Linux")
				.queryParam("ip", "119.90.140.253")
				.queryParam("protocol", "TCP")
				.queryParam("sourceIP", "119.90.140.60")
				.queryParam("port", "8888")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.put(String.class);
		System.out.println(response);
	}

	// public static void changeIPLinux(WebResource r, String time) {
	// String response = r.path("OSBase/IPAdd_Linux")
	// .queryParam("ip", "119.90.140.253")
	// .queryParam("deviceName", "xxx")
	// .queryParam("mask", "255.255.254.0")
	// .queryParam("changeToIP", "119.90.140.253")
	// .header("password", "EDGM@MAMABDACFDLLG")
	// .header("time", time).type(MediaType.APPLICATION_JSON)
	// .post(String.class);
	// System.out.println(response);
	// }

	/**
	 * @author Administrator ������������--�޸ĸ���IP
	 * @param r
	 * @param time
	 */
	private static void changeAffiIPLinux(WebResource r, String time) {
		String response = r.path("OSBase/affiIPAdd_Linux")
				.queryParam("ip", "119.90.140.253")
				.queryParam("deviceName", "eth0:1")
				.queryParam("mask", "255.255.254.0")
				.queryParam("changeToIP", "119.90.140.254")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.put(String.class);
		System.out.println(response);
	}

	/**
	 * @author Administrator ������������--ulimit
	 * @param r
	 * @param time
	 */
	private static void ulimit(WebResource r, String time) {
		String response = r.path("OSBase/ulimit_Linux")
				.queryParam("ip", "119.90.140.253")
				.queryParam("ulimit", "65535")
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.put(String.class);
		System.out.println(response);
	}

	/**
	 * @author Administrator ���Ȳ�ѯ
	 * @param r
	 * @param FilePath
	 * @param time
	 * @param opID
	 * @param isUpdate
	 */
	public static void QueryProgress(WebResource r, String time,
			String FilePath, String opID, String isUpdate) {
		JSONObject response = r.path("ProgressQuery/progress")
				.queryParam("opID", opID)
				.queryParam("isUpdate", isUpdate)
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.get(JSONObject.class);
		try {
			System.out.println(response.get("status"));
			int category = (Integer) response.get("category");
			if (category == 1) {
				try {
					writeString((String) response.get("detailInfo"), FilePath);
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else {
				System.out.println(response.get("detailInfo"));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static void writeString(String data, String filePath)
			throws IOException {
		FileOutputStream outStream = new FileOutputStream(filePath);
		outStream.write(data.getBytes("ISO-8859-1"));
		outStream.close();
	}

	/**
	 * @author Administrator ��ñ�����Ϣ
	 * @param r
	 * @param time
	 */
	public static void getCodeInfo(WebResource r, String time, String codeID) {
		String response = r.path("Code/GetCodeInfo")
				.queryParam("codeID", codeID)
				.header("password", "EDGM@MAMABDACFDLLG")
				.header("time", time).type(MediaType.APPLICATION_JSON)
				.post(String.class);
		System.out.println(response);

	}
}
