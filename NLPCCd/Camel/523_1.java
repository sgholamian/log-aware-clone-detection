//,temp,sample_1415.java,2,10,temp,sample_1417.java,2,9
//,3
public class xxx {
public void testReadFeed() throws Exception {
final TestOlingo2ResponseHandler<ODataFeed> responseHandler = new TestOlingo2ResponseHandler<ODataFeed>();
olingoApp.read(edm, MANUFACTURERS, null, null, responseHandler);
final ODataFeed dataFeed = responseHandler.await();
assertNotNull("Data feed", dataFeed);


log.info("entries");
}

};