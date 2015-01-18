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
import sample.hello.bean.ApplicationUpdateBase;


@Path("/AppUpdate")
public class AppUpdateResource {

	@POST
	@Path("/MySql")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMySql(
			@QueryParam("ip") String ip,
			@QueryParam("updatePath") String updatePath,
			@QueryParam("rootPswd") String rootPswd
			) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();
		JSONObject entity = new JSONObject();//表示要给用户返回的json对象
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip, "mysql");
			//将消息发送给Agent成功以后，返回一个代表操作id的代号eid，同时这也是数据库中存储的代表该操作的eid
			int eid = a.sendUpdateMySqlMsg( ip, scIPAddr, updatePath,rootPswd);
			//构造要返回给用户的json对象
			entity.put("eid", eid);
			entity.put("status", "update mysql has already been executing ");//
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
	public Response updateMySqlOnLinux(
			@QueryParam("ip") String ip,
			@QueryParam("rootPswd") String rootPswd
			) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();
		JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip, "mysql");
			int eid = a.sendUpdateMySqlOnLinuxMsg( ip, scIPAddr, rootPswd);
			entity.put("eid", eid);
			entity.put("status", "update mysql has already been executing ");
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
	public Response updateTomcat(
			@QueryParam("ip") String ip,
			@QueryParam("updatePath") String updatePath,
			@QueryParam("jdkPath") String jdkPath
			) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();
		JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"tomcat");
			int eid = a.sendUpdateTomcatMsg( ip, scIPAddr, updatePath,jdkPath);
			entity.put("eid", eid);
			entity.put("status", "update tomcat has already been executing ");
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
//	@POST
//	@Path("/Tomcat_Linux")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response updateTomcatOnLinux( 
//			@QueryParam("ip") String ip,
//			@QueryParam("updatePath") String updatePath,
//			@QueryParam("jdkName") String jdkName,
//			@QueryParam("jdkPath") String jdkPath
//			) {
//		Response res = null;
//		ApplicationUpdateBase a = new ApplicationUpdateBase();
//		DBOperation dbop = new DBOperation();
//		String[] scIPAddr= new String[2];
//		try {
//			scIPAddr = dbop.getRCAddrByIP("hp", ip,"tomcat");
//			int eid = a.sendUpdateTomcatOnLinuxMsg( ip, scIPAddr, updatePath,jdkName,jdkPath)) {
//				entity.put("eid", value);entity.put("status", value);res = Response.ok("update tomcat request is already executing").build();
//			} else {
//				entity.put("eid", value);entity.put("status", value);res = Response.ok("update tomcat request failed").build();
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return res;
//	}
	@POST
	@Path("/Jdk")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateJdk(
			@QueryParam("ip") String ip,
			@QueryParam("updatePath") String updatePath
			) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"jdk");
			int eid = a.sendUpdateJdkMsg( ip, scIPAddr, updatePath);
			entity.put("eid", eid);
			entity.put("status", "update jdk has already been executing ");
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
	public Response updateApache(
			@QueryParam("ip") String ip,
			@QueryParam("updatePath") String updatePath
			) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"apache");
			int eid = a.sendUpdateApacheMsg( ip, scIPAddr, updatePath);
			entity.put("eid", eid);
			entity.put("status", "update Apache has already been executing ");
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
	public Response updateNginx(
			@QueryParam("ip") String ip,
			@QueryParam("updatePath") String updatePath
			) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"nginx");
			int eid = a.sendUpdateNginxMsg( ip, scIPAddr, updatePath);
			entity.put("eid", eid);
			entity.put("status", "update Nginx has already been executing ");
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
	public Response updateZendGuardLoader(
			@QueryParam("ip") String ip,
			@QueryParam("phpPath") String phpPath
			) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"zendguardloader");
			int eid = a.sendUpdateZendGuardLoaderMsg( ip, scIPAddr,phpPath);
			entity.put("eid", eid);
			entity.put("status", "update ZendGuardLoader has already been executing ");
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
	@Path("/ZendGuardLoader_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateZendGuardLoader_Linux(
			@QueryParam("ip") String ip,
			@QueryParam("updatePath") String updatePath,
			@QueryParam("phpPath") String phpPath
			) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"zendguardloader");
			int eid = a.sendUpdateZendGuardLoaderMsgOnLinux( ip, scIPAddr,updatePath,phpPath);
			entity.put("eid", eid);
			entity.put("status", "update ZendGuardLoader has already been executing ");
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
	public Response updatePython(
			@QueryParam("ip") String ip
			) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"python");
			int eid = a.sendUpdatePythonMsg( ip, scIPAddr);
			entity.put("eid", eid);
			entity.put("status", "update python has already been executing ");
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
	public Response updatePython(
			@QueryParam("ip") String ip,
			@QueryParam("updatePath") String updatePath
			) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"python");
			int eid = a.sendUpdatePythonMsgOnLinux( ip, scIPAddr,updatePath);
			entity.put("eid", eid);
			entity.put("status", "update python has already been executing ");
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
	public Response updateMemcached(
			@QueryParam("ip") String ip
			) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"memcached");
			int eid = a.sendUpdateMemcachedMsg( ip, scIPAddr);
			entity.put("eid", eid);
			entity.put("status", "update memcached has already been executing ");
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
	public Response updateMemcached(
			@QueryParam("ip") String ip,
			@QueryParam("updatePath") String updatePath
			) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"memcached");
			int eid = a.sendUpdateMemcachedMsgOnLinux( ip, scIPAddr,updatePath);
			entity.put("eid", eid);
			entity.put("status", "update memcached has already been executing ");
			res = Response.ok(entity).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block0
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
	public Response updateIISRewrite(
			@QueryParam("ip") String ip,
			@QueryParam("updatePath") String updatePath
			) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"iisrewrite");
			int eid = a.sendUpdateIISRewriteMsg( ip, scIPAddr, updatePath);
			entity.put("eid", eid);
			entity.put("status", "update IISRewrite has already been executing ");
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
//	@POST
//	@Path("/ASP")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response updateASP(
//			@QueryParam("ip") String ip,
//			@QueryParam("updatePath") String updatePath
//			) {
//		Response res = null;
//		ApplicationUpdateBase a = new ApplicationUpdateBase();JSONObject entity = new JSONObject();
//		DBOperation dbop = new DBOperation();
//		String[] scIPAddr= new String[2];
//		try {
//			scIPAddr = dbop.getRCAddrByIP("hp", ip,"asp");
//			int eid = a.sendUpdateASPMsg( ip, scIPAddr, updatePath);
//			entity.put("eid", eid);
//			entity.put("status", "update ASP has already been executing ");
//			res = Response.ok(entity).build();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return res;
//	}
	@POST
	@Path("/FTP")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateFTP(
			@QueryParam("ip") String ip,
			@QueryParam("updatePath") String updatePath
			) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"ftp");
			int eid = a.sendUpdateFTPMsg( ip, scIPAddr, updatePath);
			entity.put("eid", eid);
			entity.put("status", "update FTP has already been executing ");
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
	public Response updateFTP(
			@QueryParam("ip") String ip
			) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"ftp");
			int eid = a.sendUpdateFTPMsgOnLinux( ip, scIPAddr);
			entity.put("eid", eid);
			entity.put("status", "update FTP has already been executing ");
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
//	@POST
//	@Path("/ASPNET")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response updateASPNET(
//			@QueryParam("ip") String ip,
//			@QueryParam("updatePath") String updatePath
//			) {
//		Response res = null;
//		ApplicationUpdateBase a = new ApplicationUpdateBase();JSONObject entity = new JSONObject();
//		DBOperation dbop = new DBOperation();
//		String[] scIPAddr= new String[2];
//		try {
//			scIPAddr = dbop.getRCAddrByIP("hp", ip,"aspnet");
//			int eid = a.sendUpdateASPNETMsg( ip, scIPAddr, updatePath);
//			entity.put("eid", eid);
//			entity.put("status", "update ASPNET has already been executing ");
//			res = Response.ok(entity).build();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return res;
//	}
	@POST
	@Path("/SQLServer2008R2")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateSQLServer2008R2(
			@QueryParam("ip") String ip,
			@QueryParam("updatePath") String updatePath,
			@QueryParam("rootPswd") String rootPswd,
			@QueryParam("hostName") String hostName,
			@QueryParam("userName") String userName
			) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"sqlserver2008r2");
			int eid = a.sendUpdateSQLServer2008R2Msg( ip, scIPAddr, updatePath,rootPswd,hostName,userName);
			entity.put("eid", eid);
			entity.put("status", "update SQLServer2008R2 has already been executing ");
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
	public Response updateSQLServer2000(
			@QueryParam("ip") String ip,
			@QueryParam("updatePath") String updatePath
			) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"sqlserver2000");
			int eid = a.sendUpdateSQLServer2000Msg( ip, scIPAddr, updatePath);
			entity.put("eid", eid);
			entity.put("status", "update SQLServer2000 has already been executing ");
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
	@Path("/Oracle10g")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateOracle10g(
			@QueryParam("ip") String ip,
			@QueryParam("updatePath") String updatePath
			) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"oracle10g");
			int eid = a.sendUpdateOracle10gMsg( ip, scIPAddr, updatePath);
			entity.put("eid", eid);
			entity.put("status", "update Oracle10g has already been executing ");
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
	public Response updateOracle11g(
			@QueryParam("ip") String ip,
			@QueryParam("hostname") String hostname,
			@QueryParam("inventorypath") String inventorypath,
			@QueryParam("oraclebase") String oraclebase,
			@QueryParam("oraclehome") String oraclehome,
			@QueryParam("rootPswd") String rootPswd
			) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"oracle11g");
			int eid = a.sendUpdateOracle11gMsg( ip, scIPAddr, hostname,inventorypath,oraclebase,oraclehome,rootPswd);
			entity.put("eid", eid);
			entity.put("status", "update Oracle11g has already been executing ");
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
	public Response update360(
			@QueryParam("ip") String ip,
			@QueryParam("updatePath") String updatePath
			) {
		Response res = null;
		ApplicationUpdateBase a = new ApplicationUpdateBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"360");
			int eid = a.sendUpdate360Msg( ip, scIPAddr, updatePath);
			entity.put("eid", eid);
			entity.put("status", "update 360 has already been executing ");
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
