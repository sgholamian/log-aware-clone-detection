//,temp,sample_1436.java,2,16,temp,sample_1426.java,2,16
//,3
public class xxx {
public void dummy_method(){
Calendar founded = (Calendar) propertyHandler.await().get(FOUNDED_PROPERTY);
final TestOlingo2ResponseHandler<Calendar> valueHandler = new TestOlingo2ResponseHandler<Calendar>();
olingoApp.read(edm, TEST_MANUFACTURER_FOUNDED_VALUE, null, null, valueHandler);
founded = valueHandler.await();
final TestOlingo2ResponseHandler<HttpStatusCodes> statusHandler = new TestOlingo2ResponseHandler<HttpStatusCodes>();
final HashMap<String, Object> properties = new HashMap<String, Object>();
properties.put(FOUNDED_PROPERTY, new Date());
olingoApp.update(edm, TEST_MANUFACTURER_FOUNDED_PROPERTY, null, new Date(), statusHandler);
statusHandler.reset();
olingoApp.update(edm, TEST_MANUFACTURER_FOUNDED_VALUE, null, new Date(), statusHandler);


log.info("founded property updated with status");
}

};