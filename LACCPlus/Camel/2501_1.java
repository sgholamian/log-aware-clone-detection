//,temp,Olingo4ComponentProducerTest.java,236,313,temp,Olingo4AppAPITest.java,497,566
//,3
public class xxx {
    @Test
    public void testBatch() throws Exception {
        final List<Olingo4BatchRequest> batchParts = new ArrayList<>();

        // 1. Edm query
        batchParts.add(Olingo4BatchQueryRequest.resourcePath(Constants.METADATA).resourceUri(TEST_SERVICE_BASE_URL).build());

        // 2. Read entities
        batchParts.add(Olingo4BatchQueryRequest.resourcePath(PEOPLE).resourceUri(TEST_SERVICE_BASE_URL).build());

        // 3. Read entity
        batchParts.add(Olingo4BatchQueryRequest.resourcePath(TEST_PEOPLE).resourceUri(TEST_SERVICE_BASE_URL).build());

        // 4. Read with $top
        final HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put(SystemQueryOptionKind.TOP.toString(), "5");
        batchParts.add(Olingo4BatchQueryRequest.resourcePath(PEOPLE).resourceUri(TEST_SERVICE_BASE_URL).queryParams(queryParams)
                .build());

        // 5. Create entity
        ClientEntity clientEntity = createEntity();
        batchParts.add(Olingo4BatchChangeRequest.resourcePath(PEOPLE).resourceUri(TEST_SERVICE_BASE_URL)
                .contentId(TEST_CREATE_RESOURCE_CONTENT_ID).operation(Operation.CREATE)
                .body(clientEntity).build());

        // 6. Update middle name in created entry
        clientEntity.getProperties()
                .add(objFactory.newPrimitiveProperty("MiddleName", objFactory.newPrimitiveValueBuilder().buildString("Lewis")));
        batchParts.add(Olingo4BatchChangeRequest.resourcePath(TEST_CREATE_PEOPLE).resourceUri(TEST_SERVICE_BASE_URL)
                .contentId(TEST_UPDATE_RESOURCE_CONTENT_ID)
                .operation(Operation.UPDATE).body(clientEntity).build());

        // 7. Delete entity
        batchParts.add(Olingo4BatchChangeRequest.resourcePath(TEST_CREATE_PEOPLE).resourceUri(TEST_SERVICE_BASE_URL)
                .operation(Operation.DELETE).build());

        // 8. Read deleted entity to verify delete
        batchParts.add(Olingo4BatchQueryRequest.resourcePath(TEST_CREATE_PEOPLE).resourceUri(TEST_SERVICE_BASE_URL).build());

        // execute batch request
        final List<Olingo4BatchResponse> responseParts = requestBody("direct:batch", batchParts);
        assertNotNull(responseParts, "Batch response");
        assertEquals(8, responseParts.size(), "Batch responses expected");

        final Edm edm = (Edm) responseParts.get(0).getBody();
        assertNotNull(edm);
        LOG.info("Edm entity sets: {}", edm.getEntityContainer().getEntitySets());

        ClientEntitySet entitySet = (ClientEntitySet) responseParts.get(1).getBody();
        assertNotNull(entitySet);
        LOG.info("Read entities: {}", entitySet.getEntities());

        clientEntity = (ClientEntity) responseParts.get(2).getBody();
        assertNotNull(clientEntity);
        LOG.info("Read entiry properties: {}", clientEntity.getProperties());

        ClientEntitySet entitySetWithTop = (ClientEntitySet) responseParts.get(3).getBody();
        assertNotNull(entitySetWithTop);
        assertEquals(5, entitySetWithTop.getEntities().size());
        LOG.info("Read entities with $top=5: {}", entitySet.getEntities());

        clientEntity = (ClientEntity) responseParts.get(4).getBody();
        assertNotNull(clientEntity);
        LOG.info("Created entity: {}", clientEntity.getProperties());

        int statusCode = responseParts.get(5).getStatusCode();
        assertEquals(HttpStatusCode.NO_CONTENT.getStatusCode(), statusCode);
        LOG.info("Update MdiddleName status: {}", statusCode);

        statusCode = responseParts.get(6).getStatusCode();
        assertEquals(HttpStatusCode.NO_CONTENT.getStatusCode(), statusCode);
        LOG.info("Delete entity status: {}", statusCode);

        assertEquals(HttpStatusCode.NOT_FOUND.getStatusCode(), responseParts.get(7).getStatusCode());
        final ODataError error = (ODataError) responseParts.get(7).getBody();
        assertNotNull(error);
        LOG.info("Read deleted entity error: {}", error.getMessage());
    }

};