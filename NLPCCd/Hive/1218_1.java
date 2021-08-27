//,temp,sample_1907.java,2,11,temp,sample_1908.java,2,10
//,3
public class xxx {
private void verifyFail(String cmd, IDriver myDriver) throws RuntimeException {
boolean success = false;
try {
success = run(cmd, false, myDriver);
} catch (AssertionError ae){


log.info("assertionerror");
}
}

};