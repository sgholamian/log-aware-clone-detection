//,temp,sample_3609.java,2,15,temp,sample_3606.java,2,12
//,3
public class xxx {
public static synchronized void halt(HaltException ee) throws HaltException {
int status = ee.getExitCode();
String msg = ee.getMessage();
try {
if (status != 0) {
}
} catch (Exception ignored) {
}
if (systemHaltDisabled) {


log.info("halt called");
}
}

};