//,temp,sample_1345.java,2,16,temp,sample_276.java,2,16
//,3
public class xxx {
public void dummy_method(){
ClientEntitySet entitySet = (ClientEntitySet)responseParts.get(1).getBody();
assertNotNull(entitySet);
clientEntity = (ClientEntity)responseParts.get(2).getBody();
assertNotNull(clientEntity);
ClientEntitySet entitySetWithTop = (ClientEntitySet)responseParts.get(3).getBody();
assertNotNull(entitySetWithTop);
assertEquals(5, entitySetWithTop.getEntities().size());
LOG.info("Read entities with $top=5: {}", entitySet.getEntities());
clientEntity = (ClientEntity)responseParts.get(4).getBody();
assertNotNull(clientEntity);


log.info("created entity");
}

};