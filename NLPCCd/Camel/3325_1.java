//,temp,sample_7720.java,2,12,temp,sample_1445.java,2,11
//,3
public class xxx {
public static String getSystemProperty(String name, String defaultValue) {
try {
return System.getProperty(name, defaultValue);
} catch (Exception e) {
if (LOG.isDebugEnabled()) {


log.info("caught security exception accessing system property will use default value");
}
}
}

};