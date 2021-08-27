//,temp,sample_1420.java,2,10,temp,sample_1416.java,2,11
//,3
public class xxx {
public void testReadUnparsedFeed() throws Exception {
final TestOlingo2ResponseHandler<InputStream> responseHandler = new TestOlingo2ResponseHandler<InputStream>();
olingoApp.uread(edm, MANUFACTURERS, null, null, responseHandler);
final InputStream rawfeed = responseHandler.await();
assertNotNull("Data feed", rawfeed);
final ODataFeed dataFeed = EntityProvider.readFeed(TEST_FORMAT_STRING, edmEntitySetMap.get(MANUFACTURERS), rawfeed, EntityProviderReadProperties.init().build());


log.info("entries");
}

};