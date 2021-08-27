//,temp,sample_5437.java,2,16,temp,sample_1440.java,2,16
//,3
public class xxx {
public void dummy_method(){
batchParts.add(Olingo2BatchChangeRequest.resourcePath(TEST_RESOURCE).operation(Operation.UPDATE) .body(updateData).build());
batchParts.add(Olingo2BatchChangeRequest.resourcePath(TEST_RESOURCE).operation(Operation.DELETE).build());
final TestOlingo2ResponseHandler<List<Olingo2BatchResponse>> responseHandler = new TestOlingo2ResponseHandler<List<Olingo2BatchResponse>>();
batchParts.add(Olingo2BatchQueryRequest.resourcePath(TEST_CREATE_MANUFACTURER).build());
olingoApp.batch(edm, null, batchParts, responseHandler);
final List<Olingo2BatchResponse> responseParts = responseHandler.await(15, TimeUnit.MINUTES);
assertEquals("Batch responses expected", 8, responseParts.size());
assertNotNull(responseParts.get(0).getBody());
final ODataFeed feed = (ODataFeed) responseParts.get(1).getBody();
assertNotNull(feed);


log.info("batch feed");
}

};