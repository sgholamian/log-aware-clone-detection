//,temp,sample_3637.java,2,16,temp,sample_3639.java,2,16
//,3
public class xxx {
public void dummy_method(){
for (int i = 0; i < 10; ++i) {
getMockServer().enqueue(new MockResponse().setResponseCode(500).setBody( TestADLResponseData.getErrorInternalServerExceptionJSONResponse()));
}
startTime = Time.monotonicNow();
try {
ls = getMockAdlFileSystem().listStatus(new Path("/test1/test2"));
} catch (IOException e) {
Assert.assertTrue(e.getMessage().contains("Internal Server Error"));
}
endTime = Time.monotonicNow();


log.info("time");
}

};