//,temp,sample_1412.java,2,11,temp,sample_5416.java,2,11
//,3
public class xxx {
protected static void setupClient() throws Exception {
olingoApp = new Olingo2AppImpl(TEST_SERVICE_URL + "/");
olingoApp.setContentType(TEST_FORMAT_STRING);
final TestOlingo2ResponseHandler<Edm> responseHandler = new TestOlingo2ResponseHandler<Edm>();
olingoApp.read(null, Olingo2AppImpl.METADATA, null, null, responseHandler);
edm = responseHandler.await();


log.info("read default entitycontainer");
}

};