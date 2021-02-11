//,temp,sample_6660.java,2,10,temp,sample_3633.java,2,10
//,3
public class xxx {
public void listStatusReturnsAsExpected() throws IOException {
getMockServer().enqueue(new MockResponse().setResponseCode(200) .setBody(TestADLResponseData.getListFileStatusJSONResponse(10)));
long startTime = Time.monotonicNow();
FileStatus[] ls = getMockAdlFileSystem() .listStatus(new Path("/test1/test2"));
long endTime = Time.monotonicNow();


log.info("time");
}

};