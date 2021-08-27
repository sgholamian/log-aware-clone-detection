//,temp,sample_5430.java,2,10,temp,sample_1429.java,2,10
//,3
public class xxx {
public void testReadDeleteCreateLinks() throws Exception {
final TestOlingo2ResponseHandler<List<String>> linksHandler = new TestOlingo2ResponseHandler<List<String>>();
olingoApp.read(edm, TEST_MANUFACTURER_LINKS_CARS, null, null, linksHandler);
final List<String> links = linksHandler.await();
assertFalse(links.isEmpty());


log.info("read links");
}

};