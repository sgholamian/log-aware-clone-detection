//,temp,sample_5418.java,2,11,temp,sample_5417.java,2,11
//,3
public class xxx {
public void testServiceDocument() throws Exception {
final TestOlingo4ResponseHandler<ClientServiceDocument> responseHandler = new TestOlingo4ResponseHandler<ClientServiceDocument>();
olingoApp.read(null, "", null, null, responseHandler);
final ClientServiceDocument serviceDocument = responseHandler.await();
final Map<String, URI> entitySets = serviceDocument.getEntitySets();
assertEquals("Service Entity Sets", 4, entitySets.size());


log.info("service document entries");
}

};