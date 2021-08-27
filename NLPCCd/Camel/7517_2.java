//,temp,sample_7743.java,2,16,temp,sample_7744.java,2,18
//,3
public class xxx {
public void dummy_method(){
String value = null;
int mode = propertiesComponent != null ? propertiesComponent.getSystemPropertiesMode() : PropertiesComponent.SYSTEM_PROPERTIES_MODE_OVERRIDE;
if (mode == PropertiesComponent.SYSTEM_PROPERTIES_MODE_OVERRIDE) {
value = System.getProperty(key);
if (value != null) {
}
}
if (value == null && properties != null) {
value = properties.getProperty(key);
if (value != null) {


log.info("found property with value to be used");
}
}
}

};