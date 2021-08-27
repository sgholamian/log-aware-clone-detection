//,temp,sample_1442.java,2,16,temp,sample_1344.java,2,16
//,3
public class xxx {
public void dummy_method(){
olingoApp.batch(edm, null, batchParts, responseHandler);
final List<Olingo2BatchResponse> responseParts = responseHandler.await(15, TimeUnit.MINUTES);
assertEquals("Batch responses expected", 8, responseParts.size());
assertNotNull(responseParts.get(0).getBody());
final ODataFeed feed = (ODataFeed) responseParts.get(1).getBody();
assertNotNull(feed);
ODataEntry dataEntry = (ODataEntry) responseParts.get(2).getBody();
assertNotNull(dataEntry);
dataEntry = (ODataEntry) responseParts.get(3).getBody();
assertNotNull(dataEntry);


log.info("batch read entry with expand");
}

};