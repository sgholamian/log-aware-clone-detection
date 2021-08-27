//,temp,sample_366.java,2,16,temp,sample_5825.java,2,16
//,3
public class xxx {
public void dummy_method(){
assertNotNull("Null batches", batches);
assertFalse("Empty batch list", batches.isEmpty());
batchInfo = batches.get(0);
batchInfo = getBatchInfo(batchInfo);
InputStream requestStream  = template().requestBody("direct:getRequest", batchInfo, InputStream.class);
assertNotNull("Null batch request", requestStream);
while (!batchProcessed(batchInfo)) {
Thread.sleep(5000);
batchInfo = getBatchInfo(batchInfo);
}


log.info("batch finished with state");
}

};