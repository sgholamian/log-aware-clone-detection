//,temp,sample_5431.java,2,9,temp,sample_1435.java,2,9
//,2
public class xxx {
public void testCreateUpdateDeleteEntity() throws Exception {
final TestOlingo4ResponseHandler<ClientEntity> entryHandler = new TestOlingo4ResponseHandler<ClientEntity>();
olingoApp.create(edm, PEOPLE, null, createEntity(), entryHandler);
ClientEntity createdEntity = entryHandler.await();


log.info("created entity");
}

};