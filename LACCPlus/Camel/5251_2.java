//,temp,Olingo2AppAPITest.java,391,462,temp,Olingo2ComponentProducerTest.java,216,298
//,3
public class xxx {
    @Test
    public void testBatch() throws Exception {
        final List<Olingo2BatchRequest> batchParts = new ArrayList<>();

        // 1. Edm query
        batchParts.add(Olingo2BatchQueryRequest.resourcePath(Olingo2AppImpl.METADATA).build());

        // 2. feed query
        batchParts.add(Olingo2BatchQueryRequest.resourcePath(MANUFACTURERS).build());

        // 3. read
        batchParts.add(Olingo2BatchQueryRequest.resourcePath(TEST_MANUFACTURER).build());

        // 4. read with expand
        final HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put(SystemQueryOption.$expand.toString(), CARS);
        batchParts.add(Olingo2BatchQueryRequest.resourcePath(TEST_MANUFACTURER).queryParams(queryParams).build());

        // 5. create
        final Map<String, Object> data = getEntityData();
        batchParts.add(Olingo2BatchChangeRequest.resourcePath(MANUFACTURERS).contentId(TEST_RESOURCE_CONTENT_ID)
                .operation(Operation.CREATE).body(data).build());

        // 6. update address in created entry
        final Map<String, Object> updateData = new HashMap<>(data);
        Map<String, Object> address = (Map<String, Object>) updateData.get(ADDRESS);
        address.put("Street", "Main Street");
        batchParts.add(Olingo2BatchChangeRequest.resourcePath(TEST_RESOURCE_ADDRESS).operation(Operation.UPDATE).body(address)
                .build());

        // 7. update
        updateData.put("Name", "MyCarManufacturer Renamed");
        batchParts.add(
                Olingo2BatchChangeRequest.resourcePath(TEST_RESOURCE).operation(Operation.UPDATE).body(updateData).build());

        // 8. delete
        batchParts.add(Olingo2BatchChangeRequest.resourcePath(TEST_RESOURCE).operation(Operation.DELETE).build());

        // 9. read to verify delete
        batchParts.add(Olingo2BatchQueryRequest.resourcePath(TEST_CREATE_MANUFACTURER).build());

        // execute batch request
        final List<Olingo2BatchResponse> responseParts = requestBody("direct:BATCH", batchParts);
        assertNotNull(responseParts, "Batch response");
        assertEquals(9, responseParts.size(), "Batch responses expected");

        final Edm edm = (Edm) responseParts.get(0).getBody();
        assertNotNull(edm);
        LOG.info("Edm entity sets: {}", edm.getEntitySets());

        final ODataFeed feed = (ODataFeed) responseParts.get(1).getBody();
        assertNotNull(feed);
        LOG.info("Read feed: {}", feed.getEntries());

        ODataEntry dataEntry = (ODataEntry) responseParts.get(2).getBody();
        assertNotNull(dataEntry);
        LOG.info("Read entry: {}", dataEntry.getProperties());

        dataEntry = (ODataEntry) responseParts.get(3).getBody();
        assertNotNull(dataEntry);
        LOG.info("Read entry with $expand: {}", dataEntry.getProperties());

        dataEntry = (ODataEntry) responseParts.get(4).getBody();
        assertNotNull(dataEntry);
        LOG.info("Created entry: {}", dataEntry.getProperties());

        int statusCode = responseParts.get(5).getStatusCode();
        assertEquals(HttpStatusCodes.NO_CONTENT.getStatusCode(), statusCode);
        LOG.info("Update address status: {}", statusCode);

        statusCode = responseParts.get(6).getStatusCode();
        assertEquals(HttpStatusCodes.NO_CONTENT.getStatusCode(), statusCode);
        LOG.info("Update entry status: {}", statusCode);

        statusCode = responseParts.get(7).getStatusCode();
        assertEquals(HttpStatusCodes.NO_CONTENT.getStatusCode(), statusCode);
        LOG.info("Delete status: {}", statusCode);

        assertEquals(HttpStatusCodes.NOT_FOUND.getStatusCode(), responseParts.get(8).getStatusCode());
        final Exception exception = (Exception) responseParts.get(8).getBody();
        assertNotNull(exception);
        LOG.info("Read deleted entry exception: {}", exception);
    }

};