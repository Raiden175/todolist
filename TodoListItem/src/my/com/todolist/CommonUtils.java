package my.com.todolist;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;


public class CommonUtils {
	
	public static String NOT_SUPPORT = "MODULE {1} ARE CURRENTLY NOT SUPPORTED. PLEASE CONTACT PHIS SYSTEM ADMINISTRATOR";
	
	public static String getNotSupportJson(String modulename){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	    format.setTimeZone(TimeZone.getTimeZone(DateUtils.KUL));
		String status = "{\n"
	            + "	\"updated_date\": \"" + format.format(new Date()) + "\",\n"
	            + "	\"Status\": \"ERROR\",\n"
	            + "	\"Status_Desc\": \"" + NOT_SUPPORT.replace("{1}", modulename) + "\"\n"
	            + "}";
		return status;
	}

	public static Properties getProperties(String fileName) {
		Properties properties = new Properties();
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(new File(fileName));
			properties.load(stream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return properties;
	}
}
