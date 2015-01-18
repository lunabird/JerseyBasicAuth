package sample.hello.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import sample.hello.bean.OSBase;

@Path("/OSBase")
public class OSBaseResource {
	/**
	 * 修改操作系统密码
	 * @param uid
	 * @param ip
	 * @param userName
	 * @param passwd
	 * @return
	 */
	@PUT
	@Path("/vmSysPwd")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changePasswd(
			@QueryParam("ip") String ip,
			@QueryParam("userName") String userName,
			@QueryParam("passwd") String passwd) {
		Response res = null;
		JSONObject entity = new JSONObject();
		OSBase ob = new OSBase();
		try {
//			String result = ob.sendChangePasswdMsg(ip, userName, passwd);
//			entity.put("response",result);
			entity.put("response","OKOKOKOK");
			res = Response.ok(entity).build();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@PUT
	@Path("/vmSysPwd_json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changePasswd_json( String json) {
		Response res = null;
		try {
			JSONObject o = new JSONObject(json);
			System.out.println(o.get("ip"));
			String ip = (String) o.get("ip");
			System.out.println(o.get("userName"));
			String userName = (String) o.get("userName");
			System.out.println(o.get("passwd"));
			String passwd = (String) o.get("passwd");
			JSONObject entity = new JSONObject();
			OSBase ob = new OSBase();
			String result = ob.sendChangePasswdMsg(ip, userName, passwd);
			entity.put("response",result);
			res =  Response.ok(entity).build();
			return res;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
//	@PUT
//	@Path("/sysService")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getSysService(){
//		Response res;
//		OSBase ob = new OSBase();
//		if (true) {
//			res =  Response.ok("success").build();
//		} else {
//			res =  Response.ok("failed").build();
//		}
//		return res;
//		
//	}
	
	
	/**
	 * 系统服务的启动/停止
	 * @param uid
	 * @param ip
	 * @param serviceName
	 * @param operation
	 * @return
	 * @throws JSONException 
	 * 
	 */
	@PUT
	@Path("/sysServiceConfig")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response startOrStopService(
			@QueryParam("ip") String ip,
			@QueryParam("serviceName") String serviceName,
			@QueryParam("operation") String operation) throws JSONException {
		Response res = null;
		OSBase ob = new OSBase();
		JSONObject entity = new JSONObject();
		String result = ob.sendchgSysServiceStateMsg(ip, serviceName, operation);
		entity.put("response",result);
		res =  Response.ok(entity).build();
		return res;
	}
	/**
	 * 查看系统日志
	 * @param uid
	 * @param ip
	 * @param logType
	 * @return
	 */
	@GET
	@Path("/sysLog")
	@Produces(MediaType.APPLICATION_JSON)
	public Response viewLog(
			@QueryParam("ip") String ip, 
			@QueryParam("logType") String logType) {
		OSBase ob = new OSBase();
		JSONObject o = ob.sendViewSysLogMsg(ip, logType);
		if(o!=null){
			return Response.ok(o, MediaType.APPLICATION_JSON).build();
		}
		return Response.ok("get system log failed").build();
	}
	
	@PUT
	@Path("/IPAdd")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeIP(
			@QueryParam("ip") String ip, 
			@QueryParam("mac") String mac,
			@QueryParam("changeToIP") String changeToIP,
			@QueryParam("mask") String mask,
			@QueryParam("gateway") String gateway,
			@QueryParam("dns") List<String> dns
			) throws JSONException{
		
		Response res = null;
		OSBase ob = new OSBase();
		JSONObject entity = new JSONObject();
		String[] dnsArr = (String[])dns.toArray(new String[dns.size()]);
		String result = ob.sendChangeIPMsg(ip, mac,changeToIP,mask, gateway,dnsArr);
		entity.put("response",result);
		res =  Response.ok(entity).build();
		return res;
	}
	/**
	 * 修改IP on Linux
	 * @param uid
	 * @param ip
	 * @param mac
	 * @param mask
	 * @param gateway
	 * @param dns
	 * @param affiIp
	 * @param affiMask
	 * @return
	 * @throws JSONException 
	 */
	@PUT
	@Path("/IPAdd_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeIPOnLinux(
			@QueryParam("ip") String ip, 
			@QueryParam("deviceName") String deviceName,
			@QueryParam("mask") String mask,
			@QueryParam("changeToIP") String changeToIP
			) throws JSONException{
		
		Response res;
		OSBase ob = new OSBase();
		JSONObject entity = new JSONObject();
		String result = ob.sendChangeIPOnLinuxMsg(ip, deviceName,mask, changeToIP);
		entity.put("response",result);
		res =  Response.ok(entity).build();
		return res;
	}
	/**
	 * 修改附属IP
	 * @param uid
	 * @param ip
	 * @param mac
	 * @param mask
	 * @param gateway
	 * @param dns
	 * @param affiIP
	 * @param affiMask
	 * @return
	 * @throws JSONException 
	 */
	@PUT
	@Path("/affiIPAdd")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeAffiIP(
			@QueryParam("ip") String ip, 
			@QueryParam("mac") String mac,
			@QueryParam("affiIp") List<String> affiIP, 
			@QueryParam("affiMask") List<String> affiMask,
			@QueryParam("affiGateway") List<String> affiGateway) throws JSONException{
		Response res;
		OSBase ob = new OSBase();
		JSONObject entity = new JSONObject();
		String[] affiIPArr = (String[])affiIP.toArray(new String[affiIP.size()]);
		String[] affiMaskArr = (String[])affiMask.toArray(new String[affiMask.size()]);
		String[] affiGatewayArr = (String[])affiGateway.toArray(new String[affiGateway.size()]);
		String result = ob.sendChangeAffiIPMsg(ip, mac,  affiIPArr, affiMaskArr, affiGatewayArr);
		entity.put("response",result);
		res =  Response.ok(entity).build();
		return res;
	}
	
	/**
	 * 修改附属IP on Linux
	 * @param uid
	 * @param ip
	 * @param deviceName
	 * @param mask
	 * @param changeToIP
	 * @return
	 * @throws JSONException 
	 */
	@PUT
	@Path("/affiIPAdd_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeAffiIPOnLinux(
			@QueryParam("ip") String ip, 
			@QueryParam("deviceName") String deviceName,
			@QueryParam("mask") String mask,
			@QueryParam("changeToIP") String changeToIP) throws JSONException{
		Response res;
		OSBase ob = new OSBase();
		JSONObject entity = new JSONObject();
		String result = ob.sendChangeAffiIPOnLinuxMsg(ip,deviceName, mask,changeToIP);
		entity.put("response",result);
		res =  Response.ok(entity).build();
		return res;
	}
	@PUT
	@Path("/disk")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeAffiIP(@QueryParam("ip") String ip) throws JSONException{
		Response res;
		OSBase ob = new OSBase();
		JSONObject entity = new JSONObject();
		String result = ob.sendDiskFormatMsg(ip);
		entity.put("response",result);
		res =  Response.ok(entity).build();
		return res;
	}
	@PUT
	@Path("/secRule")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeSecRules(
			@QueryParam("ip") String ip,
			@QueryParam("policyName") String policyName,
			@QueryParam("protocol") String protocol,
			@QueryParam("port") String port,
			@QueryParam("addSecIP") String addSecIP) throws JSONException {
		Response res;
		OSBase ob = new OSBase();
		JSONObject entity = new JSONObject();
		String result =ob.sendChangeSecRuleMsg(ip, policyName, protocol, port, addSecIP);
		entity.put("response",result);
		res =  Response.ok(entity).build();
		return res;
	}
	@PUT
	@Path("/secRule_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeSecRulesOnLinux(
			@QueryParam("ip") String ip,
			@QueryParam("protocol") String protocol,
			@QueryParam("sourceIP") String sourceIP,
			@QueryParam("port") String port) throws JSONException {
		Response res;
		OSBase ob = new OSBase();
		JSONObject entity = new JSONObject();
		String result = ob.sendChangeSecRuleOnLinuxMsg(ip, protocol,sourceIP, port);
		entity.put("response",result);
		res =  Response.ok(entity).build();
		return res;
	}
	@PUT
	@Path("/ulimit_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeUlimitOnLinux(
			@QueryParam("ip") String ip,
			@QueryParam("ulimit") String ulimit) throws JSONException {
		Response res;
		OSBase ob = new OSBase();
		JSONObject entity = new JSONObject();
		String result = ob.sendChangeUlimitOnLinuxMsg(ip, ulimit);
		entity.put("response",result);
		res =  Response.ok(entity).build();
		return res;
	}
}
