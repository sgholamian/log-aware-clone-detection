//,temp,sample_366.java,2,16,temp,sample_5825.java,2,16
//,3
public class xxx {
public void dummy_method(){
jobInfo = createJob(jobInfo);
BatchInfo batchInfo = template().requestBody("direct:createBatchQuery", jobInfo, BatchInfo.class);
assertNotNull("Null batch query", batchInfo);
assertNotNull("Null batch query id", batchInfo.getId());
InputStream requestStream = template().requestBody("direct:getRequest", batchInfo, InputStream.class);
assertNotNull("Null batch request", requestStream);
while (!batchProcessed(batchInfo)) {
Thread.sleep(5000);
batchInfo = getBatchInfo(batchInfo);
}


log.info("query finished with state");
}

};