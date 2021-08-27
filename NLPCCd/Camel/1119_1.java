//,temp,sample_5418.java,2,11,temp,sample_5417.java,2,11
//,3
public class xxx {
public void testReadEntitySet() throws Exception {
final TestOlingo4ResponseHandler<ClientEntitySet> responseHandler = new TestOlingo4ResponseHandler<ClientEntitySet>();
olingoApp.read(edm, PEOPLE, null, null, responseHandler);
final ClientEntitySet entitySet = responseHandler.await();
assertNotNull(entitySet);
assertEquals("Entity set count", 20, entitySet.getEntities().size());


log.info("entities");
}

};