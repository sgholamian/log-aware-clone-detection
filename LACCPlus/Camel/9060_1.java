//,temp,Olingo2AppAPITest.java,138,150,temp,Olingo4AppAPITest.java,208,219
//,3
public class xxx {
    @Test
    public void testReadUnparsedFeed() throws Exception {
        final TestOlingo2ResponseHandler<InputStream> responseHandler = new TestOlingo2ResponseHandler<>();

        olingoApp.uread(edm, MANUFACTURERS, null, null, responseHandler);

        final InputStream rawfeed = responseHandler.await();
        assertNotNull(rawfeed, "Data feed");
        // for this test, we just let EP to verify the stream data
        final ODataFeed dataFeed = EntityProvider.readFeed(TEST_FORMAT_STRING, edmEntitySetMap.get(MANUFACTURERS), rawfeed,
                EntityProviderReadProperties.init().build());
        LOG.info("Entries:  {}", prettyPrint(dataFeed));
    }

};