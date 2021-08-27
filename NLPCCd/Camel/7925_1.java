//,temp,sample_5420.java,2,10,temp,sample_5423.java,2,12
//,3
public class xxx {
public void testReadEntity() throws Exception {
final TestOlingo4ResponseHandler<ClientEntity> responseHandler = new TestOlingo4ResponseHandler<ClientEntity>();
olingoApp.read(edm, TEST_AIRLINE, null, null, responseHandler);
ClientEntity entity = responseHandler.await();
assertEquals("Shanghai Airline", entity.getProperty("Name").getValue().toString());


log.info("single entity");
}

};