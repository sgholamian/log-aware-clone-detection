//,temp,sample_1031.java,2,16,temp,sample_3681.java,2,16
//,3
public class xxx {
public void dummy_method(){
if (currentProperties == null) {
currentProperties = newProps;
}
for (Enumeration<String> ek = currentProperties.keys(); ek.hasMoreElements();) {
String k = ek.nextElement();
newProps.put(k, currentProperties.get(k));
}
for (String p : ((Properties) props).stringPropertyNames()) {
newProps.put(p, ((Properties) props).getProperty(p));
}


log.info("updating configadmin by overriding properties");
}

};