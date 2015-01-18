package sample.hello.resources;

import java.sql.SQLException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import sample.DBOP.DBOperation;

/**
 * �û���ѯ�¼�ִ��״̬��api�������ѯ��װ������ȣ��ű�ִ�н��ȵ�
 * @author huangpeng
 *
 */
@Path("/ProgressQuery")
public class ProgressQuery {
	@GET
	@Path("/progress")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changePasswd(@QueryParam("opID") String opID) throws JSONException, SQLException {
		Response res;
		DBOperation dbop = new DBOperation();
		JSONObject entity = new JSONObject();
		int eid = -1;
		//�ж��û������opID�Ƿ���ȷ  
		try{
			eid = Integer.parseInt(opID);
		}catch(Exception e){
			entity.put("exception", "event id error!");
			res =  Response.ok(entity).build();
		}		
		String status = dbop.getOpStatus(eid);
		entity.put("status", status);
		res =  Response.ok(entity).build();
		return res;
	}
}
