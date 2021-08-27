//,temp,sample_1421.java,2,14,temp,sample_1422.java,2,16
//,3
public class xxx {
public void testReadUnparsedEntry() throws Exception {
final TestOlingo2ResponseHandler<InputStream> responseHandler = new TestOlingo2ResponseHandler<InputStream>();
olingoApp.uread(edm, TEST_MANUFACTURER, null, null, responseHandler);
InputStream rawentry = responseHandler.await();
ODataEntry entry = EntityProvider.readEntry(TEST_FORMAT_STRING, edmEntitySetMap.get(MANUFACTURERS), rawentry, EntityProviderReadProperties.init().build());
responseHandler.reset();
olingoApp.uread(edm, TEST_CAR, null, null, responseHandler);
rawentry = responseHandler.await();
entry = EntityProvider.readEntry(TEST_FORMAT_STRING, edmEntitySetMap.get(CARS), rawentry, EntityProviderReadProperties.init().build());


log.info("single entry");
}

};