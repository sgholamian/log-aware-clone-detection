//,temp,sample_3572.java,2,9,temp,sample_3576.java,2,9
//,2
public class xxx {
public void basicTest() throws IOException {
ByteArrayOutputStream out = new ByteArrayOutputStream();
PrintStream logFile = new PrintStream(out);
TestLogger logger = new TestLogger(logFile, TestLogger.LEVEL.INFO);


log.info("error");
}

};