package my.com.todolist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseUtil {

	public static Connection connectToDatabase(String url, String uName, String uPass){
        Connection conn=null;
        try {
            Properties props = new Properties();
            props.setProperty("user",uName);
            props.setProperty("password",uPass);
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, props);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
	
	public static void closeConnection(Connection con){
		try {
			if (con != null) {
				con.close();
			}
			System.out.println("CONNECTION CLOSED");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void closeResultSet(ResultSet rst){
		try {
			rst.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void closePreparedStatement(PreparedStatement pstmt){
		try {
			pstmt.close();
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}
	
	public static void closeStatement(Statement stmt){
		try {
			stmt.close();
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}
	
	public static Object getBeanObject(Connection conn, Object beanObj, String sql, boolean isRemoveDash) {
		Statement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				BeanAutoRenderer.autoRender(beanObj, rs, isRemoveDash);
				i++;
			}
			return i > 0 ? beanObj : null;
		} catch (Exception e) {
			ErrorsHandler.printError(DatabaseUtil.class, e.getMessage(), e);
		} finally {
			DatabaseUtil.closeResultSet(rs);
			DatabaseUtil.closeStatement(st);
		}
		return null;
	}
}
