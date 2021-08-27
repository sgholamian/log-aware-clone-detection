//,temp,Olingo4AppAPITest.java,246,274,temp,Olingo4AppAPITest.java,221,244
//,3
public class xxx {
    @Test
    public void testReadUnparsedEntity() throws Exception {
        final TestOlingo4ResponseHandler<InputStream> responseHandler = new TestOlingo4ResponseHandler<>();

        olingoApp.uread(edm, TEST_AIRLINE, null, null, responseHandler);
        InputStream rawEntity = responseHandler.await();
        assertNotNull(rawEntity, "Data entity");
        ClientEntity entity = reader.readEntity(rawEntity, TEST_FORMAT);
        assertEquals("Shanghai Airline", entity.getProperty("Name").getValue().toString());
        LOG.info("Single Entity:  {}", prettyPrint(entity));

        responseHandler.reset();

        olingoApp.uread(edm, TEST_PEOPLE, null, null, responseHandler);
        rawEntity = responseHandler.await();
        entity = reader.readEntity(rawEntity, TEST_FORMAT);
        assertEquals("Russell", entity.getProperty("FirstName").getValue().toString());
        LOG.info("Single Entity:  {}", prettyPrint(entity));

        responseHandler.reset();
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put(SystemQueryOptionKind.EXPAND.toString(), TRIPS);

        olingoApp.uread(edm, TEST_PEOPLE, queryParams, null, responseHandler);

        rawEntity = responseHandler.await();
        entity = reader.readEntity(rawEntity, TEST_FORMAT);
        LOG.info("Single People Entiry with expanded Trips relation:  {}", prettyPrint(entity));
    }

};