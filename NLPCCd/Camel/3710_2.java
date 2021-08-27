//,temp,sample_1423.java,2,9,temp,sample_5426.java,2,10
//,3
public class xxx {
public void testReadUpdateProperties() throws Exception {
final TestOlingo4ResponseHandler<ClientPrimitiveValue> propertyHandler = new TestOlingo4ResponseHandler<ClientPrimitiveValue>();
olingoApp.read(edm, TEST_AIRPORTS_SIMPLE_PROPERTY, null, null, propertyHandler);
ClientPrimitiveValue name = (ClientPrimitiveValue)propertyHandler.await();
assertEquals("San Francisco International Airport", name.toString());


log.info("airport name property value");
}

};