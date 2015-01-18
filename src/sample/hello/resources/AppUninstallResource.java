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
	public Response uninstallMySql(
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath,
			@QueryParam("rootPswd") String rootPswd
			) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();
		JSONObject entity = new JSONObject();//表示要给用户返回的json对象
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip, "mysql");
			//将消息发送给Agent成功以后，返回一个代表操作id的代号eid，同时这也是数据库中存储的代表该操作的eid
			int eid = a.sendUninstallMySqlMsg( ip, scIPAddr, installPath,rootPswd);
			//构造要返回给用户的json对象
			entity.put("eid", eid);
			entity.put("status", "uninstall mysql has already been executing ");//
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
	public Response uninstallMySqlOnLinux(
			@QueryParam("ip") String ip,
			@QueryParam("rootPswd") String rootPswd
			) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();
		JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip, "mysql");
			int eid = a.sendUninstallMySqlOnLinuxMsg( ip, scIPAddr, rootPswd);
			entity.put("eid", eid);
			entity.put("status", "uninstall mysql has already been executing ");
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
	public Response uninstallTomcat(
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath,
			@QueryParam("jdkPath") String jdkPath
			) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();
		JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"tomcat");
			int eid = a.sendUninstallTomcatMsg( ip, scIPAddr, installPath,jdkPath);
			entity.put("eid", eid);
			entity.put("status", "uninstall tomcat has already been executing ");
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
//	public Response uninstallTomcatOnLinux( 
//			@QueryParam("ip") String ip,
//			@QueryParam("installPath") String installPath,
//			@QueryParam("jdkName") String jdkName,
//			@QueryParam("jdkPath") String jdkPath
//			) {
//		Response res = null;
//		ApplicationUninstallBase a = new ApplicationUninstallBase();
//		DBOperation dbop = new DBOperation();
//		String[] scIPAddr= new String[2];
//		try {
//			scIPAddr = dbop.getRCAddrByIP( "hp",ip,"tomcat");
//			int eid = a.sendUninstallTomcatOnLinuxMsg( ip, scIPAddr, installPath,jdkName,jdkPath)) {
//				entity.put("eid", value);entity.put("status", value);res = Response.ok("uninstall tomcat request is already executing").build();
//			} else {
//				entity.put("eid", value);entity.put("status", value);res = Response.ok("uninstall tomcat request failed").build();
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
	public Response uninstallJdk(
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"jdk");
			int eid = a.sendUninstallJdkMsg( ip, scIPAddr, installPath);
			entity.put("eid", eid);
			entity.put("status", "uninstall jdk has already been executing ");
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
	public Response uninstallApache(
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"apache");
			int eid = a.sendUninstallApacheMsg( ip, scIPAddr, installPath);
			entity.put("eid", eid);
			entity.put("status", "uninstall Apache has already been executing ");
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
	public Response uninstallNginx(
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP( "hp",ip,"nginx");
			int eid = a.sendUninstallNginxMsg( ip, scIPAddr, installPath);
			entity.put("eid", eid);
			entity.put("status", "install Nginx has already been executing ");
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
	public Response installZendGuardLoader(
			@QueryParam("ip") String ip,
			@QueryParam("phpPath") String phpPath
			) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"zendguardloader");
			int eid = a.sendUninstallZendGuardLoaderMsg( ip, scIPAddr,phpPath);
			entity.put("eid", eid);
			entity.put("status", "uninstall ZendGuardLoader has already been executing ");
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
	public Response uninstallZendGuardLoader_Linux(
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath,
			@QueryParam("phpPath") String phpPath
			) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"zendguardloader");
			int eid = a.sendUninstallZendGuardLoaderMsgOnLinux( ip, scIPAddr,installPath,phpPath);
			entity.put("eid", eid);
			entity.put("status", "uninstall ZendGuardLoader has already been executing ");
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
	public Response uninstallPython(
			@QueryParam("ip") String ip
			) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"python");
			int eid = a.sendUninstallPythonMsg( ip, scIPAddr);
			entity.put("eid", eid);
			entity.put("status", "uninstall python has already been executing ");
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
	public Response uninstallPython(
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"python");
			int eid = a.sendUninstallPythonMsgOnLinux( ip, scIPAddr,installPath);
			entity.put("eid", eid);
			entity.put("status", "uninstall python has already been executing ");
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
	public Response uninstallMemcached(
			@QueryParam("ip") String ip
			) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"memcached");
			int eid = a.sendUninstallMemcachedMsg( ip, scIPAddr);
			entity.put("eid", eid);
			entity.put("status", "uninstall memcached has already been executing ");
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
	public Response uninstallMemcached(
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"memcached");
			int eid = a.sendUninstallMemcachedMsgOnLinux( ip, scIPAddr,installPath);
			entity.put("eid", eid);
			entity.put("status", "uninstall memcached has already been executing ");
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
	public Response uninstallIISRewrite(
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"iisrewrite");
			int eid = a.sendUninstallIISRewriteMsg( ip, scIPAddr, installPath);
			entity.put("eid", eid);
			entity.put("status", "uninstall IISRewrite has already been executing ");
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
	@Path("/ASP")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uninstallASP(
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"asp");
			int eid = a.sendUninstallASPMsg( ip, scIPAddr, installPath);
			entity.put("eid", eid);
			entity.put("status", "uninstall ASP has already been executing ");
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
	public Response uninstallFTP(
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"ftp");
			int eid = a.sendUninstallFTPMsg( ip, scIPAddr, installPath);
			entity.put("eid", eid);
			entity.put("status", "uninstall FTP has already been executing ");
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
	public Response uninstallFTP(
			@QueryParam("ip") String ip
			) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"ftp");
			int eid = a.sendUninstallFTPMsgOnLinux( ip, scIPAddr);
			entity.put("eid", eid);
			entity.put("status", "uninstall FTP has already been executing ");
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
	@Path("/ASPNET")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uninstallASPNET(
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"aspnet");
			int eid = a.sendUninstallASPNETMsg( ip, scIPAddr, installPath);
			entity.put("eid", eid);
			entity.put("status", "uninstall ASPNET has already been executing ");
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
	public Response uninstallSQLServer2008R2(
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath,
			@QueryParam("rootPswd") String rootPswd,
			@QueryParam("hostName") String hostName,
			@QueryParam("userName") String userName
			) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"sqlserver2008r2");
			int eid = a.sendUninstallSQLServer2008R2Msg( ip, scIPAddr, installPath,rootPswd,hostName,userName);
			entity.put("eid", eid);
			entity.put("status", "uninstall SQLServer2008R2 has already been executing ");
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
	public Response uninstallSQLServer2000(
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"sqlserver2000");
			int eid = a.sendUninstallSQLServer2000Msg( ip, scIPAddr, installPath);
			entity.put("eid", eid);
			entity.put("status", "uninstall SQLServer2000 has already been executing ");
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
	public Response uninstallOracle10g(
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"oracle10g");
			int eid = a.sendUninstallOracle10gMsg( ip, scIPAddr, installPath);
			entity.put("eid", eid);
			entity.put("status", "uninstall Oracle10g has already been executing ");
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
	public Response uninstallOracle11g(
			@QueryParam("ip") String ip,
			@QueryParam("hostname") String hostname,
			@QueryParam("inventorypath") String inventorypath,
			@QueryParam("oraclebase") String oraclebase,
			@QueryParam("oraclehome") String oraclehome,
			@QueryParam("rootPswd") String rootPswd
			) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"oracle11g");
			int eid = a.sendUninstallOracle11gMsg( ip, scIPAddr, hostname,inventorypath,oraclebase,oraclehome,rootPswd);
			entity.put("eid", eid);
			entity.put("status", "uninstall Oracle11g has already been executing ");
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
	public Response uninstall360(
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationUninstallBase a = new ApplicationUninstallBase();JSONObject entity = new JSONObject();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP("hp", ip,"360");
			int eid = a.sendUninstall360Msg( ip, scIPAddr, installPath);
			entity.put("eid", eid);
			entity.put("status", "uninstall 360 has already been executing ");
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
