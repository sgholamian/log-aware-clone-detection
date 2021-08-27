//,temp,sample_1345.java,2,16,temp,sample_276.java,2,16
//,3
public class xxx {
public void dummy_method(){
final ODataFeed feed = (ODataFeed) responseParts.get(1).getBody();
assertNotNull(feed);
ODataEntry dataEntry = (ODataEntry) responseParts.get(2).getBody();
assertNotNull(dataEntry);
dataEntry = (ODataEntry) responseParts.get(3).getBody();
assertNotNull(dataEntry);
dataEntry = (ODataEntry) responseParts.get(4).getBody();
assertNotNull(dataEntry);
int statusCode = responseParts.get(5).getStatusCode();
assertEquals(HttpStatusCodes.NO_CONTENT.getStatusCode(), statusCode);


log.info("update address status");
}

};