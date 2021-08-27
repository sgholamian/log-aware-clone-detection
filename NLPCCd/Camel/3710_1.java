//,temp,sample_1423.java,2,9,temp,sample_5426.java,2,10
//,3
public class xxx {
public void testReadUpdateProperties() throws Exception {
final TestOlingo2ResponseHandler<Map<String, Object>> propertyHandler = new TestOlingo2ResponseHandler<Map<String, Object>>();
olingoApp.read(edm, TEST_MANUFACTURER_FOUNDED_PROPERTY, null, null, propertyHandler);
Calendar founded = (Calendar) propertyHandler.await().get(FOUNDED_PROPERTY);


log.info("founded property");
}

};