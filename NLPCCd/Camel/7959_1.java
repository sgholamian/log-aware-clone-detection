//,temp,sample_7738.java,2,21,temp,sample_7737.java,2,14
//,3
public class xxx {
public void dummy_method(){
if (propertiesComponent != null) {
for (PropertiesFunction function : propertiesComponent.getFunctions().values()) {
String token = function.getName() + ":";
if (key.startsWith(token)) {
String remainder = key.substring(token.length());
String value = function.apply(remainder);
if (value == null) {
throw new IllegalArgumentException("Property with key [" + key + "] using function [" + function.getName() + "]" + " returned null value which is not allowed, from input: " + input);
} else {
if (log.isDebugEnabled()) {


log.info("property with key applied by function");
}
}
}
}
}
}

};