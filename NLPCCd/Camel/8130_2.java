//,temp,sample_5428.java,2,16,temp,sample_5429.java,2,16
//,3
public class xxx {
public void dummy_method(){
TestOlingo4ResponseHandler<HttpStatusCode> statusHandler = new TestOlingo4ResponseHandler<HttpStatusCode>();
ClientEntity clientEntity = objFactory.newEntity(null);
clientEntity.getProperties().add(objFactory.newPrimitiveProperty("MiddleName", objFactory.newPrimitiveValueBuilder().buildString("Middle")));
olingoApp.update(edm, TEST_PEOPLE, null, clientEntity, statusHandler);
HttpStatusCode statusCode = statusHandler.await();
assertEquals(HttpStatusCode.NO_CONTENT, statusCode);
final TestOlingo4ResponseHandler<ClientEntity> responseHandler = new TestOlingo4ResponseHandler<ClientEntity>();
olingoApp.read(edm, TEST_PEOPLE, null, null, responseHandler);
ClientEntity entity = responseHandler.await();
assertEquals("Middle", entity.getProperty("MiddleName").getValue().toString());


log.info("updated single entity");
}

};