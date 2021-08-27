//,temp,Olingo4AppAPITest.java,314,322,temp,Olingo4AppAPITest.java,184,194
//,3
public class xxx {
    @Test
    public void testServiceDocument() throws Exception {
        final TestOlingo4ResponseHandler<ClientServiceDocument> responseHandler = new TestOlingo4ResponseHandler<>();

        olingoApp.read(null, "", null, null, responseHandler);
        final ClientServiceDocument serviceDocument = responseHandler.await();

        final Map<String, URI> entitySets = serviceDocument.getEntitySets();
        assertEquals(3, entitySets.size(), "Service Entity Sets");
        LOG.info("Service Document Entries:  {}", entitySets);
    }

};