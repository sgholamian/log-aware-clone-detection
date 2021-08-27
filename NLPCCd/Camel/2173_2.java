//,temp,sample_1141.java,2,12,temp,sample_1140.java,2,12
//,2
public class xxx {
public void testJavaVersion() throws Exception {
ExecResult body = templateJavaVersion.requestBody((Object)"test", ExecResult.class);
InputStream out = body.getStdout();
InputStream err = body.getStderr();
assertNull(out);
assertNotNull(err);
String outString = IOUtils.toString(err);


log.info("received stdout");
}

};