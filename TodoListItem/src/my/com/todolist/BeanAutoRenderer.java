package my.com.todolist;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;

public class BeanAutoRenderer {

	public static void autoRender(Object bean,ResultSet rs, boolean isRemoveDash) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class entity1Clzz=bean.getClass();
		Method[] methods=entity1Clzz.getMethods();
		String str = null;
		for(Method method:methods){
			if(StringUtils.startsWith(method.getName(), "set")) {
				Class[] types = method.getParameterTypes();
				if(types[0].toString().contains("interface") || types[0].toString().contains("List")){
				}else if(types[0].toString().contains("Timestamp") || types[0].toString().contains("Date")){
					method.invoke(bean, getTimestamp(rs,method.getName(), isRemoveDash));
				}else if(types[0].toString().contains("int") || types[0].toString().contains("Bigint")){
						method.invoke(bean, getInt(rs,method.getName(), isRemoveDash));
				}else if(types[0].toString().contains("long") || types[0].toString().contains("java.lang.Long")){
					method.invoke(bean, getLong(rs,method.getName(), isRemoveDash));
				}else if(types[0].toString().contains("char") || types[0].toString().contains("java.lang.Character")){
					str = getString(rs,method.getName(), isRemoveDash);
					method.invoke(bean, StringUtils.isBlank(str) ? null : str.charAt(0));
				}else if(types[0].toString().contains("double") || types[0].toString().contains("class java.lang.Double")){
					method.invoke(bean, getDouble(rs,method.getName(), isRemoveDash));
				}else if(types[0].toString().contains("boolean" ) || types[0].toString().contains("class java.lang.Boolean")){
					method.invoke(bean, getBoolean(rs,method.getName(), isRemoveDash));
				}else if(types[0].toString().contains("byte" ) || types[0].toString().contains("class [B")){
					method.invoke(bean, getByte(rs,method.getName(), isRemoveDash));
				}else{
					method.invoke(bean, getString(rs,method.getName(), isRemoveDash));
				}
			}
		}
	}
	
	private static String getString(ResultSet rs,String methodName, boolean isRemoveDash){
		try {
			String str = rs.getString(remove_key(methodName,"set", isRemoveDash));
			return StringUtils.isBlank(str) ? null : str;
		} catch (Exception e) { }
		return null;
	}
	
	private static int getInt(ResultSet rs,String methodName, boolean isRemoveDash){
		try {
			return rs.getInt(remove_key(methodName,"set", isRemoveDash));
		} catch (Exception e) { }
		return 0;
	}
	
	private static Long getLong(ResultSet rs,String methodName, boolean isRemoveDash){
		try {
			Long value = rs.getLong(remove_key(methodName,"set", isRemoveDash));
			return value == null ? null : value;
		} catch (Exception e) {	}
		return null;
	}

	private static Timestamp getTimestamp(ResultSet rs,String methodName, boolean isRemoveDash){
		try {
			return rs.getTimestamp(remove_key(methodName,"set", isRemoveDash));
		} catch (Exception e) {	}
		return null;
	}
	
	private static Double getDouble(ResultSet rs,String methodName, boolean isRemoveDash){
		try {
			return rs.getDouble(remove_key(methodName,"set", isRemoveDash));
		} catch (Exception e) {	}
		return null;
	}
	
	private static Boolean getBoolean(ResultSet rs,String methodName, boolean isRemoveDash){
		try {
			return rs.getBoolean(remove_key(methodName,"set", isRemoveDash));
		} catch (Exception e) {	}
		return null;
	}
	
	private static byte[] getByte(ResultSet rs,String methodName, boolean isRemoveDash){
		try {
			return rs.getBytes(remove_key(methodName,"set", isRemoveDash));
		} catch (Exception e) {	}
		return null;
	}

	private static String remove_key(String methodName,String key, boolean isRemoveDash){
		String name = StringUtils.removeStartIgnoreCase(methodName, key);  //String name=methodName.replace(key,StringUtils.EMPTY);
		String lowerCase=name.substring(0,1).toLowerCase();
		name=lowerCase+name.substring(1,name.length());
		if(isRemoveDash)
			name = name.replaceAll(name, "_");
		return name;
	}
}
