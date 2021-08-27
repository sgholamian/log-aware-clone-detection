//,temp,sample_5433.java,2,16,temp,sample_1437.java,2,16
//,3
public class xxx {
public void dummy_method(){
updateEntity = createEntity();
updateEntity.getProperties().add(objFactory.newPrimitiveProperty("MiddleName", objFactory.newPrimitiveValueBuilder().buildString("Middle Patched")));
olingoApp.patch(edm, TEST_CREATE_PEOPLE, null, updateEntity, statusHandler);
statusHandler.await();
entryHandler.reset();
olingoApp.read(edm, TEST_CREATE_PEOPLE, null, null, entryHandler);
ClientEntity updatedEntity = entryHandler.await();
statusHandler.reset();
olingoApp.delete(TEST_CREATE_PEOPLE, null, statusHandler);
HttpStatusCode statusCode = statusHandler.await();


log.info("deletion of entity was successful");
}

};