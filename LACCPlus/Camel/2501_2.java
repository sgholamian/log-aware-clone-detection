//,temp,Olingo4ComponentProducerTest.java,236,313,temp,Olingo4AppAPITest.java,497,566
//,3
public class xxx {
    @Test
    public void testBatchRequest() throws Exception {

        final List<Olingo4BatchRequest> batchParts = new ArrayList<>();

        // 1. Edm query
        batchParts.add(Olingo4BatchQueryRequest.resourcePath(Constants.METADATA).resourceUri(TEST_SERVICE_BASE_URL).build());

        // 2. Query entity set
        batchParts.add(Olingo4BatchQueryRequest.resourcePath(PEOPLE).resourceUri(TEST_SERVICE_BASE_URL).build());

        // 3. Read entity
        batchParts.add(Olingo4BatchQueryRequest.resourcePath(TEST_PEOPLE).resourceUri(TEST_SERVICE_BASE_URL).build());

        // 4. Read with expand
        final HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put(SystemQueryOptionKind.EXPAND.toString(), TRIPS);
        batchParts.add(Olingo4BatchQueryRequest.resourcePath(TEST_PEOPLE).queryParams(queryParams)
                .resourceUri(TEST_SERVICE_BASE_URL).build());

        // 5. Create entity
        final ClientEntity clientEntity = createEntity();
        batchParts.add(Olingo4BatchChangeRequest.resourcePath(PEOPLE).resourceUri(TEST_SERVICE_BASE_URL)
                .contentId(TEST_CREATE_RESOURCE_CONTENT_ID).operation(Operation.CREATE)
                .body(clientEntity).build());

        // 6. Update entity
        clientEntity.getProperties()
                .add(objFactory.newPrimitiveProperty("MiddleName", objFactory.newPrimitiveValueBuilder().buildString("Lewis")));
        batchParts.add(Olingo4BatchChangeRequest.resourcePath(TEST_CREATE_PEOPLE).resourceUri(TEST_SERVICE_BASE_URL)
                .contentId(TEST_UPDATE_RESOURCE_CONTENT_ID)
                .operation(Operation.UPDATE).body(clientEntity).build());

        // 7. Delete entity
        batchParts.add(Olingo4BatchChangeRequest.resourcePath(TEST_CREATE_PEOPLE).resourceUri(TEST_SERVICE_BASE_URL)
                .operation(Operation.DELETE).build());

        // 8. Read to verify entity delete
        batchParts.add(Olingo4BatchQueryRequest.resourcePath(TEST_CREATE_PEOPLE).resourceUri(TEST_SERVICE_BASE_URL).build());

        final TestOlingo4ResponseHandler<List<Olingo4BatchResponse>> responseHandler = new TestOlingo4ResponseHandler<>();
        olingoApp.batch(edm, null, batchParts, responseHandler);

        final List<Olingo4BatchResponse> responseParts = responseHandler.await(15, TimeUnit.MINUTES);
        assertEquals(8, responseParts.size(), "Batch responses expected");

        assertNotNull(responseParts.get(0).getBody());

        final ClientEntitySet clientEntitySet = (ClientEntitySet) responseParts.get(1).getBody();
        assertNotNull(clientEntitySet);
        LOG.info("Batch entity set:  {}", prettyPrint(clientEntitySet));

        ClientEntity returnClientEntity = (ClientEntity) responseParts.get(2).getBody();
        assertNotNull(returnClientEntity);
        LOG.info("Batch read entity:  {}", prettyPrint(returnClientEntity));

        returnClientEntity = (ClientEntity) responseParts.get(3).getBody();
        assertNotNull(returnClientEntity);
        LOG.info("Batch read entity with expand:  {}", prettyPrint(returnClientEntity));

        ClientEntity createdClientEntity = (ClientEntity) responseParts.get(4).getBody();
        assertNotNull(createdClientEntity);
        assertEquals(TEST_CREATE_RESOURCE_CONTENT_ID, responseParts.get(4).getContentId());
        LOG.info("Batch created entity:  {}", prettyPrint(returnClientEntity));

        assertEquals(HttpStatusCode.NO_CONTENT.getStatusCode(), responseParts.get(5).getStatusCode());
        assertEquals(TEST_UPDATE_RESOURCE_CONTENT_ID, responseParts.get(5).getContentId());
        assertEquals(HttpStatusCode.NO_CONTENT.getStatusCode(), responseParts.get(6).getStatusCode());
        assertEquals(HttpStatusCode.NOT_FOUND.getStatusCode(), responseParts.get(7).getStatusCode());
    }

};