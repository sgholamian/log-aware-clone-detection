//,temp,sample_1414.java,2,13,temp,sample_1413.java,2,11
//,3
public class xxx {
public void testServiceDocument() throws Exception {
final TestOlingo2ResponseHandler<ServiceDocument> responseHandler = new TestOlingo2ResponseHandler<ServiceDocument>();
olingoApp.read(null, "", null, null, responseHandler);
final ServiceDocument serviceDocument = responseHandler.await();
final List<Collection> collections = serviceDocument.getAtomInfo().getWorkspaces().get(0).getCollections();
assertEquals("Service Atom Collections", 3, collections.size());
final List<EdmEntitySetInfo> entitySetsInfo = serviceDocument.getEntitySetsInfo();
assertEquals("Service Entity Sets", 3, entitySetsInfo.size());


log.info("service document entries");
}

};