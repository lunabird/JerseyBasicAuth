package sample.hello.resources;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import sample.hello.bean.AppParamConfiguration;

@Path("/AppParamConfiguration")
public class AppParamResource {
	@POST
	@Path("/MySql")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configMySql(
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			
			) throws JSONException {
		Response res = null;
		JSONObject entity = new JSONObject();
		AppParamConfiguration a = new AppParamConfiguration();
		String result = a.sendConfigMySqlMsg( ip,cfgFilePath, paramName, paramValue);
		entity.put("response",result);
		res = Response.ok(entity).build();
		return res;
	}
	@POST
	@Path("/Tomcat")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configTomcat(
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) throws JSONException {
		Response res = null;
		JSONObject entity = new JSONObject();
		AppParamConfiguration a = new AppParamConfiguration();
		String result = a.sendConfigTomcatMsg( ip,cfgFilePath, paramName, paramValue);
		entity.put("response",result);
		res = Response.ok(entity).build();
		return res;
	}
	@POST
	@Path("/Jdk")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configJdk(
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) throws JSONException {
		Response res = null;JSONObject entity = new JSONObject();
		AppParamConfiguration a = new AppParamConfiguration();

		String result = a.sendConfigJdkMsg( ip,cfgFilePath, paramName, paramValue);
		entity.put("response",result);
		res = Response.ok(entity).build();
		return res;
	}
	@POST
	@Path("/Apache")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configApache(
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) throws JSONException {
		Response res = null;
		JSONObject entity = new JSONObject();
		AppParamConfiguration a = new AppParamConfiguration();

		String result = a.sendConfigApacheMsg( ip, cfgFilePath,paramName, paramValue);
		/*if (true) {
			res = Response.ok("chang passwd success").build();
		} else {
			res = Response.noContent().build();
		}*/
		entity.put("response",result);
		res = Response.ok(entity).build();
		return res;
	}
	@POST
	@Path("/Nginx")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configNginx(
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) throws JSONException {
		Response res = null;JSONObject entity = new JSONObject();
		AppParamConfiguration a = new AppParamConfiguration();

		String result = a.sendConfigNginxMsg( ip,cfgFilePath, paramName, paramValue);
		entity.put("response",result);
		res = Response.ok(entity).build();
		return res;
	}
	@POST
	@Path("/ZendGuardLoader")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configZendGuardLoader(
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) throws JSONException {
		Response res = null;
		JSONObject entity = new JSONObject();
		AppParamConfiguration a = new AppParamConfiguration();

		String result = a.sendConfigZendGuardLoaderMsg( ip, cfgFilePath,paramName, paramValue);
		entity.put("response",result);
		res = Response.ok(entity).build();
		return res;
	}
	@POST
	@Path("/Python")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configPython(
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) throws JSONException {
		Response res = null;
		JSONObject entity = new JSONObject();
		AppParamConfiguration a = new AppParamConfiguration();

		String result = a.sendConfigPythonMsg( ip,cfgFilePath, paramName, paramValue);
		entity.put("response",result);
		res = Response.ok(entity).build();
		return res;
	}
	@POST
	@Path("/Memcached")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configMemcached(
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) throws JSONException {
		Response res = null;
		JSONObject entity = new JSONObject();
		AppParamConfiguration a = new AppParamConfiguration();

		String result = a.sendConfigMemcachedMsg( ip, cfgFilePath,paramName, paramValue);
		entity.put("response",result);
		res = Response.ok(entity).build();
		return res;
	}
	@POST
	@Path("/IISRewrite")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configIISRewrite(
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) throws JSONException {
		Response res = null;
		JSONObject entity = new JSONObject();
		AppParamConfiguration a = new AppParamConfiguration();

		String result = a.sendConfigIISRewriteMsg( ip,cfgFilePath, paramName, paramValue);
		entity.put("response",result);
		res = Response.ok(entity).build();
		return res;
	}
	/*@POST
	@Path("/ASP")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configASP(
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) {
		Response res = null;JSONObject entity = new JSONObject();
		AppParamConfiguration a = new AppParamConfiguration();

		if (a.sendConfigASPMsg( ip,cfgFilePath, paramName, paramValue)) {
			res = Response.ok("config ASP request success").build();
		} else {
			res = Response.ok("config ASP request failed").build();
		}

		if (true) {
			res = Response.ok("chang passwd success").build();
		} else {
			res = Response.noContent().build();
		}
		entity.put("response",result);res = Response.ok(entity).build();return res;
	}*/
	
	@POST
	@Path("/FTP")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configFTP(
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) throws JSONException {
		Response res = null;
		JSONObject entity = new JSONObject();
		AppParamConfiguration a = new AppParamConfiguration();

		String result = a.sendConfigFTPMsg( ip,cfgFilePath, paramName, paramValue);
		entity.put("response",result);
		res = Response.ok(entity).build();
		return res;
	}
	/*@POST
	@Path("/ASPNET")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configASPNET(
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) {
		Response res = null;JSONObject entity = new JSONObject();
		AppParamConfiguration a = new AppParamConfiguration();

		if (a.sendConfigASPNETMsg( ip,cfgFilePath, paramName, paramValue)) {
			res = Response.ok("config ASPNET request success").build();
		} else {
			res = Response.ok("config ASPNET request failed").build();
		}

		if (true) {
			res = Response.ok("chang passwd success").build();
		} else {
			res = Response.noContent().build();
		}
		entity.put("response",result);res = Response.ok(entity).build();return res;
	}*/
	@POST
	@Path("/SQLServer2008R2")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configSQLServer2008R2(
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) throws JSONException {
		Response res = null;
		JSONObject entity = new JSONObject();
		AppParamConfiguration a = new AppParamConfiguration();

		String result = a.sendConfigSQLServer2008R2Msg( ip, cfgFilePath,paramName, paramValue);
		entity.put("response",result);
		res = Response.ok(entity).build();
		return res;
	}
	@POST
	@Path("/SQLServer2000")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configSQLServer2000(
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) throws JSONException {
		Response res = null;JSONObject entity = new JSONObject();
		AppParamConfiguration a = new AppParamConfiguration();

		String result = a.sendConfigSQLServer2000Msg( ip, cfgFilePath,paramName, paramValue);
		entity.put("response",result);
		res = Response.ok(entity).build();
		return res;
	}
	@POST
	@Path("/Oracle10g")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configOracle10g(
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) throws JSONException {
		Response res = null;
		JSONObject entity = new JSONObject();
		AppParamConfiguration a = new AppParamConfiguration();

		String result = a.sendConfigOracle10gMsg( ip,cfgFilePath, paramName, paramValue);
		entity.put("response",result);
		res = Response.ok(entity).build();
		return res;
	}
	@POST
	@Path("/Oracle11g")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configOracle11g(
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) throws JSONException {
		Response res = null;JSONObject entity = new JSONObject();
		AppParamConfiguration a = new AppParamConfiguration();

		String result = a.sendConfigOracle11gMsg( ip,cfgFilePath, paramName, paramValue);
		entity.put("response",result);
		res = Response.ok(entity).build();
		return res;
	}
	@POST
	@Path("/360")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response config360(
			@QueryParam("ip") String ip,
			@QueryParam("cfgFilePath") String cfgFilePath,
			@QueryParam("paramName") String paramName,
			@QueryParam("paramValue") String paramValue
			) throws JSONException {
		Response res = null;JSONObject entity = new JSONObject();
		AppParamConfiguration a = new AppParamConfiguration();

		String result = a.sendConfig360Msg( ip,cfgFilePath, paramName, paramValue);
		entity.put("response",result);
		res = Response.ok(entity).build();
		return res;
	}
}
