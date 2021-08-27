//,temp,Olingo2AppAPITest.java,391,462,temp,Olingo2ComponentProducerTest.java,216,298
//,3
public class xxx {
    @Test
    public void testBatchRequest() throws Exception {

        final List<Olingo2BatchRequest> batchParts = new ArrayList<>();

        // Edm query
        batchParts.add(Olingo2BatchQueryRequest.resourcePath(Olingo2AppImpl.METADATA).build());

        // feed query
        batchParts.add(Olingo2BatchQueryRequest.resourcePath(MANUFACTURERS).build());

        // read
        batchParts.add(Olingo2BatchQueryRequest.resourcePath(TEST_MANUFACTURER).build());

        // read with expand
        final HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put(SystemQueryOption.$expand.toString(), CARS);
        batchParts.add(Olingo2BatchQueryRequest.resourcePath(TEST_MANUFACTURER).queryParams(queryParams).build());

        // create
        final Map<String, Object> data = getEntityData();
        batchParts.add(Olingo2BatchChangeRequest.resourcePath(MANUFACTURERS).contentId(TEST_RESOURCE_CONTENT_ID)
                .operation(Operation.CREATE).body(data).build());

        // update
        final Map<String, Object> updateData = new HashMap<>(data);
        @SuppressWarnings("unchecked")
        Map<String, Object> address = (Map<String, Object>) updateData.get(ADDRESS);
        updateData.put("Name", "MyCarManufacturer Renamed");
        address.put("Street", "Main Street");

        batchParts.add(
                Olingo2BatchChangeRequest.resourcePath(TEST_RESOURCE).operation(Operation.UPDATE).body(updateData).build());

        // delete
        batchParts.add(Olingo2BatchChangeRequest.resourcePath(TEST_RESOURCE).operation(Operation.DELETE).build());

        final TestOlingo2ResponseHandler<List<Olingo2BatchResponse>> responseHandler = new TestOlingo2ResponseHandler<>();

        // read to verify delete
        batchParts.add(Olingo2BatchQueryRequest.resourcePath(TEST_CREATE_MANUFACTURER).build());

        olingoApp.batch(edm, null, batchParts, responseHandler);

        final List<Olingo2BatchResponse> responseParts = responseHandler.await(15, TimeUnit.MINUTES);
        assertEquals(8, responseParts.size(), "Batch responses expected");

        assertNotNull(responseParts.get(0).getBody());
        final ODataFeed feed = (ODataFeed) responseParts.get(1).getBody();
        assertNotNull(feed);
        LOG.info("Batch feed:  {}", prettyPrint(feed));

        ODataEntry dataEntry = (ODataEntry) responseParts.get(2).getBody();
        assertNotNull(dataEntry);
        LOG.info("Batch read entry:  {}", prettyPrint(dataEntry));

        dataEntry = (ODataEntry) responseParts.get(3).getBody();
        assertNotNull(dataEntry);
        LOG.info("Batch read entry with expand:  {}", prettyPrint(dataEntry));

        dataEntry = (ODataEntry) responseParts.get(4).getBody();
        assertNotNull(dataEntry);
        LOG.info("Batch create entry:  {}", prettyPrint(dataEntry));

        assertEquals(HttpStatusCodes.NO_CONTENT.getStatusCode(), responseParts.get(5).getStatusCode());
        assertEquals(HttpStatusCodes.NO_CONTENT.getStatusCode(), responseParts.get(6).getStatusCode());

        assertEquals(HttpStatusCodes.NOT_FOUND.getStatusCode(), responseParts.get(7).getStatusCode());
        final Exception exception = (Exception) responseParts.get(7).getBody();
        assertNotNull(exception);
        LOG.info("Batch retrieve deleted entry:  {}", exception);
    }

};