package my.com.todolist;

import java.math.BigInteger;

import org.codehaus.jackson.annotate.JsonIgnore;

public class TodoBean {
	
	@JsonIgnore
	private Long   todo_seqno;
	private String todo_code;
	private String todo_desc;
	private String created_date;
	private String updated_date;
	private String created_by;
	private String status;
	private String active_flag;
	private String comp_date;
		
	
	public Long getTodo_seqno() {
		return todo_seqno;
	}
	public void setTodo_seqno(Long todo_seqno) {
		this.todo_seqno = todo_seqno;
	}
	public String getTodo_code() {
		return todo_code;
	}
	public void setTodo_code(String todo_code) {
		this.todo_code = todo_code;
	}
	public String getTodo_desc() {
		return todo_desc;
	}
	public void setTodo_desc(String todo_desc) {
		this.todo_desc = todo_desc;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public String getUpdated_date() {
		return updated_date;
	}
	public void setUpdated_date(String updated_date) {
		this.updated_date = updated_date;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getActive_flag() {
		return active_flag;
	}
	public void setActive_flag(String active_flag) {
		this.active_flag = active_flag;
	}
	public String getComp_date() {
		return comp_date;
	}
	public void setComp_date(String comp_date) {
		this.comp_date = comp_date;
	}

}
