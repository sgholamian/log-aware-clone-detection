//,temp,sample_278.java,2,16,temp,sample_1348.java,2,16
//,3
public class xxx {
public void dummy_method(){
assertNotNull(dataEntry);
int statusCode = responseParts.get(5).getStatusCode();
assertEquals(HttpStatusCodes.NO_CONTENT.getStatusCode(), statusCode);
statusCode = responseParts.get(6).getStatusCode();
assertEquals(HttpStatusCodes.NO_CONTENT.getStatusCode(), statusCode);
statusCode = responseParts.get(7).getStatusCode();
assertEquals(HttpStatusCodes.NO_CONTENT.getStatusCode(), statusCode);
assertEquals(HttpStatusCodes.NOT_FOUND.getStatusCode(), responseParts.get(8).getStatusCode());
final Exception exception = (Exception) responseParts.get(8).getBody();
assertNotNull(exception);


log.info("read deleted entry exception");
}

};