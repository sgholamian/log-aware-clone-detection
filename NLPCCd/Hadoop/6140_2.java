//,temp,sample_3609.java,2,15,temp,sample_3606.java,2,12
//,3
public class xxx {
public static synchronized void terminate(ExitException ee) throws ExitException {
int status = ee.getExitCode();
String msg = ee.getMessage();
if (status != 0) {
}
if (systemExitDisabled) {


log.info("terminate called");
}
}

};