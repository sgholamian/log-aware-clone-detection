//,temp,sample_3574.java,2,9,temp,sample_3575.java,2,9
//,2
public class xxx {
public void basicTest() throws IOException {
ByteArrayOutputStream out = new ByteArrayOutputStream();
PrintStream logFile = new PrintStream(out);
TestLogger logger = new TestLogger(logFile, TestLogger.LEVEL.INFO);


log.info("info");
}

};