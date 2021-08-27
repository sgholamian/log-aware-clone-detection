//,temp,sample_5430.java,2,10,temp,sample_1429.java,2,10
//,3
public class xxx {
public void testReadCount() throws Exception {
final TestOlingo4ResponseHandler<Long> countHandler = new TestOlingo4ResponseHandler<Long>();
olingoApp.read(edm, PEOPLE + COUNT_OPTION, null, null, countHandler);
Long count = countHandler.await();
assertEquals(20, count.intValue());


log.info("people count");
}

};