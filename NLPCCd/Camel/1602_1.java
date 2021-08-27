//,temp,sample_1434.java,2,14,temp,sample_1433.java,2,12
//,3
public class xxx {
public void testReadCount() throws Exception {
final TestOlingo2ResponseHandler<Long> countHandler = new TestOlingo2ResponseHandler<Long>();
olingoApp.read(edm, MANUFACTURERS + COUNT_OPTION, null, null, countHandler);
countHandler.reset();
olingoApp.read(edm, TEST_MANUFACTURER + COUNT_OPTION, null, null, countHandler);
countHandler.reset();
olingoApp.read(edm, TEST_MANUFACTURER_LINKS_CARS + COUNT_OPTION, null, null, countHandler);
countHandler.reset();
olingoApp.read(edm, TEST_CAR_LINK_MANUFACTURER + COUNT_OPTION, null, null, countHandler);


log.info("manufacturer link count");
}

};