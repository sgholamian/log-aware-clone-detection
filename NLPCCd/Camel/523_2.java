//,temp,sample_1415.java,2,10,temp,sample_1417.java,2,9
//,3
public class xxx {
public void testReadEntry() throws Exception {
final TestOlingo2ResponseHandler<ODataEntry> responseHandler = new TestOlingo2ResponseHandler<ODataEntry>();
olingoApp.read(edm, TEST_MANUFACTURER, null, null, responseHandler);
ODataEntry entry = responseHandler.await();


log.info("single entry");
}

};