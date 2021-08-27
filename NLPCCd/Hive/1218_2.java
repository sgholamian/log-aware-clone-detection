//,temp,sample_1907.java,2,11,temp,sample_1908.java,2,10
//,3
public class xxx {
private static void run(String cmd, IDriver myDriver) throws RuntimeException {
try {
run(cmd,false, myDriver);
} catch (AssertionError ae){


log.info("assertionerror");
}
}

};