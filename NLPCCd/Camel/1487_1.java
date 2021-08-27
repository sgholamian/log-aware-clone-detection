//,temp,sample_5438.java,2,16,temp,sample_1346.java,2,16
//,3
public class xxx {
public void dummy_method(){
olingoApp.batch(edm, null, batchParts, responseHandler);
final List<Olingo4BatchResponse> responseParts = responseHandler.await(15, TimeUnit.MINUTES);
assertEquals("Batch responses expected", 8, responseParts.size());
assertNotNull(responseParts.get(0).getBody());
final ClientEntitySet clientEntitySet = (ClientEntitySet)responseParts.get(1).getBody();
assertNotNull(clientEntitySet);
ClientEntity returnClientEntity = (ClientEntity)responseParts.get(2).getBody();
assertNotNull(returnClientEntity);
returnClientEntity = (ClientEntity)responseParts.get(3).getBody();
assertNotNull(returnClientEntity);


log.info("batch read entity with expand");
}

};