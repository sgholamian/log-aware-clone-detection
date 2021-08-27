//,temp,sample_7738.java,2,21,temp,sample_7737.java,2,14
//,3
public class xxx {
private String getPropertyValue(String key, String input) {
if (propertiesComponent != null) {
for (PropertiesFunction function : propertiesComponent.getFunctions().values()) {
String token = function.getName() + ":";
if (key.startsWith(token)) {
String remainder = key.substring(token.length());


log.info("property with key is applied by function");
}
}
}
}

};