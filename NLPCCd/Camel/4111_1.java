//,temp,sample_279.java,2,16,temp,sample_1347.java,2,16
//,3
public class xxx {
public void dummy_method(){
LOG.info("Read entities with $top=5: {}", entitySet.getEntities());
clientEntity = (ClientEntity)responseParts.get(4).getBody();
assertNotNull(clientEntity);
int statusCode = responseParts.get(5).getStatusCode();
assertEquals(HttpStatusCode.NO_CONTENT.getStatusCode(), statusCode);
statusCode = responseParts.get(6).getStatusCode();
assertEquals(HttpStatusCode.NO_CONTENT.getStatusCode(), statusCode);
assertEquals(HttpStatusCode.NOT_FOUND.getStatusCode(), responseParts.get(7).getStatusCode());
final ODataError error = (ODataError)responseParts.get(7).getBody();
assertNotNull(error);


log.info("read deleted entity error");
}

};