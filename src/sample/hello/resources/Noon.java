package sample.hello.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/noon")
public class Noon {
	@GET
	@Path("/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMsg(@PathParam("param") String name) {
		String msg = "Rest say: good noon " + name;
		return msg;
	}
}