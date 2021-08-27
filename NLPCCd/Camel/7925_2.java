//,temp,sample_5420.java,2,10,temp,sample_5423.java,2,12
//,3
public class xxx {
public void testReadUnparsedEntity() throws Exception {
final TestOlingo4ResponseHandler<InputStream> responseHandler = new TestOlingo4ResponseHandler<InputStream>();
olingoApp.uread(edm, TEST_AIRLINE, null, null, responseHandler);
InputStream rawEntity = responseHandler.await();
assertNotNull("Data entity", rawEntity);
ClientEntity entity = reader.readEntity(rawEntity, TEST_FORMAT);
assertEquals("Shanghai Airline", entity.getProperty("Name").getValue().toString());


log.info("single entity");
}

};