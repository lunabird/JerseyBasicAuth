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

import sample.DBOP.AppVersionMng;
import sample.DBOP.DBOperation;
import sample.hello.bean.ApplicationUpdateBase;

@Path("/AppUpdate")
public class AppUpdateResource {

	@POST
	@Path("/MySql")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMySql(@QueryParam("ip") String ip,
			@QueryParam("uninstallPath") String uninstallPath,
			@QueryParam("updatePath") String updatePath,
			@QueryParam("rootPswd") String rootPswd,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();
		JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		AppVersionMng avm = new AppVersionMng();
		try {
			String os = dbop.getHostOSByIP(ip);
			// 由程序选择数据库里最新的版本号
			String newestV = dbop.queryAppNewestVersion("mysql", os);
			if (version.isEmpty()) {
				version = newestV;
			}
			// 判断该软件有没有在该虚拟机上安装过
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"mysql");
			if (dbSoftVersion.isEmpty()) {// 表示该软件没有在该虚拟机上安装过，却要更新，提示错误码0x0401000
				entity.put("code", "0x0401000");
			} else if (dbSoftVersion.equals(newestV)) {// 表示该软件在该虚拟机上安装了最新的版本，无需更新
				entity.put("code", "0x0401003");
			} else if (dbSoftVersion.equals(version)) {// 表示用户输入的版本和已经安装的版本相同，无需更新,提示错误码
				entity.put("code", "0x0401002");
			} else {
				String tempNewVersion = avm.whichIsNew(dbSoftVersion, version);
				if (tempNewVersion.equals(dbSoftVersion)) {// 表示现在已将安装的版本较新，无需更新,提示错误码
					entity.put("code", "0x0401001");
				} else {// 用户输入的版本比较新，可以正常更新
					scIPAddr = dbop.getRCAddrByIP("hp", ip, "mysql", version);
					// 将消息发送给Agent成功以后，返回一个代表操作id的代号eid，同时这也是数据库中存储的代表该操作的eid
					int eid = a.sendUpdateMySqlMsg(ip, scIPAddr, uninstallPath,
							updatePath, rootPswd);
					// 构造要返回给用户的json对象
					entity.put("eid", eid);
					entity.put("status", "虚拟机已接收更新mysql请求可查询状态");//
				}
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/MySql_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMySqlOnLinux(@QueryParam("ip") String ip,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();
		JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		AppVersionMng avm = new AppVersionMng();
		try {
			String os = dbop.getHostOSByIP(ip);
			// 由程序选择数据库里最新的版本号
			String newestV = dbop.queryAppNewestVersion("mysql", os);
			if (version.isEmpty()) {
				version = newestV;
			}
			// 判断该软件有没有在该虚拟机上安装过
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"mysql");
			if (dbSoftVersion.isEmpty()) {// 表示该软件没有在该虚拟机上安装过，却要更新，提示错误码0x0401000
				entity.put("code", "0x0401000");
			} else if (dbSoftVersion.equals(newestV)) {// 表示该软件在该虚拟机上安装了最新的版本，无需更新
				entity.put("code", "0x0401003");
			} else if (dbSoftVersion.equals(version)) {// 表示用户输入的版本和已经安装的版本相同，无需更新,提示错误码
				entity.put("code", "0x0401002");
			} else {
				String tempNewVersion = avm.whichIsNew(dbSoftVersion, version);
				if (tempNewVersion.equals(dbSoftVersion)) {// 表示现在已将安装的版本较新，无需更新,提示错误码
					entity.put("code", "0x0401001");
				} else {// 用户输入的版本比较新，可以正常更新
					scIPAddr = dbop.getRCAddrByIP("hp", ip, "mysql", version);
					int eid = a.sendUpdateMySqlOnLinuxMsg(ip, scIPAddr);
					entity.put("eid", eid);
					entity.put("status", "虚拟机已接收更新mysql请求可查询状态");
				}
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/Tomcat")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateTomcat(@QueryParam("ip") String ip,
			@QueryParam("updatePath") String updatePath,
			@QueryParam("jdkPath") String jdkPath,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();
		JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		AppVersionMng avm = new AppVersionMng();
		try {
			String os = dbop.getHostOSByIP(ip);
			// 由程序选择数据库里最新的版本号
			String newestV = dbop.queryAppNewestVersion("tomcat", os);
			if (version.isEmpty()) {
				version = newestV;
			}
			// 判断该软件有没有在该虚拟机上安装过
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"tomcat");
			if (dbSoftVersion.isEmpty()) {// 表示该软件没有在该虚拟机上安装过，却要更新，提示错误码0x0401000
				entity.put("code", "0x0401000");
			} else if (dbSoftVersion.equals(newestV)) {// 表示该软件在该虚拟机上安装了最新的版本，无需更新
				entity.put("code", "0x0401003");
			} else if (dbSoftVersion.equals(version)) {// 表示用户输入的版本和已经安装的版本相同，无需更新,提示错误码
				entity.put("code", "0x0401002");
			} else {
				String tempNewVersion = avm.whichIsNew(dbSoftVersion, version);
				if (tempNewVersion.equals(dbSoftVersion)) {// 表示现在已将安装的版本较新，无需更新,提示错误码
					entity.put("code", "0x0401001");
				} else {// 用户输入的版本比较新，可以正常更新
					scIPAddr = dbop.getRCAddrByIP("hp", ip, "tomcat", version);
					// 将消息发送给Agent成功以后，返回一个代表操作id的代号eid，同时这也是数据库中存储的代表该操作的eid
					int eid = a.sendUpdateTomcatMsg(ip, scIPAddr, updatePath,
							jdkPath);
					// 构造要返回给用户的json对象
					entity.put("eid", eid);
					entity.put("status", "虚拟机已接收更新tomcat请求可查询状态");//
				}
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/Tomcat_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateTomcatOnLinux(@QueryParam("ip") String ip,
			@QueryParam("unistallPath") String unistallPath,
			@QueryParam("updatePath") String updatePath,
			@QueryParam("jdkPath") String jdkPath,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();
		JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		AppVersionMng avm = new AppVersionMng();
		try {
			String os = dbop.getHostOSByIP(ip);
			// 由程序选择数据库里最新的版本号
			String newestV = dbop.queryAppNewestVersion("tomcat", os);
			if (version.isEmpty()) {
				version = newestV;
			}
			// 判断该软件有没有在该虚拟机上安装过
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"tomcat");
			if (dbSoftVersion.isEmpty()) {// 表示该软件没有在该虚拟机上安装过，却要更新，提示错误码0x0401000
				entity.put("code", "0x0401000");
			} else if (dbSoftVersion.equals(newestV)) {// 表示该软件在该虚拟机上安装了最新的版本，无需更新
				entity.put("code", "0x0401003");
			} else if (dbSoftVersion.equals(version)) {// 表示用户输入的版本和已经安装的版本相同，无需更新,提示错误码
				entity.put("code", "0x0401002");
			} else {
				String tempNewVersion = avm.whichIsNew(dbSoftVersion, version);
				if (tempNewVersion.equals(dbSoftVersion)) {// 表示现在已将安装的版本较新，无需更新,提示错误码
					entity.put("code", "0x0401001");
				} else {// 用户输入的版本比较新，可以正常更新
					scIPAddr = dbop.getRCAddrByIP("hp", ip, "tomcat", version);
					// 将消息发送给Agent成功以后，返回一个代表操作id的代号eid，同时这也是数据库中存储的代表该操作的eid
					int eid = a.sendUpdateTomcatLinuxMsg(ip, scIPAddr,
							unistallPath, updatePath, jdkPath);
					// 构造要返回给用户的json对象
					entity.put("eid", eid);
					entity.put("status", "虚拟机已接收更新tomcat请求可查询状态");//
				}
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public Response updateApache(@QueryParam("ip") String ip,
			@QueryParam("updatePath") String updatePath,
			@QueryParam("emailAddress") String emailAddress,
			@QueryParam("version") String version) {

		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();
		JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		AppVersionMng avm = new AppVersionMng();
		try {
			String os = dbop.getHostOSByIP(ip);
			// 由程序选择数据库里最新的版本号
			String newestV = dbop.queryAppNewestVersion("apache", os);
			if (version.isEmpty()) {
				version = newestV;
			}
			// 判断该软件有没有在该虚拟机上安装过
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"apache");
			if (dbSoftVersion.isEmpty()) {// 表示该软件没有在该虚拟机上安装过，却要更新，提示错误码0x0401000
				entity.put("code", "0x0401000");
			} else if (dbSoftVersion.equals(newestV)) {// 表示该软件在该虚拟机上安装了最新的版本，无需更新
				entity.put("code", "0x0401003");
			} else if (dbSoftVersion.equals(version)) {// 表示用户输入的版本和已经安装的版本相同，无需更新,提示错误码
				entity.put("code", "0x0401002");
			} else {
				String tempNewVersion = avm.whichIsNew(dbSoftVersion, version);
				if (tempNewVersion.equals(dbSoftVersion)) {// 表示现在已将安装的版本较新，无需更新,提示错误码
					entity.put("code", "0x0401001");
				} else {// 用户输入的版本比较新，可以正常更新
					scIPAddr = dbop.getRCAddrByIP("hp", ip, "apache", version);
					// 将消息发送给Agent成功以后，返回一个代表操作id的代号eid，同时这也是数据库中存储的代表该操作的eid
					int eid = a.sendUpdateApacheMsg(ip, scIPAddr, updatePath,
							emailAddress);
					// 构造要返回给用户的json对象
					entity.put("eid", eid);
					entity.put("status", "虚拟机已接收更新apache请求可查询状态");//
				}
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/Apache_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateApacheOnLinux(@QueryParam("ip") String ip,
			@QueryParam("unistallPath") String unistallPath,
			@QueryParam("updatePath") String updatePath,
			@QueryParam("emailAddress") String emailAddress,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();
		JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		AppVersionMng avm = new AppVersionMng();
		try {
			String os = dbop.getHostOSByIP(ip);
			// 由程序选择数据库里最新的版本号
			String newestV = dbop.queryAppNewestVersion("apache", os);
			if (version.isEmpty()) {
				version = newestV;
			}
			// 判断该软件有没有在该虚拟机上安装过
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"apache");
			if (dbSoftVersion.isEmpty()) {// 表示该软件没有在该虚拟机上安装过，却要更新，提示错误码0x0401000
				entity.put("code", "0x0401000");
			} else if (dbSoftVersion.equals(newestV)) {// 表示该软件在该虚拟机上安装了最新的版本，无需更新
				entity.put("code", "0x0401003");
			} else if (dbSoftVersion.equals(version)) {// 表示用户输入的版本和已经安装的版本相同，无需更新,提示错误码
				entity.put("code", "0x0401002");
			} else {
				String tempNewVersion = avm.whichIsNew(dbSoftVersion, version);
				if (tempNewVersion.equals(dbSoftVersion)) {// 表示现在已将安装的版本较新，无需更新,提示错误码
					entity.put("code", "0x0401001");
				} else {// 用户输入的版本比较新，可以正常更新
					scIPAddr = dbop.getRCAddrByIP("hp", ip, "apache", version);
					// 将消息发送给Agent成功以后，返回一个代表操作id的代号eid，同时这也是数据库中存储的代表该操作的eid
					int eid = a.sendUpdateApacheLinuxMsg(ip, scIPAddr,
							unistallPath, updatePath, emailAddress);
					// 构造要返回给用户的json对象
					entity.put("eid", eid);
					entity.put("status", "虚拟机已接收更新apache请求可查询状态");//
				}
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/Nginx")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateNginx(@QueryParam("ip") String ip,
			@QueryParam("updatePath") String updatePath,
			@QueryParam("unistallPath") String unistallPath,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();
		JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		AppVersionMng avm = new AppVersionMng();
		try {
			String os = dbop.getHostOSByIP(ip);
			// 由程序选择数据库里最新的版本号
			String newestV = dbop.queryAppNewestVersion("nginx", os);
			if (version.isEmpty()) {
				version = newestV;
			}
			// 判断该软件有没有在该虚拟机上安装过
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"nginx");
			if (dbSoftVersion.isEmpty()) {// 表示该软件没有在该虚拟机上安装过，却要更新，提示错误码0x0401000
				entity.put("code", "0x0401000");
			} else if (dbSoftVersion.equals(newestV)) {// 表示该软件在该虚拟机上安装了最新的版本，无需更新
				entity.put("code", "0x0401003");
			} else if (dbSoftVersion.equals(version)) {// 表示用户输入的版本和已经安装的版本相同，无需更新,提示错误码
				entity.put("code", "0x0401002");
			} else {
				String tempNewVersion = avm.whichIsNew(dbSoftVersion, version);
				if (tempNewVersion.equals(dbSoftVersion)) {// 表示现在已将安装的版本较新，无需更新,提示错误码
					entity.put("code", "0x0401001");
				} else {// 用户输入的版本比较新，可以正常更新
					scIPAddr = dbop.getRCAddrByIP("hp", ip, "nginx", version);
					// 将消息发送给Agent成功以后，返回一个代表操作id的代号eid，同时这也是数据库中存储的代表该操作的eid
					int eid = a.sendUpdateNginxMsg(ip, scIPAddr, updatePath,
							unistallPath);
					// 构造要返回给用户的json对象
					entity.put("eid", eid);
					entity.put("status", "虚拟机已接收更新nginx请求可查询状态");//
				}
			}
			dbop.close();
			res = Response.ok(entity).build();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/Nginx_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateNginxLinux(@QueryParam("ip") String ip,
			@QueryParam("updatePath") String updatePath,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();
		JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		AppVersionMng avm = new AppVersionMng();
		try {
			String os = dbop.getHostOSByIP(ip);
			// 由程序选择数据库里最新的版本号
			String newestV = dbop.queryAppNewestVersion("nginx", os);
			if (version.isEmpty()) {
				version = newestV;
			}
			// 判断该软件有没有在该虚拟机上安装过
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"nginx");
			if (dbSoftVersion.isEmpty()) {// 表示该软件没有在该虚拟机上安装过，却要更新，提示错误码0x0401000
				entity.put("code", "0x0401000");
			} else if (dbSoftVersion.equals(newestV)) {// 表示该软件在该虚拟机上安装了最新的版本，无需更新
				entity.put("code", "0x0401003");
			} else if (dbSoftVersion.equals(version)) {// 表示用户输入的版本和已经安装的版本相同，无需更新,提示错误码
				entity.put("code", "0x0401002");
			} else {
				String tempNewVersion = avm.whichIsNew(dbSoftVersion, version);
				if (tempNewVersion.equals(dbSoftVersion)) {// 表示现在已将安装的版本较新，无需更新,提示错误码
					entity.put("code", "0x0401001");
				} else {// 用户输入的版本比较新，可以正常更新
					scIPAddr = dbop.getRCAddrByIP("hp", ip, "nginx", version);
					// 将消息发送给Agent成功以后，返回一个代表操作id的代号eid，同时这也是数据库中存储的代表该操作的eid
					int eid = a.sendUpdateNginxLinuxMsg(ip, scIPAddr,
							updatePath);
					// 构造要返回给用户的json对象
					entity.put("eid", eid);
					entity.put("status", "虚拟机已接收更新nginx请求可查询状态");//
				}
			}
			dbop.close();
			res = Response.ok(entity).build();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/ZendGuardLoader")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateZendGuardLoader(@QueryParam("ip") String ip,
			@QueryParam("phpPath") String phpPath,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();
		JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		AppVersionMng avm = new AppVersionMng();
		try {
			String os = dbop.getHostOSByIP(ip);
			// 由程序选择数据库里最新的版本号
			String newestV = dbop.queryAppNewestVersion("zendguardloader", os);
			if (version.isEmpty()) {
				version = newestV;
			}
			// 判断该软件有没有在该虚拟机上安装过
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"zendguardloader");
			if (dbSoftVersion.isEmpty()) {// 表示该软件没有在该虚拟机上安装过，却要更新，提示错误码0x0401000
				entity.put("code", "0x0401000");
			} else if (dbSoftVersion.equals(newestV)) {// 表示该软件在该虚拟机上安装了最新的版本，无需更新
				entity.put("code", "0x0401003");
			} else if (dbSoftVersion.equals(version)) {// 表示用户输入的版本和已经安装的版本相同，无需更新,提示错误码
				entity.put("code", "0x0401002");
			} else {
				String tempNewVersion = avm.whichIsNew(dbSoftVersion, version);
				if (tempNewVersion.equals(dbSoftVersion)) {// 表示现在已将安装的版本较新，无需更新,提示错误码
					entity.put("code", "0x0401001");
				} else {// 用户输入的版本比较新，可以正常更新
					scIPAddr = dbop.getRCAddrByIP("hp", ip, "zendguardloader",
							version);
					// 将消息发送给Agent成功以后，返回一个代表操作id的代号eid，同时这也是数据库中存储的代表该操作的eid
					int eid = a.sendUpdateZendGuardLoaderMsg(ip, scIPAddr,
							phpPath);
					// 构造要返回给用户的json对象
					entity.put("eid", eid);
					entity.put("status", "虚拟机已接收更新zendGuardLoader请求可查询状态");//
				}
			}
			dbop.close();
			res = Response.ok(entity).build();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/Python")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePython(@QueryParam("ip") String ip,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();
		JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		AppVersionMng avm = new AppVersionMng();
		try {
			String os = dbop.getHostOSByIP(ip);
			// 由程序选择数据库里最新的版本号
			String newestV = dbop.queryAppNewestVersion("python", os);
			if (version.isEmpty()) {
				version = newestV;
			}
			// 判断该软件有没有在该虚拟机上安装过
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"python");
			if (dbSoftVersion.isEmpty()) {// 表示该软件没有在该虚拟机上安装过，却要更新，提示错误码0x0401000
				entity.put("code", "0x0401000");
			} else if (dbSoftVersion.equals(newestV)) {// 表示该软件在该虚拟机上安装了最新的版本，无需更新
				entity.put("code", "0x0401003");
			} else if (dbSoftVersion.equals(version)) {// 表示用户输入的版本和已经安装的版本相同，无需更新,提示错误码
				entity.put("code", "0x0401002");
			} else {
				String tempNewVersion = avm.whichIsNew(dbSoftVersion, version);
				if (tempNewVersion.equals(dbSoftVersion)) {// 表示现在已将安装的版本较新，无需更新,提示错误码
					entity.put("code", "0x0401001");
				} else {// 用户输入的版本比较新，可以正常更新
					scIPAddr = dbop.getRCAddrByIP("hp", ip, "python", version);
					// 将消息发送给Agent成功以后，返回一个代表操作id的代号eid，同时这也是数据库中存储的代表该操作的eid
					int eid = a.sendUpdatePythonMsg(ip, scIPAddr);
					// 构造要返回给用户的json对象
					entity.put("eid", eid);
					entity.put("status", "虚拟机已接收更新python请求可查询状态");//
				}
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/Python_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePythonOnLinux(@QueryParam("ip") String ip,
			@QueryParam("unistallPath") String unistallPath,
			@QueryParam("updatePath") String updatePath,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();
		JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		AppVersionMng avm = new AppVersionMng();
		try {
			String os = dbop.getHostOSByIP(ip);
			// 由程序选择数据库里最新的版本号
			String newestV = dbop.queryAppNewestVersion("python", os);
			if (version.isEmpty()) {
				version = newestV;
			}
			// 判断该软件有没有在该虚拟机上安装过
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"python");
			if (dbSoftVersion.isEmpty()) {// 表示该软件没有在该虚拟机上安装过，却要更新，提示错误码0x0401000
				entity.put("code", "0x0401000");
			} else if (dbSoftVersion.equals(newestV)) {// 表示该软件在该虚拟机上安装了最新的版本，无需更新
				entity.put("code", "0x0401003");
			} else if (dbSoftVersion.equals(version)) {// 表示用户输入的版本和已经安装的版本相同，无需更新,提示错误码
				entity.put("code", "0x0401002");
			} else {
				String tempNewVersion = avm.whichIsNew(dbSoftVersion, version);
				if (tempNewVersion.equals(dbSoftVersion)) {// 表示现在已将安装的版本较新，无需更新,提示错误码
					entity.put("code", "0x0401001");
				} else {// 用户输入的版本比较新，可以正常更新
					scIPAddr = dbop.getRCAddrByIP("hp", ip, "python", version);
					// 将消息发送给Agent成功以后，返回一个代表操作id的代号eid，同时这也是数据库中存储的代表该操作的eid
					int eid = a.sendUpdatePythonMsgOnLinux(ip, scIPAddr,
							unistallPath, updatePath);
					// 构造要返回给用户的json对象
					entity.put("eid", eid);
					entity.put("status", "虚拟机已接收更新python请求可查询状态");//
				}
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/Memcached")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMemcached(@QueryParam("ip") String ip,
			@QueryParam("unistallName") String unistallName,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();
		JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		AppVersionMng avm = new AppVersionMng();
		try {
			String os = dbop.getHostOSByIP(ip);
			// 由程序选择数据库里最新的版本号
			String newestV = dbop.queryAppNewestVersion("memcached", os);
			if (version.isEmpty()) {
				version = newestV;
			}
			// 判断该软件有没有在该虚拟机上安装过
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"memcached");
			if (dbSoftVersion.isEmpty()) {// 表示该软件没有在该虚拟机上安装过，却要更新，提示错误码0x0401000
				entity.put("code", "0x0401000");
			} else if (dbSoftVersion.equals(newestV)) {// 表示该软件在该虚拟机上安装了最新的版本，无需更新
				entity.put("code", "0x0401003");
			} else if (dbSoftVersion.equals(version)) {// 表示用户输入的版本和已经安装的版本相同，无需更新,提示错误码
				entity.put("code", "0x0401002");
			} else {
				String tempNewVersion = avm.whichIsNew(dbSoftVersion, version);
				if (tempNewVersion.equals(dbSoftVersion)) {// 表示现在已将安装的版本较新，无需更新,提示错误码
					entity.put("code", "0x0401001");
				} else {// 用户输入的版本比较新，可以正常更新
					scIPAddr = dbop.getRCAddrByIP("hp", ip, "memcached",
							version);
					int eid = a.sendUpdateMemcachedMsg(ip, scIPAddr,
							unistallName);
					entity.put("eid", eid);
					entity.put("status", "虚拟机已接收更新memcached请求可查询状态");
				}
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/Memcached_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMemcached(@QueryParam("ip") String ip,
			@QueryParam("uninstallPath") String uninstallPath,
			@QueryParam("path") String path,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();
		JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		AppVersionMng avm = new AppVersionMng();
		try {
			String os = dbop.getHostOSByIP(ip);
			// 由程序选择数据库里最新的版本号
			String newestV = dbop.queryAppNewestVersion("memcached", os);
			if (version.isEmpty()) {
				version = newestV;
			}
			// 判断该软件有没有在该虚拟机上安装过
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"memcached");
			if (dbSoftVersion.isEmpty()) {// 表示该软件没有在该虚拟机上安装过，却要更新，提示错误码0x0401000
				entity.put("code", "0x0401000");
			} else if (dbSoftVersion.equals(newestV)) {// 表示该软件在该虚拟机上安装了最新的版本，无需更新
				entity.put("code", "0x0401003");
			} else if (dbSoftVersion.equals(version)) {// 表示用户输入的版本和已经安装的版本相同，无需更新,提示错误码
				entity.put("code", "0x0401002");
			} else {
				String tempNewVersion = avm.whichIsNew(dbSoftVersion, version);
				if (tempNewVersion.equals(dbSoftVersion)) {// 表示现在已将安装的版本较新，无需更新,提示错误码
					entity.put("code", "0x0401001");
				} else {// 用户输入的版本比较新，可以正常更新
					scIPAddr = dbop.getRCAddrByIP("hp", ip, "memcached",
							version);
					int eid = a.sendUpdateMemcachedLinuxMsg(ip, scIPAddr,
							uninstallPath, path);
					entity.put("eid", eid);
					entity.put("status", "虚拟机已接收更新memcached请求可查询状态");
				}
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/IISRewrite")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateIISRewrite(@QueryParam("ip") String ip,
			@QueryParam("updatePath") String updatePath,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();
		JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		AppVersionMng avm = new AppVersionMng();
		try {
			String os = dbop.getHostOSByIP(ip);
			// 由程序选择数据库里最新的版本号
			String newestV = dbop.queryAppNewestVersion("iisrewrite", os);
			if (version.isEmpty()) {
				version = newestV;
			}
			// 判断该软件有没有在该虚拟机上安装过
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"iisrewrite");
			if (dbSoftVersion.isEmpty()) {// 表示该软件没有在该虚拟机上安装过，却要更新，提示错误码0x0401000
				entity.put("code", "0x0401000");
			} else if (dbSoftVersion.equals(newestV)) {// 表示该软件在该虚拟机上安装了最新的版本，无需更新
				entity.put("code", "0x0401003");
			} else if (dbSoftVersion.equals(version)) {// 表示用户输入的版本和已经安装的版本相同，无需更新,提示错误码
				entity.put("code", "0x0401002");
			} else {
				String tempNewVersion = avm.whichIsNew(dbSoftVersion, version);
				if (tempNewVersion.equals(dbSoftVersion)) {// 表示现在已将安装的版本较新，无需更新,提示错误码
					entity.put("code", "0x0401001");
				} else {// 用户输入的版本比较新，可以正常更新
					scIPAddr = dbop.getRCAddrByIP("hp", ip, "iisrewrite",
							version);
					// 将消息发送给Agent成功以后，返回一个代表操作id的代号eid，同时这也是数据库中存储的代表该操作的eid
					int eid = a.sendUpdateIISRewriteMsg(ip, scIPAddr,
							updatePath);
					// 构造要返回给用户的json对象
					entity.put("eid", eid);
					entity.put("status", "虚拟机已接收更新iisrewrite请求可查询状态");//
				}
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/FTP")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateFTP(@QueryParam("ip") String ip,
			@QueryParam("updatePath") String updatePath,
			@QueryParam("version") String version) {

		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();
		JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		AppVersionMng avm = new AppVersionMng();
		try {
			String os = dbop.getHostOSByIP(ip);
			// 由程序选择数据库里最新的版本号
			String newestV = dbop.queryAppNewestVersion("ftp", os);
			if (version.isEmpty()) {
				version = newestV;
			}
			// 判断该软件有没有在该虚拟机上安装过
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"ftp");
			if (dbSoftVersion.isEmpty()) {// 表示该软件没有在该虚拟机上安装过，却要更新，提示错误码0x0401000
				entity.put("code", "0x0401000");
			} else if (dbSoftVersion.equals(newestV)) {// 表示该软件在该虚拟机上安装了最新的版本，无需更新
				entity.put("code", "0x0401003");
			} else if (dbSoftVersion.equals(version)) {// 表示用户输入的版本和已经安装的版本相同，无需更新,提示错误码
				entity.put("code", "0x0401002");
			} else {
				String tempNewVersion = avm.whichIsNew(dbSoftVersion, version);
				if (tempNewVersion.equals(dbSoftVersion)) {// 表示现在已将安装的版本较新，无需更新,提示错误码
					entity.put("code", "0x0401001");
				} else {// 用户输入的版本比较新，可以正常更新
					scIPAddr = dbop.getRCAddrByIP("hp", ip, "ftp", version);
					// 将消息发送给Agent成功以后，返回一个代表操作id的代号eid，同时这也是数据库中存储的代表该操作的eid
					int eid = a.sendUpdateFTPMsg(ip, scIPAddr, updatePath);
					// 构造要返回给用户的json对象
					entity.put("eid", eid);
					entity.put("status", "虚拟机已接收更新ftp请求可查询状态");//
				}
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/FTP_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateFTPOnLinux(@QueryParam("ip") String ip,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();
		JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		AppVersionMng avm = new AppVersionMng();
		try {
			String os = dbop.getHostOSByIP(ip);
			// 由程序选择数据库里最新的版本号
			String newestV = dbop.queryAppNewestVersion("ftp", os);
			if (version.isEmpty()) {
				version = newestV;
			}
			// 判断该软件有没有在该虚拟机上安装过
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"ftp");
			if (dbSoftVersion.isEmpty()) {// 表示该软件没有在该虚拟机上安装过，却要更新，提示错误码0x0401000
				entity.put("code", "0x0401000");
			} else if (dbSoftVersion.equals(newestV)) {// 表示该软件在该虚拟机上安装了最新的版本，无需更新
				entity.put("code", "0x0401003");
			} else if (dbSoftVersion.equals(version)) {// 表示用户输入的版本和已经安装的版本相同，无需更新,提示错误码
				entity.put("code", "0x0401002");
			} else {
				String tempNewVersion = avm.whichIsNew(dbSoftVersion, version);
				if (tempNewVersion.equals(dbSoftVersion)) {// 表示现在已将安装的版本较新，无需更新,提示错误码
					entity.put("code", "0x0401001");
				} else {// 用户输入的版本比较新，可以正常更新
					scIPAddr = dbop.getRCAddrByIP("hp", ip, "ftp", version);
					// 将消息发送给Agent成功以后，返回一个代表操作id的代号eid，同时这也是数据库中存储的代表该操作的eid
					int eid = a.sendUpdateFTPMsgOnLinux(ip, scIPAddr);
					// 构造要返回给用户的json对象
					entity.put("eid", eid);
					entity.put("status", "虚拟机已接收更新ftp请求可查询状态");//
				}
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/SQLServer2008R2")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateSQLServer2008R2(@QueryParam("ip") String ip,
			@QueryParam("updatePath") String updatePath,
			@QueryParam("rootPswd") String rootPswd,
			@QueryParam("hostName") String hostName,
			@QueryParam("userName") String userName,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();
		JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		AppVersionMng avm = new AppVersionMng();
		try {
			String os = dbop.getHostOSByIP(ip);
			// 由程序选择数据库里最新的版本号
			String newestV = dbop.queryAppNewestVersion("sqlserver2008r2", os);
			if (version.isEmpty()) {
				version = newestV;
			}
			// 判断该软件有没有在该虚拟机上安装过
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"sqlserver2008r2");
			if (dbSoftVersion.isEmpty()) {// 表示该软件没有在该虚拟机上安装过，却要更新，提示错误码0x0401000
				entity.put("code", "0x0401000");
			} else if (dbSoftVersion.equals(newestV)) {// 表示该软件在该虚拟机上安装了最新的版本，无需更新
				entity.put("code", "0x0401003");
			} else if (dbSoftVersion.equals(version)) {// 表示用户输入的版本和已经安装的版本相同，无需更新,提示错误码
				entity.put("code", "0x0401002");
			} else {
				String tempNewVersion = avm.whichIsNew(dbSoftVersion, version);
				if (tempNewVersion.equals(dbSoftVersion)) {// 表示现在已将安装的版本较新，无需更新,提示错误码
					entity.put("code", "0x0401001");
				} else {// 用户输入的版本比较新，可以正常更新
					scIPAddr = dbop.getRCAddrByIP("hp", ip, "sqlserver2008r2",
							version);
					// 将消息发送给Agent成功以后，返回一个代表操作id的代号eid，同时这也是数据库中存储的代表该操作的eid
					int eid = a.sendUpdateSQLServer2008R2Msg(ip, scIPAddr,
							updatePath, rootPswd, hostName, userName);
					// 构造要返回给用户的json对象
					entity.put("eid", eid);
					entity.put("status", "虚拟机已接收更新SQLServer2008R2请求可查询状态");//
				}
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
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
		ApplicationUpdateBase a = new ApplicationUpdateBase();
		JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		AppVersionMng avm = new AppVersionMng();
		try {
			String os = dbop.getHostOSByIP(ip);
			// 由程序选择数据库里最新的版本号
			String newestV = dbop.queryAppNewestVersion("sqlserver2000", os);
			if (version.isEmpty()) {
				version = newestV;
			}
			// 判断该软件有没有在该虚拟机上安装过
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"sqlserver2000");
			if (dbSoftVersion.isEmpty()) {// 表示该软件没有在该虚拟机上安装过，却要更新，提示错误码0x0401000
				entity.put("code", "0x0401000");
			} else if (dbSoftVersion.equals(newestV)) {// 表示该软件在该虚拟机上安装了最新的版本，无需更新
				entity.put("code", "0x0401003");
			} else if (dbSoftVersion.equals(version)) {// 表示用户输入的版本和已经安装的版本相同，无需更新,提示错误码
				entity.put("code", "0x0401002");
			} else {
				String tempNewVersion = avm.whichIsNew(dbSoftVersion, version);
				if (tempNewVersion.equals(dbSoftVersion)) {// 表示现在已将安装的版本较新，无需更新,提示错误码
					entity.put("code", "0x0401001");
				} else {// 用户输入的版本比较新，可以正常更新
					scIPAddr = dbop.getRCAddrByIP("hp", ip, "sqlserver2000",
							version);
					// 将消息发送给Agent成功以后，返回一个代表操作id的代号eid，同时这也是数据库中存储的代表该操作的eid
					int eid = a.sendUpdateSQLServer2000Msg(ip, scIPAddr,
							updatePath);
					// 构造要返回给用户的json对象
					entity.put("eid", eid);
					entity.put("status", "虚拟机已接收更新SQLServer2000请求可查询状态");//
				}
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/Oracle11g")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateOracle11g(@QueryParam("ip") String ip,
			@QueryParam("hostname") String hostname,
			@QueryParam("inventorypath") String inventorypath,
			@QueryParam("oraclebase") String oraclebase,
			@QueryParam("oraclehome") String oraclehome,
			@QueryParam("rootPswd") String rootPswd,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();
		JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		AppVersionMng avm = new AppVersionMng();
		try {
			String os = dbop.getHostOSByIP(ip);
			// 由程序选择数据库里最新的版本号
			String newestV = dbop.queryAppNewestVersion("oracle11g", os);
			if (version.isEmpty()) {
				version = newestV;
			}
			// 判断该软件有没有在该虚拟机上安装过
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"oracle11g");
			if (dbSoftVersion.isEmpty()) {// 表示该软件没有在该虚拟机上安装过，却要更新，提示错误码0x0401000
				entity.put("code", "0x0401000");
			} else if (dbSoftVersion.equals(newestV)) {// 表示该软件在该虚拟机上安装了最新的版本，无需更新
				entity.put("code", "0x0401003");
			} else if (dbSoftVersion.equals(version)) {// 表示用户输入的版本和已经安装的版本相同，无需更新,提示错误码
				entity.put("code", "0x0401002");
			} else {
				String tempNewVersion = avm.whichIsNew(dbSoftVersion, version);
				if (tempNewVersion.equals(dbSoftVersion)) {// 表示现在已将安装的版本较新，无需更新,提示错误码
					entity.put("code", "0x0401001");
				} else {// 用户输入的版本比较新，可以正常更新
					scIPAddr = dbop.getRCAddrByIP("hp", ip, "oracle11g",
							version);
					// 将消息发送给Agent成功以后，返回一个代表操作id的代号eid，同时这也是数据库中存储的代表该操作的eid
					int eid = a.sendUpdateOracle11gMsg(ip, scIPAddr, hostname,
							inventorypath, oraclebase, oraclehome, rootPswd);
					// 构造要返回给用户的json对象
					entity.put("eid", eid);
					entity.put("status", "虚拟机已接收更新Oracle11g请求可查询状态");//
				}
			}
			dbop.close();
			res = Response.ok(entity).build();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@POST
	@Path("/360")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update360(@QueryParam("ip") String ip,
			@QueryParam("updatePath") String updatePath,
			@QueryParam("version") String version) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();
		JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		AppVersionMng avm = new AppVersionMng();
		try {
			String os = dbop.getHostOSByIP(ip);
			// 由程序选择数据库里最新的版本号
			String newestV = dbop.queryAppNewestVersion("360", os);
			if (version.isEmpty()) {
				version = newestV;
			}
			// 判断该软件有没有在该虚拟机上安装过
			String dbSoftVersion = dbop.queryHostappTableForSoftwareVersion(ip,
					"360");
			if (dbSoftVersion.isEmpty()) {// 表示该软件没有在该虚拟机上安装过，却要更新，提示错误码0x0401000
				entity.put("code", "0x0401000");
			} else if (dbSoftVersion.equals(newestV)) {// 表示该软件在该虚拟机上安装了最新的版本，无需更新
				entity.put("code", "0x0401003");
			} else if (dbSoftVersion.equals(version)) {// 表示用户输入的版本和已经安装的版本相同，无需更新,提示错误码
				entity.put("code", "0x0401002");
			} else {
				String tempNewVersion = avm.whichIsNew(dbSoftVersion, version);
				if (tempNewVersion.equals(dbSoftVersion)) {// 表示现在已将安装的版本较新，无需更新,提示错误码
					entity.put("code", "0x0401001");
				} else {// 用户输入的版本比较新，可以正常更新
					scIPAddr = dbop.getRCAddrByIP("hp", ip, "360", version);
					// 将消息发送给Agent成功以后，返回一个代表操作id的代号eid，同时这也是数据库中存储的代表该操作的eid
					int eid = a.sendUpdate360Msg(ip, scIPAddr, updatePath);
					// 构造要返回给用户的json对象
					entity.put("eid", eid);
					entity.put("status", "虚拟机已接收更新360请求可查询状态");//
				}
			}
			dbop.close();
			res = Response.ok(entity).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

}
