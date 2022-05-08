package my.com.todolist;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@Path("/TODO")
public class TodoServices {
	
	static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	static Date date = new Date();
	
	
	@POST
	@Path("/addToDoItem")
	@Produces({MediaType.APPLICATION_JSON})
	public String addToDoItem(@QueryParam("todoCode") String todoCode, @QueryParam("todoDesc") String todoDesc, @QueryParam("createdBy") String createdBy) {
		Connection con = null;
		try {
			TodoDAO todoObj = new TodoDAO();
			System.err.println(new Date() + " : ADD TODO ITEM " + todoCode);
			PropertiesHandler ph = new PropertiesHandler(true);
			con = DatabaseUtil.connectToDatabase(ph.getDatabaseURL(), ph.getDatabaseUser(), ph.getDatabasePassword());
			String result = todoObj.addTodoItem(con, todoCode, todoDesc, createdBy);
			System.err.println(new Date() + "==>  : RESULT ADD TODO ITEM : " + result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DatabaseUtil.closeConnection(con);
		}
		return CommonUtils.getNotSupportJson("ADD TODO ITEM");
	}
	
	
	@POST
	@Path("/deleteToDoItem")
	@Produces({MediaType.APPLICATION_JSON})
	public String deleteToDoItem(@QueryParam("todoCode") String todoCode) {
		Connection con = null;
		try {
			TodoDAO todoObj = new TodoDAO();
			System.err.println(new Date() + " : DELETE TODO ITEM " + todoCode);
			PropertiesHandler ph = new PropertiesHandler(true);
			con = DatabaseUtil.connectToDatabase(ph.getDatabaseURL(), ph.getDatabaseUser(), ph.getDatabasePassword());
			String result = todoObj.deleteTodoItem(con, todoCode);
			System.err.println(new Date() + "==>  : RESULT DELETE TODO ITEM : " + result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DatabaseUtil.closeConnection(con);
		}
		return CommonUtils.getNotSupportJson("DELETE TODO ITEM");
	}
	
	
	
	@POST
	@Path("/listToDoItem")
	@Produces({MediaType.APPLICATION_JSON})
	public String listToDoItem(@QueryParam("todoCode") String todoCode) {
		Connection con = null;
		try {
			TodoDAO todoObj = new TodoDAO();
			System.err.println(new Date() + " : LISTING TODO ITEM " + todoCode);
			PropertiesHandler ph = new PropertiesHandler(true);
			con = DatabaseUtil.connectToDatabase(ph.getDatabaseURL(), ph.getDatabaseUser(), ph.getDatabasePassword());
			String result = todoObj.listTodoItem(con,todoCode);
			System.err.println(new Date() + "==>  : LISTING TODO ITEM : " + result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DatabaseUtil.closeConnection(con);
		}
		return CommonUtils.getNotSupportJson("LISTING TODO ITEM");
	}
	
	
	@POST
	@Path("/markToDoItem")
	@Produces({MediaType.APPLICATION_JSON})
	public String markToDoItem(@QueryParam("todoCode") String todoCode) {
		Connection con = null;
		try {
			TodoDAO todoObj = new TodoDAO();
			System.err.println(new Date() + " : MARK TODO ITEM " + todoCode);
			PropertiesHandler ph = new PropertiesHandler(true);
			con = DatabaseUtil.connectToDatabase(ph.getDatabaseURL(), ph.getDatabaseUser(), ph.getDatabasePassword());
			String result = todoObj.markTodoItem(con, todoCode);
			System.err.println(new Date() + "==>  : RESULT MARK TODO ITEM : " + result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DatabaseUtil.closeConnection(con);
		}
		return CommonUtils.getNotSupportJson("MARK TODO ITEM");
	}
	
	
	
	@POST
	@Path("/post")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String testPost(String test1){
		System.out.println("===>>>" + test1);
		return "{\"status\":\"SUCCESS\", \"data\":\"" + test1 + "\"}";
	}
	
	@GET
	@Path("/get")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String testGet(@QueryParam("test") String test1){
		PropertiesHandler ph = new PropertiesHandler(true);
		System.err.println("jdbc = " + ph.getDatabaseURL());
		System.err.println("===>>>" + test1);
		return "{\"status\":\"SUCCESS\", \"data\":\"" + test1 + "\"}";
	}

}
