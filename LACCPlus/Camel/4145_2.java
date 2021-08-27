//,temp,Olingo2AppAPITest.java,90,109,temp,Olingo4AppAPITest.java,150,161
//,3
public class xxx {
    protected static void setupClient() throws Exception {
        olingoApp = new Olingo4AppImpl(getRealServiceUrl(TEST_SERVICE_BASE_URL));
        olingoApp.setContentType(TEST_FORMAT_STRING);

        LOG.info("Read Edm ");
        final TestOlingo4ResponseHandler<Edm> responseHandler = new TestOlingo4ResponseHandler<>();

        olingoApp.read(null, Constants.METADATA, null, null, responseHandler);

        edm = responseHandler.await();
        LOG.info("Read default EntityContainer:  {}", responseHandler.await().getEntityContainer().getName());
    }

};