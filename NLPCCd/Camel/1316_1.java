//,temp,sample_5439.java,2,16,temp,sample_1443.java,2,16
//,3
public class xxx {
public void dummy_method(){
assertNotNull(responseParts.get(0).getBody());
final ClientEntitySet clientEntitySet = (ClientEntitySet)responseParts.get(1).getBody();
assertNotNull(clientEntitySet);
ClientEntity returnClientEntity = (ClientEntity)responseParts.get(2).getBody();
assertNotNull(returnClientEntity);
returnClientEntity = (ClientEntity)responseParts.get(3).getBody();
assertNotNull(returnClientEntity);
ClientEntity createdClientEntity = (ClientEntity)responseParts.get(4).getBody();
assertNotNull(createdClientEntity);
assertEquals(TEST_CREATE_RESOURCE_CONTENT_ID, responseParts.get(4).getContentId());


log.info("batch created entity");
}

};