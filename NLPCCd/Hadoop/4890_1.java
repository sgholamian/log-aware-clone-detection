//,temp,sample_6660.java,2,10,temp,sample_3633.java,2,10
//,3
public class xxx {
public void getFileStatusReturnsAsExpected() throws URISyntaxException, IOException {
getMockServer().enqueue(new MockResponse().setResponseCode(200) .setBody(TestADLResponseData.getGetFileStatusJSONResponse()));
long startTime = Time.monotonicNow();
FileStatus fileStatus = getMockAdlFileSystem() .getFileStatus(new Path("/test1/test2"));
long endTime = Time.monotonicNow();


log.info("time");
}

};