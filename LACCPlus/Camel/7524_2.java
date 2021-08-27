//,temp,BulkApiBatchIntegrationTest.java,59,111,temp,BulkApiQueryIntegrationTest.java,40,87
//,3
public class xxx {
    @ParameterizedTest
    @EnumSource(names = { "XML", "CSV" })
    public void testQueryLifecycle(ContentType contentType) throws Exception {
        log.info("Testing Query lifecycle with {} content", contentType);

        // create a QUERY test Job
        JobInfo jobInfo = new JobInfo();
        jobInfo.setOperation(OperationEnum.QUERY);
        jobInfo.setContentType(contentType);
        jobInfo.setObject(Merchandise__c.class.getSimpleName());
        jobInfo = createJob(jobInfo);

        // test createQuery
        BatchInfo batchInfo = template().requestBody("direct:createBatchQuery", jobInfo, BatchInfo.class);
        assertNotNull(batchInfo, "Null batch query");
        assertNotNull(batchInfo.getId(), "Null batch query id");

        // test getRequest
        InputStream requestStream = template().requestBody("direct:getRequest", batchInfo, InputStream.class);
        assertNotNull(requestStream, "Null batch request");

        // wait for batch to finish
        log.info("Waiting for query batch to finish...");
        while (!batchProcessed(batchInfo)) {
            // sleep 5 seconds
            Thread.sleep(5000);
            // check again
            batchInfo = getBatchInfo(batchInfo);
        }
        log.info("Query finished with state " + batchInfo.getState());
        assertEquals(BatchStateEnum.COMPLETED, batchInfo.getState(), "Query did not succeed");

        // test getQueryResultList
        @SuppressWarnings("unchecked")
        List<String> resultIds = template().requestBody("direct:getQueryResultIds", batchInfo, List.class);
        assertNotNull(resultIds, "Null query result ids");
        assertFalse(resultIds.isEmpty(), "Empty result ids");

        // test getQueryResult
        for (String resultId : resultIds) {
            InputStream results = template().requestBodyAndHeader("direct:getQueryResult", batchInfo,
                    SalesforceEndpointConfig.RESULT_ID, resultId, InputStream.class);
            assertNotNull(results, "Null query result");
        }

        // close the test job
        template().requestBody("direct:closeJob", jobInfo, JobInfo.class);
    }

};