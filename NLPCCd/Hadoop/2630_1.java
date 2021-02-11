//,temp,sample_4831.java,2,16,temp,sample_4830.java,2,16
//,2
public class xxx {
public void dummy_method(){
describe("Timing getFileStatus(\"%s\")", path);
S3AFileSystem fs = getFileSystem();
MetricDiff metadataRequests = new MetricDiff(fs, Statistic.OBJECT_METADATA_REQUESTS);
MetricDiff listRequests = new MetricDiff(fs, Statistic.OBJECT_LIST_REQUESTS);
long attempts = getOperationCount();
NanoTimer timer = new NanoTimer();
for (long l = 0; l < attempts; l++) {
fs.getFileStatus(path);
}
timer.end("Time to execute %d getFileStatusCalls", attempts);


log.info("listobjects");
}

};