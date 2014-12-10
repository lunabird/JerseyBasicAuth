package sample.hello.resources;

import java.util.List;

import javax.persistence.Entity;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import sample.hello.bean.OSBase;

@Path("/OSBase")
public class OSBaseResource {
//	@Context
//	UriInfo uriInfo;
//	@Context
//	Request request;
	
	
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
	public Response changePasswd(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("userName") String userName,
			@QueryParam("passwd") String passwd) {
		Response res;
		OSBase ob = new OSBase();
//		if (ob.sendChangePasswdMsg(uid, ip, userName, passwd)) {
		if(true){
			res =  Response.ok("chang passwd success").build();
		} else {
			res =  Response.ok("chang passwd failed").build();
		}
		return res;
	}
	
	@POST
	@Path("/sysService")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSysService(){
		Response res;
		OSBase ob = new OSBase();
		if (true) {
			res =  Response.ok("success").build();
		} else {
			res =  Response.ok("failed").build();
		}
		return res;
		
	}
	
	
	/**
	 * 系统服务的启动/停止
	 * @param uid
	 * @param ip
	 * @param serviceName
	 * @param operation
	 * @return
	 * 
	 */
	@PUT
	@Path("/sysServiceConfig")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response startOrStopService(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("serviceName") String serviceName,
			@QueryParam("operation") String operation) {
		Response res = null;
		OSBase ob = new OSBase();
		if(ob.sendchgSysServiceStateMsg(uid, ip, serviceName, operation)){
			if(operation.equals("start")){
				res = Response.ok("start service success").build();
			}else if(operation.equals("stop")){
				res = Response.ok("stop service success").build();
			}			
		}else{
			if(operation.equals("start")){
				res = Response.ok("start service failed").build();
			}else if(operation.equals("stop")){
				res = Response.ok("stop service failed").build();
			}
		}
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
	public Response viewLog(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip, 
			@QueryParam("logType") String logType) {
		OSBase ob = new OSBase();
		JSONObject o = ob.sendViewSysLogMsg(uid, ip, logType);
		if(o!=null){
			return Response.ok(o, MediaType.APPLICATION_JSON).build();
		}
		return Response.ok("get system log failed").build();
	}
	
	@PUT
	@Path("/IPAdd")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeIP(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip, 
			@QueryParam("mac") String mac,
			@QueryParam("changeToIP") String changeToIP,
			@QueryParam("mask") String mask,
			@QueryParam("gateway") String gateway,
			@QueryParam("dns") List<String> dns
			){
		
		Response res;
		OSBase ob = new OSBase();
		String[] dnsArr = (String[])dns.toArray(new String[dns.size()]);
		if (ob.sendChangeIPMsg(uid, ip, mac,changeToIP,mask, gateway,dnsArr)) {
			res =  Response.ok("chang IP success").build();
		} else {
			res =  Response.ok("chang IP failed").build();
		}
		
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
	 */
	@PUT
	@Path("/IPAdd_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeIPOnLinux(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip, 
			@QueryParam("deviceName") String deviceName,
			@QueryParam("mask") String mask,
			@QueryParam("changeToIP") String changeToIP
			){
		
		Response res;
		OSBase ob = new OSBase();
		if (ob.sendChangeIPOnLinuxMsg(uid, ip, deviceName,mask, changeToIP)) {
			res =  Response.ok("chang IP success").build();
		} else {
			res =  Response.ok("chang IP failed").build();
		}
		
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
	 */
	@PUT
	@Path("/affiIPAdd")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeAffiIP(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip, 
			@QueryParam("mac") String mac,
			@QueryParam("affiIp") List<String> affiIP, 
			@QueryParam("affiMask") List<String> affiMask,
			@QueryParam("affiGateway") List<String> affiGateway){
		Response res;
		OSBase ob = new OSBase();
		String[] affiIPArr = (String[])affiIP.toArray(new String[affiIP.size()]);
		String[] affiMaskArr = (String[])affiMask.toArray(new String[affiMask.size()]);
		String[] affiGatewayArr = (String[])affiGateway.toArray(new String[affiGateway.size()]);
		if(ob.sendChangeAffiIPMsg(uid, ip, mac,  affiIPArr, affiMaskArr, affiGatewayArr)){
			res =  Response.ok("chang windows affi IP success").build();
		} else {
			res =  Response.ok("chang windows affi IP failed").build();
		}
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
	 */
	@PUT
	@Path("/affiIPAdd_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeAffiIPOnLinux(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip, 
			@QueryParam("deviceName") String deviceName,
			@QueryParam("mask") String mask,
			@QueryParam("changeToIP") String changeToIP){
		Response res;
		OSBase ob = new OSBase();
		
		if(ob.sendChangeAffiIPOnLinuxMsg(uid, ip,deviceName, mask,changeToIP)){
			res =  Response.ok("chang linux affi IP success").build();
		} else {
			res =  Response.ok("chang linux affi IP failed").build();
		}
		
		return res;
	}
	@PUT
	@Path("/disk")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeAffiIP(@QueryParam("uid") String uid,@QueryParam("ip") String ip){
		Response res;
		OSBase ob = new OSBase();
		if (ob.sendDiskFormatMsg(uid, ip)) {
			res =  Response.ok("disk format success").build();
		} else {
			res =  Response.ok("diak format failed").build();
		}
		return res;
	}
	@PUT
	@Path("/secRule")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeSecRules(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("policyName") String policyName,
			@QueryParam("protocol") String protocol,
			@QueryParam("port") String port,
			@QueryParam("addSecIP") String addSecIP) {
		Response res;
		OSBase ob = new OSBase();
		if (ob.sendChangeSecRuleMsg(uid, ip, policyName, protocol, port, addSecIP)) {
			res =  Response.ok("change security rule success").build();
		} else {
			res =  Response.ok("change security rule failed").build();
		}
		return res;
	}
	@PUT
	@Path("/secRule_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeSecRulesOnLinux(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("protocol") String protocol,
			@QueryParam("sourceIP") String sourceIP,
			@QueryParam("port") String port) {
		Response res;
		OSBase ob = new OSBase();
		if (ob.sendChangeSecRuleOnLinuxMsg(uid, ip, protocol,sourceIP, port)) {
			res =  Response.ok("change security rule on linux success").build();
		} else {
			res =  Response.ok("change security rule on linux failed").build();
		}
		return res;
	}
	@PUT
	@Path("/ulimit_Linux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeUlimitOnLinux(@QueryParam("uid") String uid,
			@QueryParam("ip") String ip,
			@QueryParam("ulimit") String ulimit) {
		Response res;
		OSBase ob = new OSBase();
		if (ob.sendChangeUlimitOnLinuxMsg(uid, ip, ulimit)) {
			res =  Response.ok("change ulimit success").build();
		} else {
			res =  Response.ok("change ulimit failed").build();
		}
		return res;
	}
}
