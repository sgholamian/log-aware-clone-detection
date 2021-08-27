//,temp,sample_8498.java,2,13,temp,sample_7619.java,2,13
//,2
public class xxx {
public boolean canTest() {
if (System.getProperty("java.vendor").contains("IBM")) {
return false;
}
try {
javax.security.auth.login.Configuration.getConfiguration();
} catch (Exception e) {


log.info("cannot run test due security exception");
}
}

};