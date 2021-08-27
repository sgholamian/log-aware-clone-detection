//,temp,sample_1418.java,2,12,temp,sample_1432.java,2,10
//,3
public class xxx {
public void testReadEntry() throws Exception {
final TestOlingo2ResponseHandler<ODataEntry> responseHandler = new TestOlingo2ResponseHandler<ODataEntry>();
olingoApp.read(edm, TEST_MANUFACTURER, null, null, responseHandler);
ODataEntry entry = responseHandler.await();
responseHandler.reset();
olingoApp.read(edm, TEST_CAR, null, null, responseHandler);
entry = responseHandler.await();


log.info("single entry");
}

};