package sample.hello.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import sample.hello.bean.VMScript;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;


@Path("/vmScript")
public class VMScriptResource {
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@QueryParam("uid") String uid,
			@FormDataParam("ip") List<String> ip,
			@FormDataParam("upload") InputStream is,
			@FormDataParam("upload") FormDataContentDisposition formData) {
		//脚本下载到服务器端的存储位置
		String fileLocation = "c:/" + formData.getFileName();
		Response res = null;
		try {
			File f = saveFile(is, fileLocation);
			String result = "Successfully File Uploaded on the path "+ fileLocation;
			VMScript vs = new VMScript();
			if(ip.size()==1){
				JSONObject json = new JSONObject();
				//如果发送给agent成功，返回操作id
				int opID = vs.sendExeVmScriptMsg(uid, ip.get(0), f);		
				json.put("opID", opID);// 操作id
				json.put("status", "the send script is already executing ");// 操作状态
				res = Response.ok(json).build();
			}else if(ip.size()>1){
				JSONArray jarr = new JSONArray();
				for(int i=0;i<ip.size();i++){
					JSONObject json = new JSONObject();
					int opID = vs.sendExeVmScriptMsg(uid, ip.get(i), f);
					json.put("opID", opID);// 操作id
					json.put("status", "the send script is already executing ");// 操作状态
					jarr.put(json);
				}
				res = Response.ok(jarr).build();
			}
			//删除临时脚本
			if (f.isFile() && f.exists()) {
				f.delete();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	private File saveFile(InputStream is, String fileLocation)
			throws IOException {
		File f = new File(fileLocation);
		OutputStream os = new FileOutputStream(f);
		byte[] buffer = new byte[256];
		int bytes = 0;
		while ((bytes = is.read(buffer)) != -1) {
			os.write(buffer, 0, bytes);
		}
		return f;
	}

	
}
