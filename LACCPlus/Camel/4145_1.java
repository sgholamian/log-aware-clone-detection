//,temp,Olingo2AppAPITest.java,90,109,temp,Olingo4AppAPITest.java,150,161
//,3
public class xxx {
    protected static void setupClient() throws Exception {
        olingoApp = new Olingo2AppImpl(TEST_SERVICE_URL + "/");
        olingoApp.setContentType(TEST_FORMAT_STRING);

        LOG.info("Read Edm ");
        final TestOlingo2ResponseHandler<Edm> responseHandler = new TestOlingo2ResponseHandler<>();

        olingoApp.read(null, Olingo2AppImpl.METADATA, null, null, responseHandler);

        edm = responseHandler.await();
        LOG.info("Read default EntityContainer:  {}", responseHandler.await().getDefaultEntityContainer().getName());

        edmEntitySetMap = new HashMap<>();
        for (EdmEntitySet ees : edm.getEntitySets()) {
            edmEntitySetMap.put(ees.getName(), ees);
        }

        // wait for generated data to be registered in server
        Thread.sleep(2000);
    }

};