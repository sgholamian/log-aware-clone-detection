//,temp,sample_6661.java,2,10,temp,sample_3638.java,2,11
//,3
public class xxx {
public void getFileStatusAclBit() throws URISyntaxException, IOException {
getMockServer().enqueue(new MockResponse().setResponseCode(200) .setBody(TestADLResponseData.getGetFileStatusJSONResponse(true)));
long startTime = Time.monotonicNow();
FileStatus fileStatus = getMockAdlFileSystem() .getFileStatus(new Path("/test1/test2"));
long endTime = Time.monotonicNow();


log.info("time");
}

};