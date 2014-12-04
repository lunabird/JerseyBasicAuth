package sample.hello.resources;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import sample.hello.bean.AppParamConfiguration;

@Path("/AppParamConfiguration")
public class AppParamResource {
	@POST
	@Path("/MySql")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configMySql(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			
			) {
		Response res = null;
		AppParamConfiguration a = new AppParamConfiguration();

		if (a.sendConfigMySqlMsg(uid, ip,cfgFilePath, paramName, paramValue)) {
			res = Response.ok("config mysql request success").build();
		} else {
			res = Response.ok("config mysql request failed").build();
		}
		return res;
	}
	@POST
	@Path("/Tomcat")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configTomcat(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) {
		Response res = null;
		AppParamConfiguration a = new AppParamConfiguration();

		if (a.sendConfigTomcatMsg(uid, ip,cfgFilePath, paramName, paramValue)) {
			res = Response.ok("config Tomcat request success").build();
		} else {
			res = Response.ok("config Tomcat request failed").build();
		}
		return res;
	}
	@POST
	@Path("/Jdk")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configJdk(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) {
		Response res = null;
		AppParamConfiguration a = new AppParamConfiguration();

		if (a.sendConfigJdkMsg(uid, ip,cfgFilePath, paramName, paramValue)) {
			res = Response.ok("config Jdk request success").build();
		} else {
			res = Response.ok("config Jdk request failed").build();
		}
		return res;
	}
	@POST
	@Path("/Apache")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configApache(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) {
		Response res = null;
		AppParamConfiguration a = new AppParamConfiguration();

		if (a.sendConfigApacheMsg(uid, ip, cfgFilePath,paramName, paramValue)) {
			res = Response.ok("config Apache request success").build();
		} else {
			res = Response.ok("config Apache request failed").build();
		}

		/*if (true) {
			res = Response.ok("chang passwd success").build();
		} else {
			res = Response.noContent().build();
		}*/
		return res;
	}
	@POST
	@Path("/Nginx")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configNginx(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) {
		Response res = null;
		AppParamConfiguration a = new AppParamConfiguration();

		if (a.sendConfigNginxMsg(uid, ip,cfgFilePath, paramName, paramValue)) {
			res = Response.ok("config Nginx request success").build();
		} else {
			res = Response.ok("config Nginx request failed").build();
		}

		/*if (true) {
			res = Response.ok("chang passwd success").build();
		} else {
			res = Response.noContent().build();
		}*/
		return res;
	}
	@POST
	@Path("/ZendGuardLoader")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configZendGuardLoader(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) {
		Response res = null;
		AppParamConfiguration a = new AppParamConfiguration();

		if (a.sendConfigZendGuardLoaderMsg(uid, ip, cfgFilePath,paramName, paramValue)) {
			res = Response.ok("config ZendGuardLoader request success").build();
		} else {
			res = Response.ok("config ZendGuardLoader request failed").build();
		}

		/*if (true) {
			res = Response.ok("chang passwd success").build();
		} else {
			res = Response.noContent().build();
		}*/
		return res;
	}
	@POST
	@Path("/Python")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configPython(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) {
		Response res = null;
		AppParamConfiguration a = new AppParamConfiguration();

		if (a.sendConfigPythonMsg(uid, ip,cfgFilePath, paramName, paramValue)) {
			res = Response.ok("config Python request success").build();
		} else {
			res = Response.ok("config Python request failed").build();
		}

		/*if (true) {
			res = Response.ok("chang passwd success").build();
		} else {
			res = Response.noContent().build();
		}*/
		return res;
	}
	@POST
	@Path("/Memcached")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configMemcached(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) {
		Response res = null;
		AppParamConfiguration a = new AppParamConfiguration();

		if (a.sendConfigMemcachedMsg(uid, ip, cfgFilePath,paramName, paramValue)) {
			res = Response.ok("config Memcached request success").build();
		} else {
			res = Response.ok("config Memcached request failed").build();
		}

		/*if (true) {
			res = Response.ok("chang passwd success").build();
		} else {
			res = Response.noContent().build();
		}*/
		return res;
	}
	@POST
	@Path("/IISRewrite")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configIISRewrite(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) {
		Response res = null;
		AppParamConfiguration a = new AppParamConfiguration();

		if (a.sendConfigIISRewriteMsg(uid, ip,cfgFilePath, paramName, paramValue)) {
			res = Response.ok("config IISRewrite request success").build();
		} else {
			res = Response.ok("config IISRewrite request failed").build();
		}

		/*if (true) {
			res = Response.ok("chang passwd success").build();
		} else {
			res = Response.noContent().build();
		}*/
		return res;
	}
	@POST
	@Path("/ASP")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configASP(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) {
		Response res = null;
		AppParamConfiguration a = new AppParamConfiguration();

		if (a.sendConfigASPMsg(uid, ip,cfgFilePath, paramName, paramValue)) {
			res = Response.ok("config ASP request success").build();
		} else {
			res = Response.ok("config ASP request failed").build();
		}

		/*if (true) {
			res = Response.ok("chang passwd success").build();
		} else {
			res = Response.noContent().build();
		}*/
		return res;
	}
	
	@POST
	@Path("/FTP")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configFTP(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) {
		Response res = null;
		AppParamConfiguration a = new AppParamConfiguration();

		if (a.sendConfigFTPMsg(uid, ip,cfgFilePath, paramName, paramValue)) {
			res = Response.ok("config FTP request success").build();
		} else {
			res = Response.ok("config FTP request failed").build();
		}

		/*if (true) {
			res = Response.ok("chang passwd success").build();
		} else {
			res = Response.noContent().build();
		}*/
		return res;
	}
	@POST
	@Path("/ASPNET")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configASPNET(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) {
		Response res = null;
		AppParamConfiguration a = new AppParamConfiguration();

		if (a.sendConfigASPNETMsg(uid, ip,cfgFilePath, paramName, paramValue)) {
			res = Response.ok("config ASPNET request success").build();
		} else {
			res = Response.ok("config ASPNET request failed").build();
		}

		/*if (true) {
			res = Response.ok("chang passwd success").build();
		} else {
			res = Response.noContent().build();
		}*/
		return res;
	}
	@POST
	@Path("/SQLServer2008R2")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configSQLServer2008R2(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) {
		Response res = null;
		AppParamConfiguration a = new AppParamConfiguration();

		if (a.sendConfigSQLServer2008R2Msg(uid, ip, cfgFilePath,paramName, paramValue)) {
			res = Response.ok("config SQLServer2008R2 request success").build();
		} else {
			res = Response.ok("config SQLServer2008R2 request failed").build();
		}

		/*if (true) {
			res = Response.ok("chang passwd success").build();
		} else {
			res = Response.noContent().build();
		}*/
		return res;
	}
	@POST
	@Path("/SQLServer2000")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configSQLServer2000(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) {
		Response res = null;
		AppParamConfiguration a = new AppParamConfiguration();

		if (a.sendConfigSQLServer2000Msg(uid, ip, cfgFilePath,paramName, paramValue)) {
			res = Response.ok("config SQLServer2000 request success").build();
		} else {
			res = Response.ok("config SQLServer2000 request failed").build();
		}

		/*if (true) {
			res = Response.ok("chang passwd success").build();
		} else {
			res = Response.noContent().build();
		}*/
		return res;
	}
	@POST
	@Path("/Oracle10g")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configOracle10g(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) {
		Response res = null;
		AppParamConfiguration a = new AppParamConfiguration();

		if (a.sendConfigOracle10gMsg(uid, ip,cfgFilePath, paramName, paramValue)) {
			res = Response.ok("config Oracle10g request success").build();
		} else {
			res = Response.ok("config Oracle10g request failed").build();
		}

		/*if (true) {
			res = Response.ok("chang passwd success").build();
		} else {
			res = Response.noContent().build();
		}*/
		return res;
	}
	@POST
	@Path("/Oracle11g")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configOracle11g(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) {
		Response res = null;
		AppParamConfiguration a = new AppParamConfiguration();

		if (a.sendConfigOracle11gMsg(uid, ip,cfgFilePath, paramName, paramValue)) {
			res = Response.ok("config Oracle11g request success").build();
		} else {
			res = Response.ok("config Oracle11g request failed").build();
		}

		/*if (true) {
			res = Response.ok("chang passwd success").build();
		} else {
			res = Response.noContent().build();
		}*/
		return res;
	}
	@POST
	@Path("/360")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response config360(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) {
		Response res = null;
		AppParamConfiguration a = new AppParamConfiguration();

		if (a.sendConfig360Msg(uid, ip,cfgFilePath, paramName, paramValue)) {
			res = Response.ok("config 360 request success").build();
		} else {
			res = Response.ok("config 360 request failed").build();
		}

		/*if (true) {
			res = Response.ok("chang passwd success").build();
		} else {
			res = Response.noContent().build();
		}*/
		return res;
	}
}
