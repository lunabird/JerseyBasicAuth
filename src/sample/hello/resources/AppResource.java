package sample.hello.resources;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import sample.DBOP.DBOperation;
import sample.hello.bean.ApplicationBase;

@Path("/AppInstallation")
public class AppResource {
	
	@POST
	@Path("/MySql")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installMySql(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath,
			@QueryParam("rootPswd") String rootPswd
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		
		try {
			scIPAddr = dbop.getRCAddrByIP(uid, ip,"mysql");
			if (a.sendSetupMySqlMsg(uid, ip, scIPAddr, installPath,rootPswd)) {
				res = Response.ok("install mysql request is already executing").build();
			} else {
				res = Response.ok("install mysql request failed").build();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	@POST
	@Path("/MySql_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installMySqlOnLinux(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("rootPswd") String rootPswd
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		
		try {
			scIPAddr = dbop.getRCAddrByIP(uid, ip,"mysql");
			if (a.sendSetupMySqlOnLinuxMsg(uid, ip, scIPAddr, rootPswd)) {
				res = Response.ok("install mysql request is already executing").build();
			} else {
				res = Response.ok("install mysql request failed").build();
			}
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
	public Response installTomcat(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath,
			@QueryParam("jdkPath") String jdkPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP(uid, ip,"tomcat");
			if (a.sendSetupTomcatMsg(uid, ip, scIPAddr, installPath,jdkPath)) {
				res = Response.ok("install tomcat request is already executing").build();
			} else {
				res = Response.ok("install tomcat request failed").build();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
//	@POST
//	@Path("/Tomcat_Linux")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response installTomcatOnLinux(@QueryParam("uid") String uid, 
//			@QueryParam("ip") String ip,
//			@QueryParam("installPath") String installPath,
//			@QueryParam("jdkName") String jdkName,
//			@QueryParam("jdkPath") String jdkPath
//			) {
//		Response res = null;
//		ApplicationBase a = new ApplicationBase();
//		DBOperation dbop = new DBOperation();
//		String[] scIPAddr= new String[2];
//		try {
//			scIPAddr = dbop.getRCAddrByIP(uid, ip,"tomcat");
//			if (a.sendSetupTomcatOnLinuxMsg(uid, ip, scIPAddr, installPath,jdkName,jdkPath)) {
//				res = Response.ok("install tomcat request is already executing").build();
//			} else {
//				res = Response.ok("install tomcat request failed").build();
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
	public Response installJdk(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP(uid, ip,"jdk");
			if (a.sendSetupJdkMsg(uid, ip, scIPAddr, installPath)) {
				res = Response.ok("install jdk request is already executing").build();
			} else {
				res = Response.ok("install jdk request failed").build();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	@POST
	@Path("/Apache")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installApache(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP(uid, ip,"apache");
			if (a.sendSetupApacheMsg(uid, ip, scIPAddr, installPath)) {
				res = Response.ok("install Apache request is already executing").build();
			} else {
				res = Response.ok("install Apache request failed").build();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	@POST
	@Path("/Nginx")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installNginx(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP(uid, ip,"nginx");
			if (a.sendSetupNginxMsg(uid, ip, scIPAddr, installPath)) {
				res = Response.ok("install Nginx request is already executing").build();
			} else {
				res = Response.ok("install Nginx request failed").build();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	@POST
	@Path("/ZendGuardLoader")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installZendGuardLoader(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("phpPath") String phpPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP(uid, ip,"zendguardloader");
			if (a.sendSetupZendGuardLoaderMsg(uid, ip, scIPAddr,phpPath)) {
				res = Response.ok("install ZendGuardLoader request is already executing").build();
			} else {
				res = Response.ok("install ZendGuardLoader request failed").build();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	@POST
	@Path("/ZendGuardLoader_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installZendGuardLoader_Linux(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath,
			@QueryParam("phpPath") String phpPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP(uid, ip,"zendguardloader");
			if (a.sendSetupZendGuardLoaderMsgOnLinux(uid, ip, scIPAddr,installPath,phpPath)) {
				res = Response.ok("install ZendGuardLoader request is already executing").build();
			} else {
				res = Response.ok("install ZendGuardLoader request failed").build();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	@POST
	@Path("/Python")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installPython(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP(uid, ip,"python");
			if (a.sendSetupPythonMsg(uid, ip, scIPAddr)) {
				res = Response.ok("install Python request is already executing").build();
			} else {
				res = Response.ok("install Python request failed").build();
			}
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
	public Response installPython(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP(uid, ip,"python");
			if (a.sendSetupPythonMsgOnLinux(uid, ip, scIPAddr,installPath)) {
				res = Response.ok("install Python request is already executing").build();
			} else {
				res = Response.ok("install Python request failed").build();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	@POST
	@Path("/Memcached")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installMemcached(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP(uid, ip,"memcached");
			if (a.sendSetupMemcachedMsg(uid, ip, scIPAddr)) {
				res = Response.ok("install Memcached request is already executing").build();
			} else {
				res = Response.ok("install Memcached request failed").build();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	@POST
	@Path("/Memcached_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installMemcached(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP(uid, ip,"memcached");
			if (a.sendSetupMemcachedMsgOnLinux(uid, ip, scIPAddr,installPath)) {
				res = Response.ok("install Memcached request is already executing").build();
			} else {
				res = Response.ok("install Memcached request failed").build();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	@POST
	@Path("/IISRewrite")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installIISRewrite(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP(uid, ip,"iisrewrite");
			if (a.sendSetupIISRewriteMsg(uid, ip, scIPAddr, installPath)) {
				res = Response.ok("install IISRewrite request is already executing").build();
			} else {
				res = Response.ok("install IISRewrite request failed").build();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	@POST
	@Path("/ASP")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installASP(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP(uid, ip,"asp");
			if (a.sendSetupASPMsg(uid, ip, scIPAddr, installPath)) {
				res = Response.ok("install ASP request is already executing").build();
			} else {
				res = Response.ok("install ASP request failed").build();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	@POST
	@Path("/FTP")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installFTP(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP(uid, ip,"ftp");
			if (a.sendSetupFTPMsg(uid, ip, scIPAddr, installPath)) {
				res = Response.ok("install FTP request is already executing").build();
			} else {
				res = Response.ok("install FTP request failed").build();
			}
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
	public Response installFTP(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP(uid, ip,"ftp");
			if (a.sendSetupFTPMsgOnLinux(uid, ip, scIPAddr)) {
				res = Response.ok("install FTP request is already executing").build();
			} else {
				res = Response.ok("install FTP request failed").build();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	@POST
	@Path("/ASPNET")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installASPNET(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP(uid, ip,"aspnet");
			if (a.sendSetupASPNETMsg(uid, ip, scIPAddr, installPath)) {
				res = Response.ok("install ASPNET request is already executing").build();
			} else {
				res = Response.ok("install ASPNET request failed").build();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	@POST
	@Path("/SQLServer2008R2")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installSQLServer2008R2(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath,
			@QueryParam("rootPswd") String rootPswd,
			@QueryParam("hostName") String hostName,
			@QueryParam("userName") String userName
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP(uid, ip,"sqlserver2008r2");
			if (a.sendSetupSQLServer2008R2Msg(uid, ip, scIPAddr, installPath,rootPswd,hostName,userName)) {
				res = Response.ok("install SQLServer2008R2 request is already executing").build();
			} else {
				res = Response.ok("install SQLServer2008R2 request failed").build();
			}
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
	public Response installSQLServer2000(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP(uid, ip,"sqlserver2000");
			if (a.sendSetupSQLServer2000Msg(uid, ip, scIPAddr, installPath)) {
				res = Response.ok("install SQLServer2000 request is already executing").build();
			} else {
				res = Response.ok("install SQLServer2000 request failed").build();
			}
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
	public Response installOracle10g(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP(uid, ip,"oracle10g");
			if (a.sendSetupOracle10gMsg(uid, ip, scIPAddr, installPath)) {
				res = Response.ok("install Oracle10g request is already executing").build();
			} else {
				res = Response.ok("install Oracle10g request failed").build();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	@POST
	@Path("/Oracle11g")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installOracle11g(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("hostname") String hostname,
			@QueryParam("inventorypath") String inventorypath,
			@QueryParam("oraclebase") String oraclebase,
			@QueryParam("oraclehome") String oraclehome,
			@QueryParam("rootPswd") String rootPswd
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP(uid, ip,"oracle11g");
			if (a.sendSetupOracle11gMsg(uid, ip, scIPAddr, hostname,inventorypath,oraclebase,oraclehome,rootPswd)) {
				res = Response.ok("install Oracle11g request is already executing").build();
			} else {
				res = Response.ok("install Oracle11g request failed").build();
			}
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
	public Response install360(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			scIPAddr = dbop.getRCAddrByIP(uid, ip,"360");
			if (a.sendSetup360Msg(uid, ip, scIPAddr, installPath)) {
				res = Response.ok("install 360 request is already executing").build();
			} else {
				res = Response.ok("install 360 request failed").build();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	
	@GET
	@Path("/status")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStatus(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("softwareName") String softwareName
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		res = Response.ok(a.sendGetStatusMsg(uid, ip, softwareName)).build();
		return res;
	}
	
}
