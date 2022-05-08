package my.com.todolist;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TodoDAO {
	
	public String addTodoItem(Connection con, String todoCode, String todoDesc, String createdBy) {
		
		PreparedStatement ptst = null;
		String returnStr = "";
		int i = 0;
		try {
			String query = "Insert into todo_list (todo_code,todo_desc,created_by)"
					+ "values (?,?,?)";
			ptst = con.prepareStatement(query);
			ptst.setString(1, todoCode);
			ptst.setString(2, todoDesc);
			ptst.setString(3, createdBy);
			i = ptst.executeUpdate();

			returnStr = "{\"todo_code\":\""+todoCode+"\","
					+ "\"status\": \"ADD TODO ITEM SUCCESS\"}";

		}catch (Exception e) {
			returnStr = "{\"todo_code\":\""+todoCode+"\","
					+ "\"status\": \"ADD TODO ITEM FAILED\"}";
			e.printStackTrace();
		}finally {
			DatabaseUtil.closePreparedStatement(ptst);
		}
		
		return returnStr;
	}
	
	public String deleteTodoItem(Connection con, String todoCode) {
		
		PreparedStatement ptst = null;
		String returnStr = "";
		try {
			String query = "DELETE FROM todo_list WHERE todo_code = ?";
			ptst = con.prepareStatement(query);
			ptst.setString(1, todoCode);
			ptst.executeUpdate();
			
			returnStr = "{\"todo_code\":\""+todoCode+"\","
					+ "\"status\": \"DELETE TODO ITEM SUCCESS\"}";
			
				
			
		}catch (Exception e) {
			returnStr = "{\"todo_code\":\""+todoCode+"\","
					+ "\"status\": \"DELETE TODO ITEM FAILED\"}";
			e.printStackTrace();
		}finally {
			DatabaseUtil.closePreparedStatement(ptst);
		}
		
		return returnStr;
	}
	
	public String listTodoItem(Connection con, String todoCode) {
		
		PreparedStatement ptst = null;
		String returnStr = "";
		try {
			String query = "SELECT * FROM todo_list ";
			query = todoCode != null ? query + " WHERE todo_code = '" + todoCode + "'" : query;
			
			TodoBean todo = (TodoBean) DatabaseUtil.getBeanObject(con, new TodoBean(), query, false);
			returnStr = todo != null ? JsonUtils.convertObjectToJson(todo, false) : "{\"status\": \"LIST NOT FOUND\"}";
		}catch (Exception e) {
			returnStr = "{\"status\": \"UNABLE TO GET LIST\"}";
			e.printStackTrace();
		}
		
		return returnStr;
	}
	
	public String markTodoItem(Connection con, String todoCode) {
		
		PreparedStatement ptst = null;
		String returnStr = "";
		try {
			String query = "update todo_list set status = 'COMP', updated_date = now(), comp_date = now() "
						+ " WHERE todo_code = '" + todoCode + "'" ;
			
			ptst = con.prepareStatement(query);
			ptst.executeUpdate();
			
			TodoBean todo = (TodoBean) DatabaseUtil.getBeanObject(con, new TodoBean(), query, false);
			returnStr = listTodoItem(con, todoCode);
			
		}catch (Exception e) {
			returnStr = "{\"todo_code\":\""+todoCode+"\","
					+ "\"status\": \"MARK TODO ITEM FAILED\"}";
			e.printStackTrace();
		}finally {
			DatabaseUtil.closePreparedStatement(ptst);
		}
		
		return returnStr;
	}

}
