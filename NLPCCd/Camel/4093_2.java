//,temp,sample_1424.java,2,12,temp,sample_5427.java,2,14
//,3
public class xxx {
public void testReadUpdateProperties() throws Exception {
final TestOlingo4ResponseHandler<ClientPrimitiveValue> propertyHandler = new TestOlingo4ResponseHandler<ClientPrimitiveValue>();
olingoApp.read(edm, TEST_AIRPORTS_SIMPLE_PROPERTY, null, null, propertyHandler);
ClientPrimitiveValue name = (ClientPrimitiveValue)propertyHandler.await();
assertEquals("San Francisco International Airport", name.toString());
final TestOlingo4ResponseHandler<ClientPrimitiveValue> valueHandler = new TestOlingo4ResponseHandler<ClientPrimitiveValue>();
olingoApp.read(edm, TEST_AIRPORTS_SIMPLE_PROPERTY_VALUE, null, null, valueHandler);
ClientPrimitiveValue nameValue = valueHandler.await();
assertEquals("San Francisco International Airport", name.toString());


log.info("airport name property value");
}

};