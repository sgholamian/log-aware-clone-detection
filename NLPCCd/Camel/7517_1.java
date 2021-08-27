//,temp,sample_7743.java,2,16,temp,sample_7744.java,2,18
//,3
public class xxx {
private String doGetPropertyValue(String key) {
if (ObjectHelper.isEmpty(key)) {
return parseProperty(key, null, properties);
}
String value = null;
int mode = propertiesComponent != null ? propertiesComponent.getSystemPropertiesMode() : PropertiesComponent.SYSTEM_PROPERTIES_MODE_OVERRIDE;
if (mode == PropertiesComponent.SYSTEM_PROPERTIES_MODE_OVERRIDE) {
value = System.getProperty(key);
if (value != null) {


log.info("found a jvm system property with value to be used");
}
}
}

};