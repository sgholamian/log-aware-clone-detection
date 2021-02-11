//,temp,sample_6661.java,2,10,temp,sample_3638.java,2,11
//,3
public class xxx {
public void listStatusAclBit() throws URISyntaxException, IOException {
getMockServer().enqueue(new MockResponse().setResponseCode(200) .setBody(TestADLResponseData.getListFileStatusJSONResponse(true)));
FileStatus[] ls = null;
long startTime = Time.monotonicNow();
ls = getMockAdlFileSystem() .listStatus(new Path("/test1/test2"));
long endTime = Time.monotonicNow();


log.info("time");
}

};