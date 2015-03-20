package sample.hello.resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import sample.DBOP.DBOperation;
import sample.hello.bean.ApplicationBase;

@Path("/AppInstallation")
public class AppResource {
	@POST
	@Path("/MySql")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installMySql(@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath,
			@QueryParam("rootPswd") String rootPswd,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		try {
			JSONArray jarr = new JSONArray();
			for (int i = 0; i < ip.size(); i++) {
				JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
				if (version.isEmpty()) {// ����û�û������汾�ţ��������Ҫ�Լ�ѡ��һ���汾��
					String os = dbop.getHostOSByIP(ip.get(i));
					// �ɳ���ѡ�����ݿ������µİ汾��
					version = dbop.queryAppNewestVersion("mysql", os);
				}
				int opID = 0;
				// �жϸ������û���ڸ�������ϰ�װ��
				String dbSoftVersion = dbop
						.queryHostappTableForSoftwareVersion(ip.get(i), "mysql");
				if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����������װ
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i), "mysql",
							version);
					opID = a.sendSetupMySqlMsg(ip.get(i), scIPAddr,
							installPath, rootPswd);
					// ����Ҫ���ظ��û���json����
					entity.put("opID", opID);
					entity.put("status", "������ѽ��հ�װmysql����ɲ�ѯ״̬");//
				} else {// ����Ѱ�װ���û��ַ������ٰ�װ,���ش������0x0101000
					entity.put("code", "0x0101000");
				}
				jarr.put(entity);
			}
			res = Response.ok(jarr).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbop.close();
		return res;
	}

	@POST
	@Path("/MySql_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installMySqlOnLinux(@QueryParam("ip") List<String> ip,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		try {
			JSONArray jarr = new JSONArray();
			for (int i = 0; i < ip.size(); i++) {
				JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
				if (version.isEmpty()) {// ����û�û������汾�ţ��������Ҫ�Լ�ѡ��һ���汾��
					String os = dbop.getHostOSByIP(ip.get(i));
					// �ɳ���ѡ�����ݿ������µİ汾��
					version = dbop.queryAppNewestVersion("mysql", os);
				}
				int opID = 0;
				// �жϸ������û���ڸ�������ϰ�װ��
				String dbSoftVersion = dbop
						.queryHostappTableForSoftwareVersion(ip.get(i), "mysql");
				if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����������װ
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i), "mysql",
							version);
					opID = a.sendSetupMySqlOnLinuxMsg(ip.get(i), scIPAddr);
					// ����Ҫ���ظ��û���json����
					entity.put("opID", opID);
					entity.put("status", "������ѽ��հ�װmysql����ɲ�ѯ״̬");//
				} else {// ����Ѱ�װ���û��ַ������ٰ�װ,���ش������0x0101000
					entity.put("code", "0x0101000");
				}
				jarr.put(entity);
			}
			res = Response.ok(jarr).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbop.close();
		return res;
	}

	@POST
	@Path("/Tomcat")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installTomcat(@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath,
			@QueryParam("jdkPath") String jdkPath,
			@QueryParam("version") String version) {

		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		try {
			JSONArray jarr = new JSONArray();
			for (int i = 0; i < ip.size(); i++) {
				JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
				if (version.isEmpty()) {// ����û�û������汾�ţ��������Ҫ�Լ�ѡ��һ���汾��
					String os = dbop.getHostOSByIP(ip.get(i));
					// �ɳ���ѡ�����ݿ������µİ汾��
					version = dbop.queryAppNewestVersion("tomcat", os);
				}
				int opID = 0;
				// �жϸ������û���ڸ�������ϰ�װ��
				String dbSoftVersion = dbop
						.queryHostappTableForSoftwareVersion(ip.get(i),
								"tomcat");
				if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����������װ
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i), "tomcat",
							version);
					opID = a.sendSetupTomcatMsg(ip.get(i), scIPAddr,
							installPath, jdkPath);
					// ����Ҫ���ظ��û���json����
					entity.put("opID", opID);
					entity.put("status", "������ѽ��հ�װtomcat����ɲ�ѯ״̬");//
				} else {// ����Ѱ�װ���û��ַ������ٰ�װ,���ش������0x0101000
					entity.put("code", "0x0101000");
				}
				jarr.put(entity);
			}
			res = Response.ok(jarr).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbop.close();
		return res;
	}

	@POST
	@Path("/Apache")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installApache(@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath,
			@QueryParam("emailAddress") String emailAddress,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		try {
			JSONArray jarr = new JSONArray();
			for (int i = 0; i < ip.size(); i++) {
				JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
				if (version.isEmpty()) {// ����û�û������汾�ţ��������Ҫ�Լ�ѡ��һ���汾��
					String os = dbop.getHostOSByIP(ip.get(i));
					// �ɳ���ѡ�����ݿ������µİ汾��
					version = dbop.queryAppNewestVersion("apache", os);
				}
				int opID = 0;
				// �жϸ������û���ڸ�������ϰ�װ��
				String dbSoftVersion = dbop
						.queryHostappTableForSoftwareVersion(ip.get(i),
								"apache");
				if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����������װ
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i), "apache",
							version);
					opID = a.sendSetupApacheMsg(ip.get(i), scIPAddr,
							installPath, emailAddress);
					// ����Ҫ���ظ��û���json����
					entity.put("opID", opID);
					entity.put("status", "������ѽ��հ�װapache����ɲ�ѯ״̬");//
				} else {// ����Ѱ�װ���û��ַ������ٰ�װ,���ش������0x0101000
					entity.put("code", "0x0101000");
				}
				jarr.put(entity);
			}
			res = Response.ok(jarr).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbop.close();
		return res;
	}
	@POST
	@Path("/Nginx")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installNginx(@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		try {

			JSONArray jarr = new JSONArray();
			for (int i = 0; i < ip.size(); i++) {
				JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
				if (version.isEmpty()) {// ����û�û������汾�ţ��������Ҫ�Լ�ѡ��һ���汾��
					String os = dbop.getHostOSByIP(ip.get(i));
					// �ɳ���ѡ�����ݿ������µİ汾��
					version = dbop.queryAppNewestVersion("nginx", os);
				}
				int opID = 0;
				// �жϸ������û���ڸ�������ϰ�װ��
				String dbSoftVersion = dbop
						.queryHostappTableForSoftwareVersion(ip.get(i), "nginx");
				if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����������װ
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i), "nginx",
							version);
					opID = a.sendSetupNginxMsg(ip.get(i), scIPAddr, installPath);
					// ����Ҫ���ظ��û���json����
					entity.put("opID", opID);
					entity.put("status", "������ѽ��հ�װnginx����ɲ�ѯ״̬");//
				} else {// ����Ѱ�װ���û��ַ������ٰ�װ,���ش������0x0101000
					entity.put("code", "0x0101000");
				}
				jarr.put(entity);

			}
			res = Response.ok(jarr).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbop.close();
		return res;
	}
	@POST
	@Path("/ZendGuardLoader")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installZendGuardLoader(@QueryParam("ip") List<String> ip,
			@QueryParam("phpPath") String phpPath,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		try {

			JSONArray jarr = new JSONArray();
			for (int i = 0; i < ip.size(); i++) {
				JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
				if (version.isEmpty()) {// ����û�û������汾�ţ��������Ҫ�Լ�ѡ��һ���汾��
					String os = dbop.getHostOSByIP(ip.get(i));
					// �ɳ���ѡ�����ݿ������µİ汾��
					version = dbop.queryAppNewestVersion("zendguardloader", os);
				}
				int opID = 0;
				// �жϸ������û���ڸ�������ϰ�װ��
				String dbSoftVersion = dbop
						.queryHostappTableForSoftwareVersion(ip.get(i),
								"zendguardloader");
				if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����������װ
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i),
							"zendguardloader", version);
					opID = a.sendSetupZendGuardLoaderMsg(ip.get(i), scIPAddr,
							phpPath);
					// ����Ҫ���ظ��û���json����
					entity.put("opID", opID);
					entity.put("status", "������ѽ��հ�װzendGuardLoader����ɲ�ѯ״̬");//
				} else {// ����Ѱ�װ���û��ַ������ٰ�װ,���ش������0x0101000
					entity.put("code", "0x0101000");
				}
				jarr.put(entity);
			}
			res = Response.ok(jarr).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbop.close();
		return res;
	}
	@POST
	@Path("/Python")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installPython(@QueryParam("ip") List<String> ip,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		try {
			JSONArray jarr = new JSONArray();
			for (int i = 0; i < ip.size(); i++) {
				JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
				if (version.isEmpty()) {// ����û�û������汾�ţ��������Ҫ�Լ�ѡ��һ���汾��
					String os = dbop.getHostOSByIP(ip.get(i));
					// �ɳ���ѡ�����ݿ������µİ汾��
					version = dbop.queryAppNewestVersion("python", os);
				}
				int opID = 0;
				// �жϸ������û���ڸ�������ϰ�װ��
				String dbSoftVersion = dbop
						.queryHostappTableForSoftwareVersion(ip.get(i),
								"python");
				if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����������װ
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i), "python",
							version);
					opID = a.sendSetupPythonMsg(ip.get(i), scIPAddr);
					// ����Ҫ���ظ��û���json����
					entity.put("opID", opID);
					entity.put("status", "������ѽ��հ�װpython����ɲ�ѯ״̬");//
				} else {// ����Ѱ�װ���û��ַ������ٰ�װ,���ش������0x0101000
					entity.put("code", "0x0101000");
				}
				jarr.put(entity);
			}
			res = Response.ok(jarr).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbop.close();
		return res;
	}
	@POST
	@Path("/Python_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installPython(@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		try {
			JSONArray jarr = new JSONArray();
			for (int i = 0; i < ip.size(); i++) {
				JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
				if (version.isEmpty()) {// ����û�û������汾�ţ��������Ҫ�Լ�ѡ��һ���汾��
					String os = dbop.getHostOSByIP(ip.get(i));
					// �ɳ���ѡ�����ݿ������µİ汾��
					version = dbop.queryAppNewestVersion("python", os);
				}
				int opID = 0;
				// �жϸ������û���ڸ�������ϰ�װ��
				String dbSoftVersion = dbop
						.queryHostappTableForSoftwareVersion(ip.get(i),
								"python");
				if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����������װ
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i), "python",
							version);
					opID = a.sendSetupPythonMsgOnLinux(ip.get(i), scIPAddr,
							installPath);
					// ����Ҫ���ظ��û���json����
					entity.put("opID", opID);
					entity.put("status", "������ѽ��հ�װpython����ɲ�ѯ״̬");//
				} else {// ����Ѱ�װ���û��ַ������ٰ�װ,���ش������0x0101000
					entity.put("code", "0x0101000");
				}
				jarr.put(entity);
			}
			res = Response.ok(jarr).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbop.close();
		return res;
	}

	@POST
	@Path("/Memcached")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installMemcached(@QueryParam("ip") List<String> ip,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		try {
			JSONArray jarr = new JSONArray();
			for (int i = 0; i < ip.size(); i++) {
				JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
				if (version.isEmpty()) {// ����û�û������汾�ţ��������Ҫ�Լ�ѡ��һ���汾��
					String os = dbop.getHostOSByIP(ip.get(i));
					// �ɳ���ѡ�����ݿ������µİ汾��
					version = dbop.queryAppNewestVersion("memcached", os);
				}
				int opID = 0;
				// �жϸ������û���ڸ�������ϰ�װ��
				String dbSoftVersion = dbop
						.queryHostappTableForSoftwareVersion(ip.get(i),
								"memcached");
				if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����������װ
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i), "memcached",
							version);
					opID = a.sendSetupMemcachedMsg(ip.get(i), scIPAddr);
					// ����Ҫ���ظ��û���json����
					entity.put("opID", opID);
					entity.put("status", "������ѽ��հ�װmemcached����ɲ�ѯ״̬");//
				} else {// ����Ѱ�װ���û��ַ������ٰ�װ,���ش������0x0101000
					entity.put("code", "0x0101000");
				}
				jarr.put(entity);
			}
			res = Response.ok(jarr).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbop.close();
		return res;
	}

	@POST
	@Path("/Memcached_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installMemcached(@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath,
			@QueryParam("version") String version) {

		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		try {
			JSONArray jarr = new JSONArray();
			for (int i = 0; i < ip.size(); i++) {
				JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
				if (version.isEmpty()) {// ����û�û������汾�ţ��������Ҫ�Լ�ѡ��һ���汾��
					String os = dbop.getHostOSByIP(ip.get(i));
					// �ɳ���ѡ�����ݿ������µİ汾��
					version = dbop.queryAppNewestVersion("memcached", os);
				}
				int opID = 0;
				// �жϸ������û���ڸ�������ϰ�װ��
				String dbSoftVersion = dbop
						.queryHostappTableForSoftwareVersion(ip.get(i),
								"memcached");
				if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����������װ
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i), "memcached",
							version);
					opID = a.sendSetupMemcachedMsgOnLinux(ip.get(i), scIPAddr,
							installPath);
					// ����Ҫ���ظ��û���json����
					entity.put("opID", opID);
					entity.put("status", "������ѽ��հ�װmemcached����ɲ�ѯ״̬");//
				} else {// ����Ѱ�װ���û��ַ������ٰ�װ,���ش������0x0101000
					entity.put("code", "0x0101000");
				}
				jarr.put(entity);
			}
			res = Response.ok(jarr).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbop.close();
		return res;
	}

	@POST
	@Path("/IISRewrite")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installIISRewrite(@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		try {
			JSONArray jarr = new JSONArray();
			for (int i = 0; i < ip.size(); i++) {
				JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
				if (version.isEmpty()) {// ����û�û������汾�ţ��������Ҫ�Լ�ѡ��һ���汾��
					String os = dbop.getHostOSByIP(ip.get(i));
					// �ɳ���ѡ�����ݿ������µİ汾��
					version = dbop.queryAppNewestVersion("iisrewrite", os);
				}
				int opID = 0;
				// �жϸ������û���ڸ�������ϰ�װ��
				String dbSoftVersion = dbop
						.queryHostappTableForSoftwareVersion(ip.get(i),
								"iisrewrite");
				if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����������װ
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i),
							"iisrewrite", version);
					opID = a.sendSetupIISRewriteMsg(ip.get(i), scIPAddr,
							installPath);
					// ����Ҫ���ظ��û���json����
					entity.put("opID", opID);
					entity.put("status", "������ѽ��հ�װiisrewrite����ɲ�ѯ״̬");//
				} else {// ����Ѱ�װ���û��ַ������ٰ�װ,���ش������0x0101000
					entity.put("code", "0x0101000");
				}
				jarr.put(entity);
			}
			res = Response.ok(jarr).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbop.close();
		return res;
	}
	@POST
	@Path("/FTP")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installFTP(@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		try {
			JSONArray jarr = new JSONArray();
			for (int i = 0; i < ip.size(); i++) {
				JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
				if (version.isEmpty()) {// ����û�û������汾�ţ��������Ҫ�Լ�ѡ��һ���汾��
					String os = dbop.getHostOSByIP(ip.get(i));
					// �ɳ���ѡ�����ݿ������µİ汾��
					version = dbop.queryAppNewestVersion("ftp", os);
				}
				int opID = 0;
				// �жϸ������û���ڸ�������ϰ�װ��
				String dbSoftVersion = dbop
						.queryHostappTableForSoftwareVersion(ip.get(i), "ftp");
				if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����������װ
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i), "ftp",
							version);
					opID = a.sendSetupFTPMsg(ip.get(i), scIPAddr, installPath);
					// ����Ҫ���ظ��û���json����
					entity.put("opID", opID);
					entity.put("status", "������ѽ��հ�װftp����ɲ�ѯ״̬");//
				} else {// ����Ѱ�װ���û��ַ������ٰ�װ,���ش������0x0101000
					entity.put("code", "0x0101000");
				}
				jarr.put(entity);
			}
			res = Response.ok(jarr).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbop.close();
		return res;
	}

	@POST
	@Path("/FTP_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installFTP(@QueryParam("ip") List<String> ip,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		try {
			JSONArray jarr = new JSONArray();
			for (int i = 0; i < ip.size(); i++) {
				JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
				if (version.isEmpty()) {// ����û�û������汾�ţ��������Ҫ�Լ�ѡ��һ���汾��
					String os = dbop.getHostOSByIP(ip.get(i));
					// �ɳ���ѡ�����ݿ������µİ汾��
					version = dbop.queryAppNewestVersion("ftp", os);
				}
				int opID = 0;
				// �жϸ������û���ڸ�������ϰ�װ��
				String dbSoftVersion = dbop
						.queryHostappTableForSoftwareVersion(ip.get(i), "ftp");
				if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����������װ
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i), "ftp",
							version);
					opID = a.sendSetupFTPMsgOnLinux(ip.get(i), scIPAddr);
					// ����Ҫ���ظ��û���json����
					entity.put("opID", opID);
					entity.put("status", "������ѽ��հ�װftp����ɲ�ѯ״̬");//
				} else {// ����Ѱ�װ���û��ַ������ٰ�װ,���ش������0x0101000
					entity.put("code", "0x0101000");
				}
				jarr.put(entity);
			}
			res = Response.ok(jarr).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbop.close();
		return res;
	}
	@POST
	@Path("/SQLServer2008R2")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installSQLServer2008R2(@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath,
			@QueryParam("rootPswd") String rootPswd,
			@QueryParam("hostName") String hostName,
			@QueryParam("userName") String userName,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		try {
			JSONArray jarr = new JSONArray();
			for (int i = 0; i < ip.size(); i++) {
				JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
				if (version.isEmpty()) {// ����û�û������汾�ţ��������Ҫ�Լ�ѡ��һ���汾��
					String os = dbop.getHostOSByIP(ip.get(i));
					// �ɳ���ѡ�����ݿ������µİ汾��
					version = dbop.queryAppNewestVersion("sqlserver2008r2", os);
				}
				int opID = 0;
				// �жϸ������û���ڸ�������ϰ�װ��
				String dbSoftVersion = dbop
						.queryHostappTableForSoftwareVersion(ip.get(i),
								"sqlserver2008r2");
				if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����������װ
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i),
							"sqlserver2008r2", version);
					opID = a.sendSetupSQLServer2008R2Msg(ip.get(i), scIPAddr,
							installPath, rootPswd, hostName, userName);
					// ����Ҫ���ظ��û���json����
					entity.put("opID", opID);
					entity.put("status", "������ѽ��հ�װSQLServer2008R2����ɲ�ѯ״̬");//
				} else {// ����Ѱ�װ���û��ַ������ٰ�װ,���ش������0x0101000
					entity.put("code", "0x0101000");
				}
				jarr.put(entity);
			}
			res = Response.ok(jarr).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbop.close();
		return res;
	}
	@POST
	@Path("/InterfaceSQLServer2008R2")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response interfaceInstallSQLServer2008R2(
			@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath,
			@QueryParam("rootPswd") String rootPswd,
			@QueryParam("hostName") String hostName,
			@QueryParam("userName") String userName,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		try {
			JSONArray jarr = new JSONArray();
			for (int i = 0; i < ip.size(); i++) {
				JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
				if (version.isEmpty()) {// ����û�û������汾�ţ��������Ҫ�Լ�ѡ��һ���汾��
					String os = dbop.getHostOSByIP(ip.get(i));
					// �ɳ���ѡ�����ݿ������µİ汾��
					version = dbop.queryAppNewestVersion("sqlserver2008r2", os);
				}
				int opID = 0;
				// �жϸ������û���ڸ�������ϰ�װ��
				String dbSoftVersion = dbop
						.queryHostappTableForSoftwareVersion(ip.get(i),
								"sqlserver2008r2");
				if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����������װ
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i),
							"sqlserver2008r2", version);
					opID = a.sendSetupSQLServer2008R2InterfaceMsg(ip.get(i),
							scIPAddr, installPath, rootPswd, hostName, userName);
					// ����Ҫ���ظ��û���json����
					entity.put("opID", opID);
					entity.put("status", "������ѽ��հ�װSQLServer2008R2����ɲ�ѯ״̬");//
				} else {// ����Ѱ�װ���û��ַ������ٰ�װ,���ش������0x0101000
					entity.put("code", "0x0101000");
				}
				jarr.put(entity);
			}
			res = Response.ok(jarr).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbop.close();
		return res;
	}

	@POST
	@Path("/SQLServer2000")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installSQLServer2000(@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		try {
			JSONArray jarr = new JSONArray();
			for (int i = 0; i < ip.size(); i++) {
				JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
				if (version.isEmpty()) {// ����û�û������汾�ţ��������Ҫ�Լ�ѡ��һ���汾��
					String os = dbop.getHostOSByIP(ip.get(i));
					// �ɳ���ѡ�����ݿ������µİ汾��
					version = dbop.queryAppNewestVersion("sqlserver2000", os);
				}
				int opID = 0;
				// �жϸ������û���ڸ�������ϰ�װ��
				String dbSoftVersion = dbop
						.queryHostappTableForSoftwareVersion(ip.get(i),
								"sqlserver2000");
				if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����������װ
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i),
							"sqlserver2000", version);
					opID = a.sendSetupSQLServer2000Msg(ip.get(i), scIPAddr,
							installPath);
					// ����Ҫ���ظ��û���json����
					entity.put("opID", opID);
					entity.put("status", "������ѽ��հ�װSQLServer2000����ɲ�ѯ״̬");//
				} else {// ����Ѱ�װ���û��ַ������ٰ�װ,���ش������0x0101000
					entity.put("code", "0x0101000");
				}
				jarr.put(entity);
			}
			res = Response.ok(jarr).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbop.close();
		return res;
	}

	@POST
	@Path("/InterfaceSQLServer2000")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response interfaceInstallSQLServer2000(
			@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		try {
			JSONArray jarr = new JSONArray();
			for (int i = 0; i < ip.size(); i++) {
				JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
				if (version.isEmpty()) {// ����û�û������汾�ţ��������Ҫ�Լ�ѡ��һ���汾��
					String os = dbop.getHostOSByIP(ip.get(i));
					// �ɳ���ѡ�����ݿ������µİ汾��
					version = dbop.queryAppNewestVersion("sqlserver2000", os);
				}
				int opID = 0;
				// �жϸ������û���ڸ�������ϰ�װ��
				String dbSoftVersion = dbop
						.queryHostappTableForSoftwareVersion(ip.get(i),
								"sqlserver2000");
				if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����������װ
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i),
							"sqlserver2000", version);
					opID = a.sendSetupSQLServer2000InterfaceMsg(ip.get(i),
							scIPAddr, installPath);
					// ����Ҫ���ظ��û���json����
					entity.put("opID", opID);
					entity.put("status", "������ѽ��հ�װSQLServer2000����ɲ�ѯ״̬");//
				} else {// ����Ѱ�װ���û��ַ������ٰ�װ,���ش������0x0101000
					entity.put("code", "0x0101000");
				}
				jarr.put(entity);
			}
			res = Response.ok(jarr).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbop.close();
		return res;
	}
	@POST
	@Path("/Oracle11g_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installOracle11gLinux(@QueryParam("ip") List<String> ip,
			@QueryParam("username") String username,
			@QueryParam("oraclebase") String oraclebase,
			@QueryParam("oracleinventory") String oracleinventory,
			@QueryParam("oraclehome") String oraclehome,
			@QueryParam("oracle_sid") String oracle_sid,
			@QueryParam("rootPswd") String rootPswd,
			@QueryParam("oradata") String oradata,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		try {

			JSONArray jarr = new JSONArray();
			for (int i = 0; i < ip.size(); i++) {
				JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
				if (version.isEmpty()) {// ����û�û������汾�ţ��������Ҫ�Լ�ѡ��һ���汾��
					String os = dbop.getHostOSByIP(ip.get(i));
					// �ɳ���ѡ�����ݿ������µİ汾��
					version = dbop.queryAppNewestVersion("oracle11g", os);
				}
				int opID = 0;

				// �жϸ������û���ڸ�������ϰ�װ��
				String dbSoftVersion = dbop
						.queryHostappTableForSoftwareVersion(ip.get(i),
								"oracle11g");
				if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����������װ
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i), "oracle11g",
							version);
					opID = a.sendSetupOracle11gLinuxMsg(ip.get(i), scIPAddr,
							username, oraclebase, oracleinventory, oraclehome,
							oracle_sid, rootPswd, oradata);
					// ����Ҫ���ظ��û���json����
					entity.put("opID", opID);
					entity.put("status", "������ѽ��հ�װOracle11g����ɲ�ѯ״̬");//
				} else {// ����Ѱ�װ���û��ַ������ٰ�װ,���ش������0x0101000
					entity.put("code", "0x0101000");
				}
				jarr.put(entity);

			}

			res = Response.ok(jarr).build();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbop.close();
		return res;
	}
	@POST
	@Path("/InterfaceOracle10g")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response interfaceInstallOracle10g(
			@QueryParam("ip") List<String> ip,
			@QueryParam("hostname") String hostname,
			@QueryParam("inventorypath") String inventorypath,
			@QueryParam("oraclebase") String oraclebase,
			@QueryParam("oraclehome") String oraclehome,
			@QueryParam("rootPswd") String rootPswd,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		try {

			JSONArray jarr = new JSONArray();
			for (int i = 0; i < ip.size(); i++) {
				JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
				if (version.isEmpty()) {// ����û�û������汾�ţ��������Ҫ�Լ�ѡ��һ���汾��
					String os = dbop.getHostOSByIP(ip.get(i));
					// �ɳ���ѡ�����ݿ������µİ汾��
					version = dbop.queryAppNewestVersion("oracle10g", os);
				}
				int opID = 0;
				// �жϸ������û���ڸ�������ϰ�װ��
				String dbSoftVersion = dbop
						.queryHostappTableForSoftwareVersion(ip.get(i),
								"oracle10g");
				if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����������װ
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i), "oracle10g",
							version);

					opID = a.sendSetupOracle10gInterfaceMsg(ip.get(i),
							scIPAddr, hostname, inventorypath, oraclebase,
							oraclehome, rootPswd);
					// ����Ҫ���ظ��û���json����
					entity.put("opID", opID);
					entity.put("status", "������ѽ��հ�װOracle10g����ɲ�ѯ״̬");//
				} else {// ����Ѱ�װ���û��ַ������ٰ�װ,���ش������0x0101000
					entity.put("code", "0x0101000");
				}

				jarr.put(entity);
			}

			res = Response.ok(jarr).build();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbop.close();
		return res;
	}
	@POST
	@Path("/Oracle11g")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installOracle11g(@QueryParam("ip") List<String> ip,
			@QueryParam("hostname") String hostname,
			@QueryParam("inventorypath") String inventorypath,
			@QueryParam("oraclebase") String oraclebase,
			@QueryParam("oraclehome") String oraclehome,
			@QueryParam("rootPswd") String rootPswd,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		try {

			JSONArray jarr = new JSONArray();
			for (int i = 0; i < ip.size(); i++) {
				JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
				if (version.isEmpty()) {// ����û�û������汾�ţ��������Ҫ�Լ�ѡ��һ���汾��
					String os = dbop.getHostOSByIP(ip.get(i));
					// �ɳ���ѡ�����ݿ������µİ汾��
					version = dbop.queryAppNewestVersion("oracle11g", os);
				}
				int opID = 0;
				// �жϸ������û���ڸ�������ϰ�װ��
				String dbSoftVersion = dbop
						.queryHostappTableForSoftwareVersion(ip.get(i),
								"oracle11g");
				if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����������װ
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i), "oracle11g",
							version);
					opID = a.sendSetupOracle11gMsg(ip.get(i), scIPAddr,
							hostname, inventorypath, oraclebase, oraclehome,
							rootPswd);
					// ����Ҫ���ظ��û���json����
					entity.put("opID", opID);
					entity.put("status", "������ѽ��հ�װOracle11g����ɲ�ѯ״̬");//
				} else {// ����Ѱ�װ���û��ַ������ٰ�װ,���ش������0x0101000
					entity.put("code", "0x0101000");
				}
				jarr.put(entity);

			}

			res = Response.ok(jarr).build();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbop.close();
		return res;
	}
	@POST
	@Path("/InterfaceOracle11g")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response InterfaceinstallOracle11g(
			@QueryParam("ip") List<String> ip,
			@QueryParam("oraclebase") String oraclebase,
			@QueryParam("oraclehome") String oraclehome,
			@QueryParam("inventorypath") String inventorypath,
			@QueryParam("databasename") String databasename,
			@QueryParam("rootPswd") String rootPswd,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		try {

			JSONArray jarr = new JSONArray();
			for (int i = 0; i < ip.size(); i++) {
				JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
				if (version.isEmpty()) {// ����û�û������汾�ţ��������Ҫ�Լ�ѡ��һ���汾��
					String os = dbop.getHostOSByIP(ip.get(i));
					// �ɳ���ѡ�����ݿ������µİ汾��
					version = dbop.queryAppNewestVersion("oracle11g", os);
				}
				int opID = 0;
				// �жϸ������û���ڸ�������ϰ�װ��
				String dbSoftVersion = dbop
						.queryHostappTableForSoftwareVersion(ip.get(i),
								"oracle11g");
				if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����������װ
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i), "oracle11g",
							version);
					opID = a.sendSetupOracle11gInterfaceMsg(ip.get(0),
							scIPAddr, oraclebase, oraclehome, inventorypath,
							databasename, rootPswd);
					// ����Ҫ���ظ��û���json����
					entity.put("opID", opID);
					entity.put("status", "������ѽ��հ�װOracle11g����ɲ�ѯ״̬");//
				} else {// ����Ѱ�װ���û��ַ������ٰ�װ,���ش������0x0101000
					entity.put("code", "0x0101000");
				}

				jarr.put(entity);
			}

			res = Response.ok(jarr).build();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbop.close();
		return res;
	}

	@POST
	@Path("/360")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response install360(@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		try {
			JSONArray jarr = new JSONArray();
			for (int i = 0; i < ip.size(); i++) {
				JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
				if (version.isEmpty()) {// ����û�û������汾�ţ��������Ҫ�Լ�ѡ��һ���汾��
					String os = dbop.getHostOSByIP(ip.get(i));
					// �ɳ���ѡ�����ݿ������µİ汾��
					version = dbop.queryAppNewestVersion("360", os);
				}
				int opID = 0;
				// �жϸ������û���ڸ�������ϰ�װ��
				String dbSoftVersion = dbop
						.queryHostappTableForSoftwareVersion(ip.get(i), "360");
				if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����������װ
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i), "360",
							version);
					opID = a.sendSetup360Msg(ip.get(i), scIPAddr, installPath);
					// ����Ҫ���ظ��û���json����
					entity.put("opID", opID);
					entity.put("status", "������ѽ��հ�װ360����ɲ�ѯ״̬");//
				} else {// ����Ѱ�װ���û��ַ������ٰ�װ,���ش������0x0101000
					entity.put("code", "0x0101000");
				}
				jarr.put(entity);
			}
			res = Response.ok(jarr).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbop.close();
		return res;
	}

}
