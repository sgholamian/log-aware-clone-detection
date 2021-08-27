//,temp,Olingo4AppAPITest.java,246,274,temp,Olingo4AppAPITest.java,221,244
//,3
public class xxx {
    @Test
    public void testReadEntity() throws Exception {
        final TestOlingo4ResponseHandler<ClientEntity> responseHandler = new TestOlingo4ResponseHandler<>();

        olingoApp.read(edm, TEST_AIRLINE, null, null, responseHandler);
        ClientEntity entity = responseHandler.await();
        assertEquals("Shanghai Airline", entity.getProperty("Name").getValue().toString());
        LOG.info("Single Entity:  {}", prettyPrint(entity));

        responseHandler.reset();

        olingoApp.read(edm, TEST_PEOPLE, null, null, responseHandler);
        entity = responseHandler.await();
        assertEquals("Russell", entity.getProperty("FirstName").getValue().toString());
        LOG.info("Single Entry:  {}", prettyPrint(entity));

        responseHandler.reset();
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put(SystemQueryOptionKind.EXPAND.toString(), TRIPS);

        olingoApp.read(edm, TEST_PEOPLE, queryParams, null, responseHandler);
        ClientEntity entityExpanded = responseHandler.await();
        LOG.info("Single People Entiry with expanded Trips relation:  {}", prettyPrint(entityExpanded));
    }

};