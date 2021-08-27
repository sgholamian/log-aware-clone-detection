//,temp,sample_4422.java,2,9,temp,sample_3391.java,2,10
//,3
public class xxx {
private boolean loadOneClasspathConfig(String fname) {
URL x = getResource(fname);
if (x != null) {
addResource(x);


log.info("loaded config from classpath");
}
}

};