//,temp,sample_278.java,2,16,temp,sample_1348.java,2,16
//,3
public class xxx {
public void dummy_method(){
ClientEntitySet entitySetWithTop = (ClientEntitySet)responseParts.get(3).getBody();
assertNotNull(entitySetWithTop);
assertEquals(5, entitySetWithTop.getEntities().size());
LOG.info("Read entities with $top=5: {}", entitySet.getEntities());
clientEntity = (ClientEntity)responseParts.get(4).getBody();
assertNotNull(clientEntity);
int statusCode = responseParts.get(5).getStatusCode();
assertEquals(HttpStatusCode.NO_CONTENT.getStatusCode(), statusCode);
statusCode = responseParts.get(6).getStatusCode();
assertEquals(HttpStatusCode.NO_CONTENT.getStatusCode(), statusCode);


log.info("delete entity status");
}

};