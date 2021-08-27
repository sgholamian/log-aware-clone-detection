//,temp,Olingo4AppAPITest.java,413,447,temp,Olingo4AppAPITest.java,370,404
//,2
public class xxx {
    @Test
    public void testPatchOptimisticConcurrency() throws Exception {
        // test simple property Airlines
        final TestOlingo4ResponseHandler<ClientEntity> entityHandler = new TestOlingo4ResponseHandler<>();

        olingoApp.read(edm, TEST_AIRLINE_TO_UPDATE, null, null, entityHandler);

        // Confirm presence of eTag
        ClientEntity airline = entityHandler.await();
        assertNotNull(airline);
        assertNotNull(airline.getETag());

        TestOlingo4ResponseHandler<HttpStatusCode> statusHandler = new TestOlingo4ResponseHandler<>();
        ClientEntity clientEntity = objFactory.newEntity(null);
        String newAirlineName = "The Patched American Airlines";
        clientEntity.getProperties().add(
                objFactory.newPrimitiveProperty("Name", objFactory.newPrimitiveValueBuilder().buildString(newAirlineName)));

        //
        // Call patch
        //
        olingoApp.patch(edm, TEST_AIRLINE_TO_UPDATE, null, clientEntity, statusHandler);

        HttpStatusCode statusCode = statusHandler.await();
        assertEquals(HttpStatusCode.NO_CONTENT, statusCode);
        LOG.info("Name property updated with status {}", statusCode.getStatusCode());

        // Check for updated entity
        final TestOlingo4ResponseHandler<ClientEntity> responseHandler = new TestOlingo4ResponseHandler<>();

        olingoApp.read(edm, TEST_AIRLINE_TO_UPDATE, null, null, responseHandler);
        ClientEntity entity = responseHandler.await();
        assertEquals(newAirlineName, entity.getProperty("Name").getValue().toString());
        LOG.info("Updated Single Entity:  {}", prettyPrint(entity));
    }

};