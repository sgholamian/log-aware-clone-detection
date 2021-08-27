//,temp,sample_1412.java,2,11,temp,sample_5416.java,2,11
//,3
public class xxx {
protected static void setupClient() throws Exception {
olingoApp = new Olingo4AppImpl(getRealServiceUrl(TEST_SERVICE_BASE_URL));
olingoApp.setContentType(TEST_FORMAT_STRING);
final TestOlingo4ResponseHandler<Edm> responseHandler = new TestOlingo4ResponseHandler<Edm>();
olingoApp.read(null, Constants.METADATA, null, null, responseHandler);
edm = responseHandler.await();


log.info("read default entitycontainer");
}

};