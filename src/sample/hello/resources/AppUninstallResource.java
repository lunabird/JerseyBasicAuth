package sample.hello.resources;

import java.sql.SQLException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import sample.DBOP.DBOperation;
import sample.hello.bean.ApplicationUninstallBase;

@Path("/AppUninstall")
public class AppUninstallResource {

	@POST
	@Path("/MySql")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uninstallMySql(@QueryParam("ip") String ip,
			@QueryParam("path") String path) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();
		JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
		DBOperation dbop = new DBOperation();
		try {
			// �жϸ������û���ڸ�������ϰ�װ��
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"mysql");
			if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����ȴҪж�أ����ش�����0x0201000
				entity.put("code", "0x0201000");
			} else {// ����Ѱ�װ������ж��
					// ����Ϣ���͸�Agent�ɹ��Ժ󣬷���һ���������id�Ĵ���eid��ͬʱ��Ҳ�����ݿ��д洢�Ĵ���ò�����eid
				int eid = a.sendUninstallMySqlMsg(ip, path);
				// ����Ҫ���ظ��û���json����
				entity.put("eid", eid);
				entity.put("status", "������ѽ���ж��mysql����ɲ�ѯ״̬");//
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * @POST
	 * 
	 * @Path("/MySql_Linux")
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response
	 * uninstallMySqlOnLinux(
	 * 
	 * @QueryParam("ip") String ip ) { Response res = null;
	 * ApplicationUninstallBase a = new ApplicationUninstallBase(); JSONObject
	 * entity = new JSONObject(); try { int eid =
	 * a.sendUninstallMySqlOnLinuxMsg( ip); entity.put("eid", eid);
	 * entity.put("status", "������ѽ���ж��mysql����ɲ�ѯ״̬"); res =
	 * Response.ok(entity).build(); } catch (JSONException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } return res; }
	 */
	@POST
	@Path("/MySql_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uninstallMySqlOnLinux(@QueryParam("ip") String ip) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();
		JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		try {
			// �жϸ������û���ڸ�������ϰ�װ��
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"mysql");
			if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����ȴҪж�أ����ش�����0x0201000
				entity.put("code", "0x0201000");
			} else {// ����Ѱ�װ������ж��
					// ����Ϣ���͸�Agent�ɹ��Ժ󣬷���һ���������id�Ĵ���eid��ͬʱ��Ҳ�����ݿ��д洢�Ĵ���ò�����eid
				int eid = a.sendUninstallMySqlOnLinuxMsg(ip);
				// ����Ҫ���ظ��û���json����
				entity.put("eid", eid);
				entity.put("status", "������ѽ���ж��mysql����ɲ�ѯ״̬");
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/Tomcat")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uninstallTomcat(@QueryParam("ip") String ip) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();
		JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
		DBOperation dbop = new DBOperation();
		try {
			// �жϸ������û���ڸ�������ϰ�װ��
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"tomcat");
			if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����ȴҪж�أ����ش�����0x0201000
				entity.put("code", "0x0201000");
			} else {// ����Ѱ�װ������ж��
					// ����Ϣ���͸�Agent�ɹ��Ժ󣬷���һ���������id�Ĵ���eid��ͬʱ��Ҳ�����ݿ��д洢�Ĵ���ò�����eid
				int eid = a.sendUninstallTomcatMsg(ip);
				// ����Ҫ���ظ��û���json����
				entity.put("eid", eid);
				entity.put("status", "������ѽ���ж��tomcat����ɲ�ѯ״̬");//
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/Tomcat_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uninstallTomcatOnLinux(@QueryParam("ip") String ip,
			@QueryParam("path") String path) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();
		JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
		DBOperation dbop = new DBOperation();
		try {
			// �жϸ������û���ڸ�������ϰ�װ��
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"tomcat");
			if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����ȴҪж�أ����ش�����0x0201000
				entity.put("code", "0x0201000");
			} else {// ����Ѱ�װ������ж��
					// ����Ϣ���͸�Agent�ɹ��Ժ󣬷���һ���������id�Ĵ���eid��ͬʱ��Ҳ�����ݿ��д洢�Ĵ���ò�����eid
				int eid = a.sendUninstallTomcatLinuxMsg(ip, path);
				// ����Ҫ���ظ��û���json����
				entity.put("eid", eid);
				entity.put("status", "������ѽ���ж��tomcat����ɲ�ѯ״̬");//
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/Jdk")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uninstallJdk(@QueryParam("ip") String ip) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();
		JSONObject entity = new JSONObject();

		try {

			int eid = a.sendUninstallJdkMsg(ip);

			entity.put("eid", eid);
			entity.put("status", "������ѽ���ж��jdk����ɲ�ѯ״̬");
			res = Response.ok(entity).build();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/Apache")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uninstallApache(@QueryParam("ip") String ip) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();
		JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
		DBOperation dbop = new DBOperation();
		try {
			// �жϸ������û���ڸ�������ϰ�װ��
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"apache");
			if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����ȴҪж�أ����ش�����0x0201000
				entity.put("code", "0x0201000");
			} else {// ����Ѱ�װ������ж��
					// ����Ϣ���͸�Agent�ɹ��Ժ󣬷���һ���������id�Ĵ���eid��ͬʱ��Ҳ�����ݿ��д洢�Ĵ���ò�����eid
				int eid = a.sendUninstallApacheMsg(ip);
				// ����Ҫ���ظ��û���json����
				entity.put("eid", eid);
				entity.put("status", "������ѽ���ж��apache����ɲ�ѯ״̬");//
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/Apache_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uninstallApacheOnLinux(@QueryParam("ip") String ip,
			@QueryParam("path") String path) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();
		JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
		DBOperation dbop = new DBOperation();
		try {
			// �жϸ������û���ڸ�������ϰ�װ��
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"apache");
			if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����ȴҪж�أ����ش�����0x0201000
				entity.put("code", "0x0201000");
			} else {// ����Ѱ�װ������ж��
					// ����Ϣ���͸�Agent�ɹ��Ժ󣬷���һ���������id�Ĵ���eid��ͬʱ��Ҳ�����ݿ��д洢�Ĵ���ò�����eid
				int eid = a.sendUninstallApacheLinuxMsg(ip, path);
				// ����Ҫ���ظ��û���json����
				entity.put("eid", eid);
				entity.put("status", "������ѽ���ж��apache����ɲ�ѯ״̬");//
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * @POST
	 * 
	 * @Path("/Nginx")
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response uninstallNginx(
	 * 
	 * @QueryParam("ip") String ip,
	 * 
	 * @QueryParam("path") String Path ) { Response res = null;
	 * ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject
	 * entity = new JSONObject();
	 * 
	 * 
	 * try {
	 * 
	 * 
	 * int eid = a.sendUninstallNginxMsg( ip,Path); entity.put("eid", eid);
	 * entity.put("status", "������ѽ���ж��nginx����ɲ�ѯ״̬"); res =
	 * Response.ok(entity).build(); } catch (JSONException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } return res; }
	 */
	@POST
	@Path("/Nginx")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uninstallNginx(@QueryParam("ip") String ip,
			@QueryParam("path") String Path) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();
		JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		try {
			// �жϸ������û���ڸ�������ϰ�װ��
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"nginx");
			if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����ȴҪж�أ����ش�����0x0201000
				entity.put("code", "0x0201000");
			} else {// ����Ѱ�װ������ж��
					// ����Ϣ���͸�Agent�ɹ��Ժ󣬷���һ���������id�Ĵ���eid��ͬʱ��Ҳ�����ݿ��д洢�Ĵ���ò�����eid
				int eid = a.sendUninstallNginxMsg(ip, Path);
				// ����Ҫ���ظ��û���json����
				entity.put("eid", eid);
				entity.put("status", "������ѽ���ж��nginx����ɲ�ѯ״̬");//
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * @POST
	 * 
	 * @Path("/ZendGuardLoader")
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response
	 * uninstallZendGuardLoader(
	 * 
	 * @QueryParam("ip") String ip,
	 * 
	 * @QueryParam("path") String Path ) { Response res = null;
	 * ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject
	 * entity = new JSONObject();
	 * 
	 * 
	 * try {
	 * 
	 * 
	 * int eid = a.sendUninstallZendGuardLoaderMsg( ip,Path); entity.put("eid",
	 * eid); entity.put("status", "������ѽ���ж��zendGuardLoader����ɲ�ѯ״̬ "); res =
	 * Response.ok(entity).build(); } catch (JSONException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } return res; }
	 */
	@POST
	@Path("/ZendGuardLoader")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uninstallZendGuardLoader(@QueryParam("ip") String ip,
			@QueryParam("path") String Path) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();
		JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();

		try {
			// �жϸ������û���ڸ�������ϰ�װ��
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"zendguardloader");
			if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����ȴҪж�أ����ش�����0x0201000
				entity.put("code", "0x0201000");
			} else {// ����Ѱ�װ������ж��
					// ����Ϣ���͸�Agent�ɹ��Ժ󣬷���һ���������id�Ĵ���eid��ͬʱ��Ҳ�����ݿ��д洢�Ĵ���ò�����eid
				int eid = a.sendUninstallZendGuardLoaderMsg(ip, Path);
				// ����Ҫ���ظ��û���json����
				entity.put("eid", eid);
				entity.put("status", "������ѽ���ж��zendGuardLoader����ɲ�ѯ״̬");//
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	// @POST
	// @Path("/ZendGuardLoader_Linux")
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	// public Response uninstallZendGuardLoader_Linux(
	// @QueryParam("ip") String ip,
	// @QueryParam("path") String Path
	// ) {
	// Response res = null;
	// ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject
	// entity = new JSONObject();
	//
	//
	// try {
	// scIPAddr = dbop.getRCAddrByIP("hp", ip,"zendguardloader");
	//
	// int eid = a.sendUninstallZendGuardLoaderMsgOnLinux( ip,Path);
	// entity.put("eid", eid);
	// entity.put("status",
	// "uninstall ZendGuardLoader has already been executing ");
	// res = Response.ok(entity).build();
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return res;
	// }
	@POST
	@Path("/Python")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uninstallPython(@QueryParam("ip") String ip) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();
		JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
		DBOperation dbop = new DBOperation();
		try {
			// �жϸ������û���ڸ�������ϰ�װ��
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"python");
			if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����ȴҪж�أ����ش�����0x0201000
				entity.put("code", "0x0201000");
			} else {// ����Ѱ�װ������ж��
					// ����Ϣ���͸�Agent�ɹ��Ժ󣬷���һ���������id�Ĵ���eid��ͬʱ��Ҳ�����ݿ��д洢�Ĵ���ò�����eid
				int eid = a.sendUninstallPythonMsg(ip);
				// ����Ҫ���ظ��û���json����
				entity.put("eid", eid);
				entity.put("status", "������ѽ���ж��python����ɲ�ѯ״̬");//
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/Python_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uninstallPythonLinux(@QueryParam("ip") String ip,
			@QueryParam("path") String path) {

		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();
		JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
		DBOperation dbop = new DBOperation();
		try {
			// �жϸ������û���ڸ�������ϰ�װ��
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"python");
			if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����ȴҪж�أ����ش�����0x0201000
				entity.put("code", "0x0201000");
			} else {// ����Ѱ�װ������ж��
					// ����Ϣ���͸�Agent�ɹ��Ժ󣬷���һ���������id�Ĵ���eid��ͬʱ��Ҳ�����ݿ��д洢�Ĵ���ò�����eid
				int eid = a.sendUninstallPythonLinuxMsg(ip, path);
				// ����Ҫ���ظ��û���json����
				entity.put("eid", eid);
				entity.put("status", "������ѽ���ж��python����ɲ�ѯ״̬");//
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * @POST
	 * 
	 * @Path("/Memcached")
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response uninstallMemcached(
	 * 
	 * @QueryParam("ip") String ip,
	 * 
	 * @QueryParam("softName") String softName ) { Response res = null;
	 * ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject
	 * entity = new JSONObject();
	 * 
	 * 
	 * try {
	 * 
	 * 
	 * int eid = a.sendUninstallMemcachedMsg( ip,softName); entity.put("eid",
	 * eid); entity.put("status", "������ѽ���ж��memcached����ɲ�ѯ״̬"); res =
	 * Response.ok(entity).build(); } catch (JSONException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } return res; }
	 */
	@POST
	@Path("/Memcached")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uninstallMemcached(@QueryParam("ip") String ip,
			@QueryParam("softName") String softName) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();
		JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
		DBOperation dbop = new DBOperation();
		try {
			// �жϸ������û���ڸ�������ϰ�װ��
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"memcached");
			if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����ȴҪж�أ����ش�����0x0201000
				entity.put("code", "0x0201000");
			} else {// ����Ѱ�װ������ж��
					// ����Ϣ���͸�Agent�ɹ��Ժ󣬷���һ���������id�Ĵ���eid��ͬʱ��Ҳ�����ݿ��д洢�Ĵ���ò�����eid
				int eid = a.sendUninstallMemcachedMsg(ip, softName);
				// ����Ҫ���ظ��û���json����
				entity.put("eid", eid);
				entity.put("status", "������ѽ���ж��memcached����ɲ�ѯ״̬");
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * @POST
	 * 
	 * @Path("/Memcached_Linux")
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response uninstallMemcached(
	 * 
	 * @QueryParam("ip") String ip,
	 * 
	 * @QueryParam("softName") String softName,
	 * 
	 * @QueryParam("path") String path ) { Response res = null;
	 * ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject
	 * entity = new JSONObject();
	 * 
	 * 
	 * try {
	 * 
	 * 
	 * int eid = a.sendUninstallMemcachedLinuxMsg( ip,softName,path);
	 * entity.put("eid", eid); entity.put("status", "������ѽ���ж��memcached����ɲ�ѯ״̬");
	 * res = Response.ok(entity).build(); } catch (JSONException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } return res; }
	 */
	@POST
	@Path("/Memcached_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uninstallMemcached(@QueryParam("ip") String ip,
			@QueryParam("softName") String softName,
			@QueryParam("path") String path) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();
		JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();

		try {
			// �жϸ������û���ڸ�������ϰ�װ��
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"memcached");
			if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����ȴҪж�أ����ش�����0x0201000
				entity.put("code", "0x0201000");
			} else {// ����Ѱ�װ������ж��
					// ����Ϣ���͸�Agent�ɹ��Ժ󣬷���һ���������id�Ĵ���eid��ͬʱ��Ҳ�����ݿ��д洢�Ĵ���ò�����eid
				int eid = a.sendUninstallMemcachedLinuxMsg(ip, softName, path);
				entity.put("eid", eid);
				entity.put("status", "������ѽ���ж��memcached����ɲ�ѯ״̬");
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	// @POST
	// @Path("/Memcached_Linux")
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	// public Response uninstallMemcachedL(
	// @QueryParam("ip") String ip
	// ) {
	// Response res = null;
	// ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject
	// entity = new JSONObject();
	//
	//
	// try {
	// scIPAddr = dbop.getRCAddrByIP("hp", ip,"memcached");
	//
	// int eid = a.sendUninstallMemcachedMsgOnLinux( ip);
	// entity.put("eid", eid);
	// entity.put("status", "uninstall memcached has already been executing ");
	// res = Response.ok(entity).build();
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block0
	// e.printStackTrace();
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return res;
	// }
	@POST
	@Path("/IISRewrite")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uninstallIISRewrite(@QueryParam("ip") String ip) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();
		JSONObject entity = new JSONObject();

		try {

			int eid = a.sendUninstallIISRewriteMsg(ip);
			entity.put("eid", eid);
			entity.put("status", "������ѽ���ж��IISRewrite����ɲ�ѯ״̬");
			res = Response.ok(entity).build();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	// @POST
	// @Path("/ASP")
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	// public Response uninstallASP(
	// @QueryParam("ip") String ip,
	// @QueryParam("installPath") String installPath
	// ) {
	// Response res = null;
	// ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject
	// entity = new JSONObject();
	//
	//
	// try {
	// scIPAddr = dbop.getRCAddrByIP("hp", ip,"asp");
	// int eid = a.sendUninstallASPMsg( ip);
	// entity.put("eid", eid);
	// entity.put("status", "uninstall ASP has already been executing ");
	// res = Response.ok(entity).build();
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return res;
	// }
	@POST
	@Path("/FTP")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uninstallFTP(@QueryParam("ip") String ip) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();
		JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
		DBOperation dbop = new DBOperation();
		try {
			// �жϸ������û���ڸ�������ϰ�װ��
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"ftp");
			if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����ȴҪж�أ����ش�����0x0201000
				entity.put("code", "0x0201000");
			} else {// ����Ѱ�װ������ж��
					// ����Ϣ���͸�Agent�ɹ��Ժ󣬷���һ���������id�Ĵ���eid��ͬʱ��Ҳ�����ݿ��д洢�Ĵ���ò�����eid
				int eid = a.sendUninstallFTPMsg(ip);
				// ����Ҫ���ظ��û���json����
				entity.put("eid", eid);
				entity.put("status", "������ѽ���ж��ftp����ɲ�ѯ״̬");//
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/FTP_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uninstallFTPL(@QueryParam("ip") String ip) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();
		JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
		DBOperation dbop = new DBOperation();
		try {
			// �жϸ������û���ڸ�������ϰ�װ��
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"ftp");
			if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����ȴҪж�أ����ش�����0x0201000
				entity.put("code", "0x0201000");
			} else {// ����Ѱ�װ������ж��
					// ����Ϣ���͸�Agent�ɹ��Ժ󣬷���һ���������id�Ĵ���eid��ͬʱ��Ҳ�����ݿ��д洢�Ĵ���ò�����eid
				int eid = a.sendUninstallFTPMsgOnLinux(ip);
				// ����Ҫ���ظ��û���json����
				entity.put("eid", eid);
				entity.put("status", "������ѽ���ж��ftp����ɲ�ѯ״̬");//
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	// @POST
	// @Path("/ASPNET")
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	// public Response uninstallASPNET(
	// @QueryParam("ip") String ip,
	// @QueryParam("installPath") String installPath
	// ) {
	// Response res = null;
	// ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject
	// entity = new JSONObject();
	//
	//
	// try {
	// scIPAddr = dbop.getRCAddrByIP("hp", ip,"aspnet");
	// int eid = a.sendUninstallASPNETMsg( ip);
	// entity.put("eid", eid);
	// entity.put("status", "uninstall ASPNET has already been executing ");
	// res = Response.ok(entity).build();
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return res;
	// }
	/*
	 * @POST
	 * 
	 * @Path("/SQLServer2008R2")
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response
	 * uninstallSQLServer2008R2(
	 * 
	 * @QueryParam("ip") String ip ) { Response res = null;
	 * ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject
	 * entity = new JSONObject();
	 * 
	 * 
	 * try {
	 * 
	 * 
	 * int eid = a.sendUninstallSQLServer2008R2Msg( ip); entity.put("eid", eid);
	 * entity.put("status", "������ѽ���ж��SQLServer2008R2����ɲ�ѯ״̬"); res =
	 * Response.ok(entity).build(); } catch (JSONException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } return res; }
	 */
	@POST
	@Path("/SQLServer2008R2")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uninstallSQLServer2008R2(@QueryParam("ip") String ip) {

		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();
		JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
		DBOperation dbop = new DBOperation();
		try {
			// �жϸ������û���ڸ�������ϰ�װ��
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"sqlserver2008r2");
			if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����ȴҪж�أ����ش�����0x0201000
				entity.put("code", "0x0201000");
			} else {// ����Ѱ�װ������ж��
					// ����Ϣ���͸�Agent�ɹ��Ժ󣬷���һ���������id�Ĵ���eid��ͬʱ��Ҳ�����ݿ��д洢�Ĵ���ò�����eid
				int eid = a.sendUninstallSQLServer2008R2Msg(ip);
				// ����Ҫ���ظ��û���json����
				entity.put("eid", eid);
				entity.put("status", "������ѽ���ж��SQLServer2008R2����ɲ�ѯ״̬");//
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/SQLServer2000")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateSQLServer2000(@QueryParam("ip") String ip,
			@QueryParam("updatePath") String updatePath,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();
		JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
		DBOperation dbop = new DBOperation();
		try {
			// �жϸ������û���ڸ�������ϰ�װ��
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"sqlserver2000");
			if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����ȴҪж�أ����ش�����0x0201000
				entity.put("code", "0x0201000");
			} else {// ����Ѱ�װ������ж��
					// ����Ϣ���͸�Agent�ɹ��Ժ󣬷���һ���������id�Ĵ���eid��ͬʱ��Ҳ�����ݿ��д洢�Ĵ���ò�����eid
				int eid = a.sendUninstallSQLServer2000Msg(ip);
				// ����Ҫ���ظ��û���json����
				entity.put("eid", eid);
				entity.put("status", "������ѽ���ж��SQLServer2000����ɲ�ѯ״̬");//
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/Oracle10g")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uninstallOracle10g(@QueryParam("ip") String ip) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();
		JSONObject entity = new JSONObject();

		try {

			int eid = a.sendUninstallOracle10gMsg(ip);
			entity.put("eid", eid);
			entity.put("status", "������ѽ���ж��Oracle10g����ɲ�ѯ״̬");
			res = Response.ok(entity).build();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * @POST
	 * 
	 * @Path("/Oracle11g")
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response uninstallOracle11g(
	 * 
	 * @QueryParam("ip") String ip,
	 * 
	 * @QueryParam("ip") String path ) { Response res = null;
	 * ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject
	 * entity = new JSONObject();
	 * 
	 * 
	 * try {
	 * 
	 * int eid = a.sendUninstallOracle11gMsg( ip,path); entity.put("eid", eid);
	 * entity.put("status", "������ѽ���ж��Oracle11g����ɲ�ѯ״̬"); res =
	 * Response.ok(entity).build(); } catch (JSONException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } return res; }
	 */
	@POST
	@Path("/Oracle11g")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uninstallOracle11g(@QueryParam("ip") String ip,
			@QueryParam("ip") String path) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();
		JSONObject entity = new JSONObject();

		DBOperation dbop = new DBOperation();
		try {
			// �жϸ������û���ڸ�������ϰ�װ��
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"oracle11g");
			if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����ȴҪж�أ����ش�����0x0201000
				entity.put("code", "0x0201000");
			} else {// ����Ѱ�װ������ж��
					// ����Ϣ���͸�Agent�ɹ��Ժ󣬷���һ���������id�Ĵ���eid��ͬʱ��Ҳ�����ݿ��д洢�Ĵ���ò�����eid
				int eid = a.sendUninstallOracle11gMsg(ip, path);
				// ����Ҫ���ظ��û���json����
				entity.put("eid", eid);
				entity.put("status", "������ѽ���ж��Oracle11g����ɲ�ѯ״̬");//
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * @POST
	 * 
	 * @Path("/Oracle11g_Linux")
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response uninstallOracle11g(
	 * 
	 * @QueryParam("ip") String ip,
	 * 
	 * @QueryParam("oraclepath") String oraclepath,
	 * 
	 * @QueryParam("user") String user ) { Response res = null;
	 * ApplicationUninstallBase a = new ApplicationUninstallBase(); JSONObject
	 * entity = new JSONObject();
	 * 
	 * 
	 * try {
	 * 
	 * int eid = a.sendUninstallOracle11gLinuxMsg( ip,oraclepath,user);
	 * entity.put("eid", eid); entity.put("status", "������ѽ���ж��Oracle11g����ɲ�ѯ״̬");
	 * res = Response.ok(entity).build(); } catch (JSONException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } return res; }
	 */
	@POST
	@Path("/Oracle11g_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uninstallOracle11g(@QueryParam("ip") String ip,
			@QueryParam("oraclepath") String oraclepath,
			@QueryParam("user") String user) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();
		JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();

		try {
			// �жϸ������û���ڸ�������ϰ�װ��
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"oracle11g");
			if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����ȴҪж�أ����ش�����0x0201000
				entity.put("code", "0x0201000");
			} else {// ����Ѱ�װ������ж��
					// ����Ϣ���͸�Agent�ɹ��Ժ󣬷���һ���������id�Ĵ���eid��ͬʱ��Ҳ�����ݿ��д洢�Ĵ���ò�����eid
				int eid = a
						.sendUninstallOracle11gLinuxMsg(ip, oraclepath, user);
				// ����Ҫ���ظ��û���json����
				entity.put("eid", eid);
				entity.put("status", "������ѽ���ж��Oracle11g����ɲ�ѯ״̬");//
			}
			dbop.close();

			res = Response.ok(entity).build();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/360")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uninstall360(@QueryParam("ip") String ip) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();
		JSONObject entity = new JSONObject();// ��ʾҪ���û����ص�json����
		DBOperation dbop = new DBOperation();
		try {
			// �жϸ������û���ڸ�������ϰ�װ��
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"360");
			if (dbSoftVersion.isEmpty()) {// ��ʾ�����û���ڸ�������ϰ�װ����ȴҪж�أ����ش�����0x0201000
				entity.put("code", "0x0201000");
			} else {// ����Ѱ�װ������ж��
					// ����Ϣ���͸�Agent�ɹ��Ժ󣬷���һ���������id�Ĵ���eid��ͬʱ��Ҳ�����ݿ��д洢�Ĵ���ò�����eid
				int eid = a.sendUninstall360Msg(ip);
				// ����Ҫ���ظ��û���json����
				entity.put("eid", eid);
				entity.put("status", "������ѽ���ж��360����ɲ�ѯ״̬");//
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

}
