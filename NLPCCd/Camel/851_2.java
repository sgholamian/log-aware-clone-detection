//,temp,sample_277.java,2,16,temp,sample_1444.java,2,16
//,3
public class xxx {
public void dummy_method(){
assertNotNull(dataEntry);
dataEntry = (ODataEntry) responseParts.get(3).getBody();
assertNotNull(dataEntry);
dataEntry = (ODataEntry) responseParts.get(4).getBody();
assertNotNull(dataEntry);
assertEquals(HttpStatusCodes.NO_CONTENT.getStatusCode(), responseParts.get(5).getStatusCode());
assertEquals(HttpStatusCodes.NO_CONTENT.getStatusCode(), responseParts.get(6).getStatusCode());
assertEquals(HttpStatusCodes.NOT_FOUND.getStatusCode(), responseParts.get(7).getStatusCode());
final Exception exception = (Exception) responseParts.get(7).getBody();
assertNotNull(exception);


log.info("batch retrieve deleted entry");
}

};