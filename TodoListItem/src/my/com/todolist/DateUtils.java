package my.com.todolist;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtils {
	
    public static final String Format6 = "yyyy-MM-dd hh:mm:ss.SSS aa zz ZZ"; //using by configEPFile properties
    public static final String DDMMYYYY = "dd/MM/yyyy";
    public static final DateFormat dateFormat_AM_PM = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
    public static final DateFormat ddmmyyyDf = new SimpleDateFormat("ddMMyyyy");
    public static final DateFormat yyyyMMddDf = new SimpleDateFormat("yyyyMMdd");
    public static final DateFormat dd_mm_yyyyDf = new SimpleDateFormat(DDMMYYYY);
    public static final DateFormat format_time_only = new SimpleDateFormat("h:mm a");
    public static final DateFormat ddMMyy = new SimpleDateFormat("ddMMyy");
    public static final DateFormat yyyyMMddhhmmssDf = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final DateFormat yyyyMMddHHmmDf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final DateFormat yyyy_dd_MM_HH_mm_ss_df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String DateFormat4 = "yyyy-MM-dd HH:mm:ss.SSSSS";
    public static final String DateFormat5 = "yyyyMMddHHmmss";
    public static final String DateHL7 = "yyyyMMddHHmmss";
    public static final String KUL = "Asia/Kuala_Lumpur";    

    public static Date removeHMS(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date adjustDate(Date date, int daysNo) {
        Date dateWithoutHSM = removeHMS(date);
        Calendar cal = new GregorianCalendar();
        cal.setTime(dateWithoutHSM);
        cal.add(Calendar.DAY_OF_YEAR, daysNo);
        return cal.getTime();
    }

    public static Date createDate(int year, int month, int date, int hour, int minute) {
        Calendar cal = new GregorianCalendar();
        cal.set(year, month, date, hour, minute);
        return cal.getTime();
    }

    public static final String getCurrentDateTime(String dateformat) {
        SimpleDateFormat format = new SimpleDateFormat(dateformat);
        format.setTimeZone(TimeZone.getTimeZone(DateUtils.KUL));
        return format.format(new Date());
    }

    public static final String getCurrentDate(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        format.setTimeZone(TimeZone.getTimeZone(DateUtils.KUL));
        return format.format(new Date());
    }
    
    public static final String format(Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        format.setTimeZone(TimeZone.getTimeZone(DateUtils.KUL));
        return format.format(date);
    }
    
    public static final String getDateString(Date date, String patern){
        SimpleDateFormat format = new SimpleDateFormat(patern);
        format.setTimeZone(TimeZone.getTimeZone(DateUtils.KUL));
        return format.format(date);
    }
    
    public static final Date getDate(String pattern, String datetime) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            format.setTimeZone(TimeZone.getTimeZone(DateUtils.KUL));
            return format.parse(datetime);
        } catch (Exception ex) { }
        return null;
    }
    
    public static final String getDateStringOtherFormat(String pattern, String datetime){
    	try {
    		String pattern1 = "";
    		if(datetime.length() <= 8)
    			pattern1 = "yyyyMMdd";
    		else if(datetime.length() > 8)
    			pattern1 = "yyyyMMddHHmmss";
    		Date date = getDate(pattern1, datetime);
    		return getDateString(date, pattern);
        } catch (Exception ex) { }
        return null;
    }
}
