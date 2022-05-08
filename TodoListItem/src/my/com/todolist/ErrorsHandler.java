package my.com.todolist;

import org.apache.log4j.Logger;

public class ErrorsHandler {
	
	public static final String DateFormat4 = "yyyy-MM-dd HH:mm:ss.SSSSS";

    public static String getErrorInString(Exception ex) {
        String errlog = ex.getMessage();
        for (StackTraceElement element : ex.getStackTrace()) {
            errlog = errlog + "\n" + element.toString();
        }
        return errlog;
    }

    public static void printData2Param(Class<?> cls, String itemname, String itemvalue, boolean isPrintError) {
        String datetime = DateUtils.getCurrentDateTime(DateUtils.DateFormat4);// format.format(new Date());
        Logger.getLogger(cls.getName()).error(datetime + "===>" + itemname + " : " + itemvalue);
        try {
        	if(isPrintError)
        		System.err.println("\n" + datetime + "===>" + itemvalue.replaceAll("\r", "\n"));
        	else
        		System.out.println("\n" + datetime + "===>" + itemname + " : " + itemvalue.replaceAll("\r", "\n"));
        } catch (Exception e) {
            System.out.println("\n" + datetime + "===>" + itemname + " : " + itemvalue);
        }
    }

    public static void printData1Param(Class<?> cls, String itemvalue, boolean isPrintError) {
        String datetime = DateUtils.getCurrentDateTime(DateUtils.DateFormat4);// format.format(new Date());
        Logger.getLogger(cls.getName()).error(datetime + "===>" + itemvalue);
        try {
        	if(isPrintError)
        		System.err.println("\n" + datetime + "===>" + itemvalue.replaceAll("\r", "\n"));
        	else
                System.out.println("\n" + datetime + "===>" + itemvalue.replaceAll("\r", "\n"));
        } catch (Exception e) {
            System.out.println("\n" + datetime + "===>" + itemvalue);
        }
    }

    public static void printError(Class<?> cls, String itemvalue, Exception ex) {
        String datetime = DateUtils.getCurrentDateTime(DateUtils.DateFormat4);// format.format(new Date());
        Logger.getLogger(cls.getName()).error(datetime + "===>" + itemvalue, ex);
        System.err.println("\n" + datetime + "===>" + itemvalue + "\n" + ErrorsHandler.getErrorInString(ex));
    }

    public static String getErrorChunk(String errors, int strlen) {
        String result = null;
        if (errors != null) {
            if (errors.length() > strlen) {
                errors = errors.substring(0, strlen);
            }
            try {
                result = errors.replaceAll("\n", ".");
            } catch (Exception e) {
                result = errors;
            }
        }
        return result;
    }
}
