//,temp,sample_5438.java,2,16,temp,sample_1346.java,2,16
//,3
public class xxx {
public void dummy_method(){
ODataEntry dataEntry = (ODataEntry) responseParts.get(2).getBody();
assertNotNull(dataEntry);
dataEntry = (ODataEntry) responseParts.get(3).getBody();
assertNotNull(dataEntry);
dataEntry = (ODataEntry) responseParts.get(4).getBody();
assertNotNull(dataEntry);
int statusCode = responseParts.get(5).getStatusCode();
assertEquals(HttpStatusCodes.NO_CONTENT.getStatusCode(), statusCode);
statusCode = responseParts.get(6).getStatusCode();
assertEquals(HttpStatusCodes.NO_CONTENT.getStatusCode(), statusCode);


log.info("update entry status");
}

};