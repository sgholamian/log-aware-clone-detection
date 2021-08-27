//,temp,sample_1424.java,2,12,temp,sample_5427.java,2,14
//,3
public class xxx {
public void testReadUpdateProperties() throws Exception {
final TestOlingo2ResponseHandler<Map<String, Object>> propertyHandler = new TestOlingo2ResponseHandler<Map<String, Object>>();
olingoApp.read(edm, TEST_MANUFACTURER_FOUNDED_PROPERTY, null, null, propertyHandler);
Calendar founded = (Calendar) propertyHandler.await().get(FOUNDED_PROPERTY);
final TestOlingo2ResponseHandler<Calendar> valueHandler = new TestOlingo2ResponseHandler<Calendar>();
olingoApp.read(edm, TEST_MANUFACTURER_FOUNDED_VALUE, null, null, valueHandler);
founded = valueHandler.await();


log.info("founded property");
}

};