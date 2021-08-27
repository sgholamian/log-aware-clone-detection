//,temp,sample_5421.java,2,14,temp,sample_5425.java,2,16
//,3
public class xxx {
public void testReadEntity() throws Exception {
final TestOlingo4ResponseHandler<ClientEntity> responseHandler = new TestOlingo4ResponseHandler<ClientEntity>();
olingoApp.read(edm, TEST_AIRLINE, null, null, responseHandler);
ClientEntity entity = responseHandler.await();
assertEquals("Shanghai Airline", entity.getProperty("Name").getValue().toString());
responseHandler.reset();
olingoApp.read(edm, TEST_PEOPLE, null, null, responseHandler);
entity = responseHandler.await();
assertEquals("Russell", entity.getProperty("FirstName").getValue().toString());


log.info("single entry");
}

};