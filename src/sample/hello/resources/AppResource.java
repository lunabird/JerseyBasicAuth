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
	public Response installMySql(
			@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath,
			@QueryParam("rootPswd") String rootPswd
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			if (ip.size() == 1) {
				JSONObject entity = new JSONObject();//表示要给用户返回的json对象
				scIPAddr = dbop.getRCAddrByIP("hp", ip.get(0), "mysql");
				// 将消息发送给Agent成功以后，返回一个代表操作id的代号opID，同时这也是数据库中存储的代表该操作的opID
				int opID = a.sendSetupMySqlMsg( ip.get(0), scIPAddr,installPath, rootPswd);
				// 构造要返回给用户的json对象
				entity.put("opID", opID);
				entity.put("status","install mysql has already been executing ");//
				res = Response.ok(entity).build();
			}else if(ip.size()>1){
				JSONArray jarr = new JSONArray();
				for(int i=0;i<ip.size();i++){
					JSONObject entity = new JSONObject();//表示要给用户返回的json对象
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i), "mysql");
					int opID = a.sendSetupMySqlMsg( ip.get(i), scIPAddr,installPath, rootPswd);
					// 构造要返回给用户的json对象
					entity.put("opID", opID);
					entity.put("status","install mysql has already been executing ");//
					jarr.put(entity);
				}
				res = Response.ok(jarr).build();
			}
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
	public Response installMySqlOnLinux(
			@QueryParam("ip") List<String> ip,
			@QueryParam("rootPswd") String rootPswd
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr = new String[2];
		try {
			if (ip.size() == 1) {
				JSONObject entity = new JSONObject();
				scIPAddr = dbop.getRCAddrByIP("hp", ip.get(0), "mysql");
				int opID = a.sendSetupMySqlOnLinuxMsg( ip.get(0), scIPAddr,rootPswd);
				entity.put("opID", opID);
				entity.put("status","install mysql has already been executing ");
				res = Response.ok(entity).build();
			} else if (ip.size() > 1) {
				JSONArray jarr = new JSONArray();
				for (int i = 0; i < ip.size(); i++) {
					JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
					scIPAddr = dbop.getRCAddrByIP( "hp",ip.get(i), "mysql");
					int opID = a.sendSetupMySqlOnLinuxMsg( ip.get(i), scIPAddr,rootPswd);
					// 构造要返回给用户的json对象
					entity.put("opID", opID);
					entity.put("status","install mysql has already been executing ");//
					jarr.put(entity);
				}
				res = Response.ok(jarr).build();
			}
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
	public Response installTomcat(
			@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath,
			@QueryParam("jdkPath") String jdkPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			if (ip.size() == 1) {
				JSONObject entity = new JSONObject();
				scIPAddr = dbop.getRCAddrByIP("hp", ip.get(0), "tomcat");
				int opID = a.sendSetupTomcatMsg( ip.get(0), scIPAddr,installPath, jdkPath);
				entity.put("opID", opID);
				entity.put("status","install tomcat has already been executing ");
				res = Response.ok(entity).build();
			} else if (ip.size() > 1) {
				JSONArray jarr = new JSONArray();
				for (int i = 0; i < ip.size(); i++) {
					JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i), "tomcat");
					int opID = a.sendSetupTomcatMsg( ip.get(i), scIPAddr,installPath, jdkPath);
					// 构造要返回给用户的json对象
					entity.put("opID", opID);
					entity.put("status","install tomcat has already been executing ");//
					jarr.put(entity);
				}
				res = Response.ok(jarr).build();
			}
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
//	public Response installTomcatOnLinux( 
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
//			scIPAddr = dbop.getRCAddrByIP( ip,"tomcat");
//			int opID = a.sendSetupTomcatOnLinuxMsg( ip, scIPAddr, installPath,jdkName,jdkPath)) {
//				entity.put("opID", value);entity.put("status", value);res = Response.ok("install tomcat request is already executing").build();
//			} else {
//				entity.put("opID", value);entity.put("status", value);res = Response.ok("install tomcat request failed").build();
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
	public Response installJdk(
			@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			if (ip.size() == 1) {
				JSONObject entity = new JSONObject();
				scIPAddr = dbop.getRCAddrByIP( "hp",ip.get(0), "jdk");
				int opID = a.sendSetupJdkMsg( ip.get(0), scIPAddr,installPath);
				entity.put("opID", opID);
				entity.put("status", "install jdk has already been executing ");
				res = Response.ok(entity).build();
			} else if (ip.size() > 1) {
				JSONArray jarr = new JSONArray();
				for (int i = 0; i < ip.size(); i++) {
					JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
					scIPAddr = dbop.getRCAddrByIP( "hp",ip.get(i), "jdk");
					int opID = a.sendSetupJdkMsg( ip.get(i), scIPAddr,installPath);
					// 构造要返回给用户的json对象
					entity.put("opID", opID);
					entity.put("status","install jdk has already been executing ");//
					jarr.put(entity);
				}
				res = Response.ok(jarr).build();
			}
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
	public Response installApache(
			@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			if (ip.size() == 1) {
				JSONObject entity = new JSONObject();
				scIPAddr = dbop.getRCAddrByIP("hp", ip.get(0), "apache");
				int opID = a.sendSetupApacheMsg( ip.get(0), scIPAddr, installPath);
				entity.put("opID", opID);
				entity.put("status","install Apache has already been executing ");
				res = Response.ok(entity).build();
			} else if (ip.size() > 1) {
				JSONArray jarr = new JSONArray();
				for (int i = 0; i < ip.size(); i++) {
					JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
					scIPAddr = dbop.getRCAddrByIP( "hp",ip.get(i), "apache");
					int opID = a.sendSetupApacheMsg( ip.get(i), scIPAddr, installPath);
					// 构造要返回给用户的json对象
					entity.put("opID", opID);
					entity.put("status","install Apache has already been executing ");//
					jarr.put(entity);
				}
				res = Response.ok(jarr).build();
			}
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
	public Response installNginx(
			@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			if (ip.size() == 1) {
				JSONObject entity = new JSONObject();
				scIPAddr = dbop.getRCAddrByIP("hp", ip.get(0), "nginx");
				int opID = a.sendSetupNginxMsg( ip.get(0), scIPAddr,installPath);
				entity.put("opID", opID);
				entity.put("status","install Nginx has already been executing ");
				res = Response.ok(entity).build();
			} else if (ip.size() > 1) {
				JSONArray jarr = new JSONArray();
				for (int i = 0; i < ip.size(); i++) {
					JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
					scIPAddr = dbop.getRCAddrByIP( "hp",ip.get(i), "nginx");
					int opID = a.sendSetupNginxMsg( ip.get(i), scIPAddr,installPath);
					// 构造要返回给用户的json对象
					entity.put("opID", opID);
					entity.put("status","install Nginx has already been executing ");//
					jarr.put(entity);
				}
				res = Response.ok(jarr).build();
			}
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
			@QueryParam("ip") List<String> ip,
			@QueryParam("phpPath") String phpPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			if (ip.size() == 1) {
				JSONObject entity = new JSONObject();
				scIPAddr = dbop.getRCAddrByIP("hp", ip.get(0), "zendguardloader");
				int opID = a.sendSetupZendGuardLoaderMsg( ip.get(0),scIPAddr, phpPath);
				entity.put("opID", opID);
				entity.put("status","install ZendGuardLoader has already been executing ");
				res = Response.ok(entity).build();
			} else if (ip.size() > 1) {
				JSONArray jarr = new JSONArray();
				for (int i = 0; i < ip.size(); i++) {
					JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
					scIPAddr = dbop.getRCAddrByIP( "hp",ip.get(i),"zendguardloader");
					int opID = a.sendSetupZendGuardLoaderMsg( ip.get(i),scIPAddr, phpPath);
					// 构造要返回给用户的json对象
					entity.put("opID", opID);
					entity.put("status","install ZendGuardLoader has already been executing ");//
					jarr.put(entity);
				}
				res = Response.ok(jarr).build();
			}
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
	public Response installZendGuardLoader_Linux(
			@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath,
			@QueryParam("phpPath") String phpPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			if (ip.size() == 1) {
				JSONObject entity = new JSONObject();
				scIPAddr = dbop.getRCAddrByIP("hp", ip.get(0), "zendguardloader");
				int opID = a.sendSetupZendGuardLoaderMsgOnLinux( ip.get(0),scIPAddr, installPath, phpPath);
				entity.put("opID", opID);
				entity.put("status","install ZendGuardLoader has already been executing ");
				res = Response.ok(entity).build();
			} else if (ip.size() > 1) {
				JSONArray jarr = new JSONArray();
				for (int i = 0; i < ip.size(); i++) {
					JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
					scIPAddr = dbop.getRCAddrByIP( "hp",ip.get(i),"zendguardloader");
					int opID = a.sendSetupZendGuardLoaderMsgOnLinux(ip.get(i), scIPAddr, installPath, phpPath);
					// 构造要返回给用户的json对象
					entity.put("opID", opID);
					entity.put("status","install ZendGuardLoader has already been executing ");//
					jarr.put(entity);
				}
				res = Response.ok(jarr).build();
			}
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
	public Response installPython(
			@QueryParam("ip") List<String> ip
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			if (ip.size() == 1) {
				JSONObject entity = new JSONObject();
				scIPAddr = dbop.getRCAddrByIP("hp", ip.get(0), "python");
				int opID = a.sendSetupPythonMsg( ip.get(0), scIPAddr);
				entity.put("opID", opID);
				entity.put("status","install python has already been executing ");
				res = Response.ok(entity).build();
			} else if (ip.size() > 1) {
				JSONArray jarr = new JSONArray();
				for (int i = 0; i < ip.size(); i++) {
					JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i),"python");
					int opID = a.sendSetupPythonMsg( ip.get(i), scIPAddr);
					// 构造要返回给用户的json对象
					entity.put("opID", opID);
					entity.put("status","install python has already been executing ");//
					jarr.put(entity);
				}
				res = Response.ok(jarr).build();
			}
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
	public Response installPython(
			@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			if (ip.size() == 1) {
				JSONObject entity = new JSONObject();
				scIPAddr = dbop.getRCAddrByIP("hp", ip.get(0), "python");
				int opID = a.sendSetupPythonMsgOnLinux( ip.get(0),scIPAddr, installPath);
				entity.put("opID", opID);
				entity.put("status","install python has already been executing ");
				res = Response.ok(entity).build();
			} else if (ip.size() > 1) {
				JSONArray jarr = new JSONArray();
				for (int i = 0; i < ip.size(); i++) {
					JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
					scIPAddr = dbop.getRCAddrByIP( "hp",ip.get(i), "python");
					int opID = a.sendSetupPythonMsgOnLinux( ip.get(i),scIPAddr, installPath);
					// 构造要返回给用户的json对象
					entity.put("opID", opID);
					entity.put("status","install python has already been executing ");//
					jarr.put(entity);
				}
				res = Response.ok(jarr).build();
			}
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
	public Response installMemcached(
			@QueryParam("ip") List<String>  ip
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			if (ip.size() == 1) {
				JSONObject entity = new JSONObject();
				scIPAddr = dbop.getRCAddrByIP( "hp",ip.get(0), "memcached");
				int opID = a.sendSetupMemcachedMsg( ip.get(0), scIPAddr);
				entity.put("opID", opID);
				entity.put("status","install memcached has already been executing ");
				res = Response.ok(entity).build();
			} else if (ip.size() > 1) {
				JSONArray jarr = new JSONArray();
				for (int i = 0; i < ip.size(); i++) {
					JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i), "memcached");
					int opID = a.sendSetupMemcachedMsg( ip.get(i), scIPAddr);
					// 构造要返回给用户的json对象
					entity.put("opID", opID);
					entity.put("status","install memcached has already been executing ");//
					jarr.put(entity);
				}
				res = Response.ok(jarr).build();
			}
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
	public Response installMemcached(
			@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			if (ip.size() == 1) {
				JSONObject entity = new JSONObject();
				scIPAddr = dbop.getRCAddrByIP( "hp",ip.get(0), "memcached");
				int opID = a.sendSetupMemcachedMsgOnLinux( ip.get(0),scIPAddr, installPath);
				entity.put("opID", opID);
				entity.put("status","install memcached has already been executing ");
				res = Response.ok(entity).build();
			} else if (ip.size() > 1) {
				JSONArray jarr = new JSONArray();
				for (int i = 0; i < ip.size(); i++) {
					JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i), "memcached");
					int opID = a.sendSetupMemcachedMsgOnLinux( ip.get(i),scIPAddr, installPath);
					// 构造要返回给用户的json对象
					entity.put("opID", opID);
					entity.put("status","install memcached has already been executing ");//
					jarr.put(entity);
				}
				res = Response.ok(jarr).build();
			}
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
	public Response installIISRewrite(
			@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			if (ip.size() == 1) {
				JSONObject entity = new JSONObject();
				scIPAddr = dbop.getRCAddrByIP( "hp",ip.get(0), "iisrewrite");
				int opID = a.sendSetupIISRewriteMsg( ip.get(0), scIPAddr,installPath);
				entity.put("opID", opID);
				entity.put("status","install IISRewrite has already been executing ");
				res = Response.ok(entity).build();
			} else if (ip.size() > 1) {
				JSONArray jarr = new JSONArray();
				for (int i = 0; i < ip.size(); i++) {
					JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
					scIPAddr = dbop.getRCAddrByIP( "hp",ip.get(i), "iisrewrite");
					int opID = a.sendSetupIISRewriteMsg( ip.get(i),scIPAddr, installPath);
					// 构造要返回给用户的json对象
					entity.put("opID", opID);
					entity.put("status","install IISRewrite has already been executing ");//
					jarr.put(entity);
				}
				res = Response.ok(jarr).build();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	/*@POST
	@Path("/ASP")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installASP(
			@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			if (ip.size() == 1) {
				JSONObject entity = new JSONObject();
				scIPAddr = dbop.getRCAddrByIP( ip.get(0), "asp");
				int opID = a.sendSetupASPMsg( ip.get(0), scIPAddr,installPath);
				entity.put("opID", opID);
				entity.put("status", "install ASP has already been executing ");
				res = Response.ok(entity).build();
			} else if (ip.size() > 1) {
				JSONArray jarr = new JSONArray();
				for (int i = 0; i < ip.size(); i++) {
					JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
					scIPAddr = dbop.getRCAddrByIP( ip.get(i), "asp");
					int opID = a.sendSetupASPMsg( ip.get(i), scIPAddr,installPath);
					// 构造要返回给用户的json对象
					entity.put("opID", opID);
					entity.put("status","install ASP has already been executing ");//
					jarr.put(entity);
				}
				res = Response.ok(jarr).build();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}*/
	@POST
	@Path("/FTP")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installFTP(
			@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			if (ip.size() == 1) {
				JSONObject entity = new JSONObject();
				scIPAddr = dbop.getRCAddrByIP( "hp",ip.get(0), "ftp");
				int opID = a.sendSetupFTPMsg( ip.get(0), scIPAddr,installPath);
				entity.put("opID", opID);
				entity.put("status", "install FTP has already been executing ");
				res = Response.ok(entity).build();
			} else if (ip.size() > 1) {
				JSONArray jarr = new JSONArray();
				for (int i = 0; i < ip.size(); i++) {
					JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i), "ftp");
					int opID = a.sendSetupFTPMsg( ip.get(i), scIPAddr,installPath);
					// 构造要返回给用户的json对象
					entity.put("opID", opID);
					entity.put("status","install FTP has already been executing ");//
					jarr.put(entity);
				}
				res = Response.ok(jarr).build();
			}
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
	public Response installFTP(
			@QueryParam("ip") List<String> ip
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			if (ip.size() == 1) {
				JSONObject entity = new JSONObject();
				scIPAddr = dbop.getRCAddrByIP( "hp",ip.get(0), "ftp");
				int opID = a.sendSetupFTPMsgOnLinux( ip.get(0), scIPAddr);
				entity.put("opID", opID);
				entity.put("status", "install FTP has already been executing ");
				res = Response.ok(entity).build();
			} else if (ip.size() > 1) {
				JSONArray jarr = new JSONArray();
				for (int i = 0; i < ip.size(); i++) {
					JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i), "ftp");
					int opID = a.sendSetupFTPMsgOnLinux( ip.get(i),scIPAddr);
					// 构造要返回给用户的json对象
					entity.put("opID", opID);
					entity.put("status","install FTP has already been executing ");//
					jarr.put(entity);
				}
				res = Response.ok(jarr).build();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	/*@POST
	@Path("/ASPNET")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installASPNET(
			@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			if (ip.size() == 1) {
				JSONObject entity = new JSONObject();
				scIPAddr = dbop.getRCAddrByIP( ip.get(0), "aspnet");
				int opID = a.sendSetupASPNETMsg( ip.get(0), scIPAddr,installPath);
				entity.put("opID", opID);
				entity.put("status","install ASPNET has already been executing ");
				res = Response.ok(entity).build();
			} else if (ip.size() > 1) {
				JSONArray jarr = new JSONArray();
				for (int i = 0; i < ip.size(); i++) {
					JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
					scIPAddr = dbop.getRCAddrByIP( ip.get(i), "aspnet");
					int opID = a.sendSetupASPNETMsg( ip.get(i), scIPAddr,installPath);
					// 构造要返回给用户的json对象
					entity.put("opID", opID);
					entity.put("status","install ASPNET has already been executing ");//
					jarr.put(entity);
				}
				res = Response.ok(jarr).build();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}*/
	@POST
	@Path("/SQLServer2008R2")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response installSQLServer2008R2(
			@QueryParam("ip") List<String> ip,
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
			if (ip.size() == 1) {
				JSONObject entity = new JSONObject();
				scIPAddr = dbop.getRCAddrByIP( "hp",ip.get(0), "sqlserver2008r2");
				int opID = a.sendSetupSQLServer2008R2Msg( ip.get(0),scIPAddr, installPath, rootPswd, hostName, userName);
				entity.put("opID", opID);
				entity.put("status","install SQLServer2008R2 has already been executing ");
				res = Response.ok(entity).build();
			} else if (ip.size() > 1) {
				JSONArray jarr = new JSONArray();
				for (int i = 0; i < ip.size(); i++) {
					JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
					scIPAddr = dbop.getRCAddrByIP( "hp",ip.get(i),"sqlserver2008r2");
					int opID = a.sendSetupSQLServer2008R2Msg( ip.get(i),scIPAddr, installPath, rootPswd, hostName,userName);
					// 构造要返回给用户的json对象
					entity.put("opID", opID);
					entity.put("status","install SQLServer2008R2 has already been executing ");//
					jarr.put(entity);
				}
				res = Response.ok(jarr).build();
			}
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
	public Response installSQLServer2000(
			@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			if (ip.size() == 1) {
				JSONObject entity = new JSONObject();
				scIPAddr = dbop.getRCAddrByIP( "hp",ip.get(0), "sqlserver2000");
				int opID = a.sendSetupSQLServer2000Msg( ip.get(0),scIPAddr, installPath);
				entity.put("opID", opID);
				entity.put("status","install SQLServer2000 has already been executing ");
				res = Response.ok(entity).build();
			} else if (ip.size() > 1) {
				JSONArray jarr = new JSONArray();
				for (int i = 0; i < ip.size(); i++) {
					JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i),"sqlserver2000");
					int opID = a.sendSetupSQLServer2000Msg( ip.get(i),scIPAddr, installPath);
					// 构造要返回给用户的json对象
					entity.put("opID", opID);
					entity.put("status","install SQLServer2000 has already been executing ");//
					jarr.put(entity);
				}
				res = Response.ok(jarr).build();
			}
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
	public Response installOracle10g(
			@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			if (ip.size() == 1) {
				JSONObject entity = new JSONObject();
				scIPAddr = dbop.getRCAddrByIP( "hp",ip.get(0), "oracle10g");
				int opID = a.sendSetupOracle10gMsg( ip.get(0), scIPAddr,installPath);
				entity.put("opID", opID);
				entity.put("status","install Oracle10g has already been executing ");
				res = Response.ok(entity).build();
			} else if (ip.size() > 1) {
				JSONArray jarr = new JSONArray();
				for (int i = 0; i < ip.size(); i++) {
					JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
					scIPAddr = dbop.getRCAddrByIP( "hp",ip.get(i), "oracle10g");
					int opID = a.sendSetupOracle10gMsg( ip.get(i),scIPAddr, installPath);
					// 构造要返回给用户的json对象
					entity.put("opID", opID);
					entity.put("status","install Oracle10g has already been executing ");//
					jarr.put(entity);
				}
				res = Response.ok(jarr).build();
			}
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
	public Response installOracle11g(
			@QueryParam("ip") List<String> ip,
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
			if (ip.size() == 1) {
				JSONObject entity = new JSONObject();
				scIPAddr = dbop.getRCAddrByIP( "hp",ip.get(0), "oracle11g");
				int opID = a.sendSetupOracle11gMsg( ip.get(0), scIPAddr,hostname, inventorypath, oraclebase, oraclehome,rootPswd);
				entity.put("opID", opID);
				entity.put("status","install Oracle11g has already been executing ");
				res = Response.ok(entity).build();
			} else if (ip.size() > 1) {
				JSONArray jarr = new JSONArray();
				for (int i = 0; i < ip.size(); i++) {
					JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
					scIPAddr = dbop.getRCAddrByIP( "hp",ip.get(i), "oracle11g");
					int opID = a.sendSetupOracle11gMsg( ip.get(i),scIPAddr, hostname, inventorypath, oraclebase,oraclehome, rootPswd);
					// 构造要返回给用户的json对象
					entity.put("opID", opID);
					entity.put("status","install Oracle11g has already been executing ");//
					jarr.put(entity);
				}
				res = Response.ok(jarr).build();
			}
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
	public Response install360(
			@QueryParam("ip") List<String> ip,
			@QueryParam("installPath") String installPath
			) {
		Response res = null;
		ApplicationBase a = new ApplicationBase();
		DBOperation dbop = new DBOperation();
		String[] scIPAddr= new String[2];
		try {
			if (ip.size() == 1) {
				JSONObject entity = new JSONObject();
				scIPAddr = dbop.getRCAddrByIP( "hp",ip.get(0), "360");
				int opID = a.sendSetup360Msg( ip.get(0), scIPAddr,installPath);
				entity.put("opID", opID);
				entity.put("status", "install 360 has already been executing ");
				res = Response.ok(entity).build();
			} else if (ip.size() > 1) {
				JSONArray jarr = new JSONArray();
				for (int i = 0; i < ip.size(); i++) {
					JSONObject entity = new JSONObject();// 表示要给用户返回的json对象
					scIPAddr = dbop.getRCAddrByIP("hp", ip.get(i), "360");
					int opID = a.sendSetup360Msg( ip.get(i), scIPAddr,installPath);
					// 构造要返回给用户的json对象
					entity.put("opID", opID);
					entity.put("status","install 360 has already been executing ");//
					jarr.put(entity);
				}
				res = Response.ok(jarr).build();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	
//	@GET
//	@Path("/status")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getStatus(
//			@QueryParam("ip") String ip,
//			@QueryParam("softwareName") String softwareName
//			) {
//		Response res = null;
//		ApplicationBase a = new ApplicationBase();JSONObject entity = new JSONObject();
//		entity.put("opID", opID);
//		entity.put("status", "");
//		res = Response.ok(entity).build();
//		return res;
//	}
	
}
