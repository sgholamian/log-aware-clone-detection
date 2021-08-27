//,temp,sample_1439.java,2,17,temp,sample_5435.java,2,17
//,2
public class xxx {
public void dummy_method(){
ClientEntity updatedEntity = entryHandler.await();
statusHandler.reset();
olingoApp.delete(TEST_CREATE_PEOPLE, null, statusHandler);
HttpStatusCode statusCode = statusHandler.await();
try {
entryHandler.reset();
olingoApp.read(edm, TEST_CREATE_PEOPLE, null, null, entryHandler);
entryHandler.await();
fail("Entity not deleted!");
} catch (Exception e) {


log.info("deleted entity not found");
}
}

};