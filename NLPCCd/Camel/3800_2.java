//,temp,sample_1418.java,2,12,temp,sample_1432.java,2,10
//,3
public class xxx {
public void testReadCount() throws Exception {
final TestOlingo2ResponseHandler<Long> countHandler = new TestOlingo2ResponseHandler<Long>();
olingoApp.read(edm, MANUFACTURERS + COUNT_OPTION, null, null, countHandler);
countHandler.reset();
olingoApp.read(edm, TEST_MANUFACTURER + COUNT_OPTION, null, null, countHandler);


log.info("manufacturer count");
}

};