package my.com.todolist;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;


public class JsonUtils {
	
	public static String convertObjectToJson(Object obj, boolean removeNull){
		System.err.println("Start convertObjectToJson funct");
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES , false);
			if(removeNull){
				mapper.setSerializationInclusion(Inclusion.NON_NULL);
				mapper.setSerializationInclusion(Inclusion.NON_EMPTY);
			}
			return mapper.writeValueAsString(obj);			
		} catch (Exception e) {
			ErrorsHandler.printError(JsonUtils.class, e.getMessage(), e);
		}
		System.err.println("End convertObjectToJson funct");
		return null;
	}

	public static Object convertJsonStringToObject(String jsonString, Class<?> objectClass, Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JsonNode rootNode = objectMapper.readTree(jsonString);
            if (rootNode.isArray()) {
                List<Object> listobject = new ArrayList<Object>();
                for (JsonNode node : rootNode) {
                	object = convertJsonStringToObject(node.toString(), objectClass, object.getClass().newInstance());
                	listobject.add(object);
                }
                return listobject;
            } else
                return objectMapper.treeToValue(rootNode, objectClass);
        } catch (Exception e) {
        	ErrorsHandler.printError(JsonUtils.class, e.getMessage(), e);
        }
        return null;
    }
	
	public static Map<String, Object> convertJsonStringToMap(String jsonString){
		try {
			ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return objectMapper.readValue(jsonString, new TypeReference<Map<String,Object>>(){});
		} catch (Exception e) {
			ErrorsHandler.printError(JsonUtils.class, e.getMessage(), e);
		}
		return null;
	}
	
	public static String convertMapToJsonString(Map<?, ?> map){
		try {
			StringWriter stringWriter = new StringWriter();
			new ObjectMapper().writeValue(stringWriter, map);
			return stringWriter.toString();
		} catch (Exception e) {
			ErrorsHandler.printError(JsonUtils.class, e.getMessage(), e);
		}
		return null;		
	}
}
