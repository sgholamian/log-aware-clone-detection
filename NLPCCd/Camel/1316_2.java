//,temp,sample_5439.java,2,16,temp,sample_1443.java,2,16
//,3
public class xxx {
public void dummy_method(){
assertEquals("Batch responses expected", 8, responseParts.size());
assertNotNull(responseParts.get(0).getBody());
final ODataFeed feed = (ODataFeed) responseParts.get(1).getBody();
assertNotNull(feed);
ODataEntry dataEntry = (ODataEntry) responseParts.get(2).getBody();
assertNotNull(dataEntry);
dataEntry = (ODataEntry) responseParts.get(3).getBody();
assertNotNull(dataEntry);
dataEntry = (ODataEntry) responseParts.get(4).getBody();
assertNotNull(dataEntry);


log.info("batch create entry");
}

};