//,temp,sample_279.java,2,16,temp,sample_1347.java,2,16
//,3
public class xxx {
public void dummy_method(){
dataEntry = (ODataEntry) responseParts.get(3).getBody();
assertNotNull(dataEntry);
dataEntry = (ODataEntry) responseParts.get(4).getBody();
assertNotNull(dataEntry);
int statusCode = responseParts.get(5).getStatusCode();
assertEquals(HttpStatusCodes.NO_CONTENT.getStatusCode(), statusCode);
statusCode = responseParts.get(6).getStatusCode();
assertEquals(HttpStatusCodes.NO_CONTENT.getStatusCode(), statusCode);
statusCode = responseParts.get(7).getStatusCode();
assertEquals(HttpStatusCodes.NO_CONTENT.getStatusCode(), statusCode);


log.info("delete status");
}

};