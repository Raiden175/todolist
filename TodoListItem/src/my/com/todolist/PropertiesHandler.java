package my.com.todolist;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

public final class PropertiesHandler {

    private String postgresdriver;
    private String postgresurl;
    private String postgresuser;
    private String postrespassword;
    private boolean isnewprops = false;
    private static final String comment = "TODO PROPERTIES UPDATED - " + DateUtils.getCurrentDate(DateUtils.Format6);
    private static final String propfilename = "/usr/local/tomcat/webapps/todo.properties";
    private static final boolean isxmlfile = false;

    public PropertiesHandler() {
    }

    public PropertiesHandler(boolean isRead) {
    	System.err.println("propfilename = " + propfilename);
        if (isRead) {
            Properties props = PropertiesHandler.getSortedProperties();
            readProperties(props);
        }
    }

    public static Properties getSortedProperties() {
        Properties tmp = new Properties() {
			private static final long serialVersionUID = 3124071177104039491L;

			@Override
            public Set<Object> keySet() {
                return new TreeSet<Object>(super.keySet());
            }

            @Override
            public Set<String> stringPropertyNames() {
                TreeSet<String> set = new TreeSet<String>();
                for (Object o : keySet()) {
                    set.add((String) o);
                }
                return set;
            }

            @Override
            public synchronized Enumeration<Object> keys() {
                return Collections.enumeration(new TreeSet<Object>(super.keySet()));
            }
        };
        return tmp;
    }

    public static Properties LoadProperties(Properties props, String filename, boolean isxml) throws InvalidPropertiesFormatException, IOException {
        InputStream in = null;
        try {			
	        if (isxml) {
	            in = new FileInputStream(filename + ".xml");
	            props.loadFromXML(in);
	        } else {
	            in = new FileInputStream(filename);
	            props.load(in);
	        }
	        return props;
		} catch (Exception ex) {
			ErrorsHandler.printError(PropertiesHandler.class, ex.getMessage(), ex);
		} finally {
			try {				
				in.close();
			} catch (Exception e) {	}
		}
        return null;
    }

    public static void writeProperties(Properties props, String filename, String comment, boolean isxml) {
    	FileOutputStream file = null;
        try {
            Properties tmp = PropertiesHandler.getSortedProperties();
            tmp.putAll(props);
            if (isxml) {
            	file = new FileOutputStream(filename + ".xml");
                tmp.storeToXML(file, comment);
            } else {
            	file = new FileOutputStream(filename);
                tmp.store(file, comment);
            }
            file.flush();
        } catch (IOException ex) {
            ErrorsHandler.printError(PropertiesHandler.class, ex.getMessage(), ex);
        } finally {
			try {
				file.close();
			} catch (IOException e) {
			}
        }
    }

    public static void printPropertiesValue(Properties props) {
        Enumeration<?> em = props.keys();    //this.props.propertyNames(); props.list(System.out);
        String propname;
        while (em.hasMoreElements()) {
            propname = em.nextElement().toString();
            System.out.println(propname.toUpperCase() + " = " + props.getProperty(propname));
        }
    }

    public Properties readProperties(Properties props) {
        this.isnewprops = false;
        try {
            PropertiesHandler.LoadProperties(props, propfilename, PropertiesHandler.isxmlfile);
            this.postgresdriver = checkDefaultValue(props, props.getProperty("jdbc.postgresql.driver"), "jdbc.postgresql.driver");
            this.postgresurl = checkDefaultValue(props, props.getProperty("jdbc.postgresql.URL"), "jdbc.postgresql.URL");
            this.postgresuser = checkDefaultValue(props, props.getProperty("jdbc.postgresql.user"), "jdbc.postgresql.user");
            this.postrespassword = checkDefaultValue(props, props.getProperty("jdbc.postgresql.password"), "jdbc.postgresql.password");
            if (this.isnewprops)
                PropertiesHandler.writeProperties(props, propfilename, PropertiesHandler.comment, PropertiesHandler.isxmlfile);
        } catch (IOException ex) {
            ErrorsHandler.printError(PropertiesHandler.class, ex.getMessage(), ex);
            PropertiesHandler.writeProperties(props, propfilename, PropertiesHandler.comment, PropertiesHandler.isxmlfile);
        }
        return props;
    }

    private String checkDefaultValue(Properties props, String value, String propname) throws IOException {
        if (StringUtils.trimToNull(value) == null) {
            this.isnewprops = true;
            ErrorsHandler.printData2Param(PropertiesHandler.class, propname, value, false);
            if (propname.equalsIgnoreCase("jdbc.postgresql.driver"))
                props.setProperty(propname, "org.postgresql.Driver");
            if (propname.equalsIgnoreCase("jdbc.postgresql.URL"))
                props.setProperty(propname, "jdbc:postgresql://127.0.0.1:5432/todo");
            if (propname.equalsIgnoreCase("jdbc.postgresql.user"))
                props.setProperty(propname, "admin");
            if (propname.equalsIgnoreCase("jdbc.postgresql.password"))
                props.setProperty(propname, "admin");
        }
        return value;
    }
    
    public Properties getProperties(String propertiesFilename) {
    	Properties props = new Properties();
    	try {
        	props = PropertiesHandler.getSortedProperties();
    		props = PropertiesHandler.LoadProperties(props, propertiesFilename, false);
    		if(props == null){
    			props = PropertiesHandler.getSortedProperties();
    			PropertiesHandler.writeProperties(props, propertiesFilename, propertiesFilename, false);
    		}
		} catch (Exception ex) {
			ErrorsHandler.printError(PropertiesHandler.class, ex.getMessage(), ex);
            PropertiesHandler.writeProperties(props, propertiesFilename, propertiesFilename, false);
		}
        return props;
    }
    

    public String getDriverClass() {
        return this.postgresdriver;
    }

    public String getDatabaseURL() {
        return this.postgresurl;
    }

    public String getDatabaseUser() {
        return this.postgresuser;
    }

    public String getDatabasePassword() {
        return this.postrespassword;
    }
    
}
