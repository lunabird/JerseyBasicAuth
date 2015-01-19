package sample.hello.resources;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
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
import edu.xidian.enc.AESUtil;
import edu.xidian.enc.SerializeUtil;
import edu.xidian.message.Message;
import edu.xidian.message.MsgType;

@Path("/VMReboot")
public class RebootResource {
	@POST
	@Path("/reboot")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changePasswd(@QueryParam("ip") List<String> ip){
		Response res;
		JSONArray entity = new JSONArray();
		try {
			for (int i = 0; i < ip.size(); i++) {
				JSONObject jo = new JSONObject();
				boolean flag = sendVMRebootMsg(ip.get(i));
				jo.put("ip", flag);
				entity.put(jo);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		res =  Response.ok(entity).build();
		return res;
	}
	
	/**
	 * 发送虚拟机重启指令
	 * @param ip
	 */
	public boolean sendVMRebootMsg(String ip){
		int opID = insertEvent(ip,"VMReboot");
		//发送Socket消息给Agent
		try {
			System.out.println("ip*************************"+ip);
			Socket socket = new Socket(ip, 9700);
			Message msg = new Message(MsgType.reboot, opID+"",null);
			//加密
			String datatemp = SerializeUtil.serialize(msg);  
			byte[] str = AESUtil.encrypt(datatemp,ip);
			//传输
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(str);
			socket.close();
			return true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 将重启事件插入到数据库中
	 * @param hostIp
	 * @param opName
	 */
	public int insertEvent(String hostIp,String opName){
		int opID = -1;
		DBOperation dbop = new DBOperation();
		try {
			opID = dbop.insertOperation(hostIp,opName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opID;
	}
}
