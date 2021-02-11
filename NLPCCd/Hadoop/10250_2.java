//,temp,sample_3637.java,2,16,temp,sample_3639.java,2,16
//,3
public class xxx {
public void dummy_method(){
long endTime = Time.monotonicNow();
for (int i = 0; i < ls.length; i++) {
Assert.assertTrue(ls[i].isDirectory());
Assert.assertEquals(true, ls[i].getPermission().getAclBit());
}
ls = null;
getMockServer().enqueue(new MockResponse().setResponseCode(200) .setBody(TestADLResponseData.getListFileStatusJSONResponse(false)));
startTime = Time.monotonicNow();
ls = getMockAdlFileSystem() .listStatus(new Path("/test1/test2"));
endTime = Time.monotonicNow();


log.info("time");
}

};