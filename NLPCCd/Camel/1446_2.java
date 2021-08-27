//,temp,sample_1442.java,2,16,temp,sample_1344.java,2,16
//,3
public class xxx {
public void dummy_method(){
final Edm edm = (Edm) responseParts.get(0).getBody();
assertNotNull(edm);
final ODataFeed feed = (ODataFeed) responseParts.get(1).getBody();
assertNotNull(feed);
ODataEntry dataEntry = (ODataEntry) responseParts.get(2).getBody();
assertNotNull(dataEntry);
dataEntry = (ODataEntry) responseParts.get(3).getBody();
assertNotNull(dataEntry);
dataEntry = (ODataEntry) responseParts.get(4).getBody();
assertNotNull(dataEntry);


log.info("created entry");
}

};