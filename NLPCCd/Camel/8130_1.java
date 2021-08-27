//,temp,sample_5428.java,2,16,temp,sample_5429.java,2,16
//,3
public class xxx {
public void dummy_method(){
final TestOlingo4ResponseHandler<ClientPrimitiveValue> valueHandler = new TestOlingo4ResponseHandler<ClientPrimitiveValue>();
olingoApp.read(edm, TEST_AIRPORTS_SIMPLE_PROPERTY_VALUE, null, null, valueHandler);
ClientPrimitiveValue nameValue = valueHandler.await();
assertEquals("San Francisco International Airport", name.toString());
TestOlingo4ResponseHandler<HttpStatusCode> statusHandler = new TestOlingo4ResponseHandler<HttpStatusCode>();
ClientEntity clientEntity = objFactory.newEntity(null);
clientEntity.getProperties().add(objFactory.newPrimitiveProperty("MiddleName", objFactory.newPrimitiveValueBuilder().buildString("Middle")));
olingoApp.update(edm, TEST_PEOPLE, null, clientEntity, statusHandler);
HttpStatusCode statusCode = statusHandler.await();
assertEquals(HttpStatusCode.NO_CONTENT, statusCode);


log.info("name property updated with status");
}

};