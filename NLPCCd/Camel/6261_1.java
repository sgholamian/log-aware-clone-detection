//,temp,sample_1420.java,2,10,temp,sample_1416.java,2,11
//,3
public class xxx {
public void testReadUnparsedEntry() throws Exception {
final TestOlingo2ResponseHandler<InputStream> responseHandler = new TestOlingo2ResponseHandler<InputStream>();
olingoApp.uread(edm, TEST_MANUFACTURER, null, null, responseHandler);
InputStream rawentry = responseHandler.await();
ODataEntry entry = EntityProvider.readEntry(TEST_FORMAT_STRING, edmEntitySetMap.get(MANUFACTURERS), rawentry, EntityProviderReadProperties.init().build());


log.info("single entry");
}

};