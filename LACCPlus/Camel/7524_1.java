//,temp,BulkApiBatchIntegrationTest.java,59,111,temp,BulkApiQueryIntegrationTest.java,40,87
//,3
public class xxx {
    @ParameterizedTest
    @MethodSource("getBatches")
    public void testBatchLifecycle(BatchTest request) throws Exception {
        log.info("Testing Batch lifecycle with {} content", request.contentType);

        // create an UPSERT test Job for this batch request
        JobInfo jobInfo = new JobInfo();
        jobInfo.setOperation(OperationEnum.UPSERT);
        jobInfo.setContentType(request.contentType);
        jobInfo.setObject(Merchandise__c.class.getSimpleName());
        jobInfo.setExternalIdFieldName("Name");
        jobInfo = createJob(jobInfo);

        // test createBatch
        Map<String, Object> headers = new HashMap<>();
        headers.put(SalesforceEndpointConfig.JOB_ID, jobInfo.getId());
        headers.put(SalesforceEndpointConfig.CONTENT_TYPE, jobInfo.getContentType());
        BatchInfo batchInfo = template().requestBodyAndHeaders("direct:createBatch", request.stream, headers, BatchInfo.class);
        assertNotNull(batchInfo, "Null batch");
        assertNotNull(batchInfo.getId(), "Null batch id");

        // test getAllBatches
        @SuppressWarnings("unchecked")
        List<BatchInfo> batches = template().requestBody("direct:getAllBatches", jobInfo, List.class);
        assertNotNull(batches, "Null batches");
        assertFalse(batches.isEmpty(), "Empty batch list");

        // test getBatch
        batchInfo = batches.get(0);
        batchInfo = getBatchInfo(batchInfo);

        // test getRequest
        InputStream requestStream = template().requestBody("direct:getRequest", batchInfo, InputStream.class);
        assertNotNull(requestStream, "Null batch request");

        // wait for batch to finish
        log.info("Waiting for batch to finish...");
        while (!batchProcessed(batchInfo)) {
            // sleep 5 seconds
            Thread.sleep(5000);
            // check again
            batchInfo = getBatchInfo(batchInfo);
        }
        log.info("Batch finished with state " + batchInfo.getState());
        assertEquals(BatchStateEnum.COMPLETED, batchInfo.getState(), "Batch did not succeed");

        // test getResults
        InputStream results = template().requestBody("direct:getResults", batchInfo, InputStream.class);
        assertNotNull(results, "Null batch results");

        // close the test job
        template().requestBody("direct:closeJob", jobInfo, JobInfo.class);
    }

};