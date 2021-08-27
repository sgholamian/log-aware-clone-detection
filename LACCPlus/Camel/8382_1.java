//,temp,Olingo2AppAPITest.java,127,136,temp,Olingo4AppAPITest.java,196,206
//,3
public class xxx {
    @Test
    public void testReadFeed() throws Exception {
        final TestOlingo2ResponseHandler<ODataFeed> responseHandler = new TestOlingo2ResponseHandler<>();

        olingoApp.read(edm, MANUFACTURERS, null, null, responseHandler);

        final ODataFeed dataFeed = responseHandler.await();
        assertNotNull(dataFeed, "Data feed");
        LOG.info("Entries:  {}", prettyPrint(dataFeed));
    }

};