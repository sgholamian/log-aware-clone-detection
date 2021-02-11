//,temp,sample_5493.java,2,11,temp,sample_4471.java,2,10
//,3
public class xxx {
private List<PartETag> waitForAllPartUploads() throws IOException {
try {
return Futures.allAsList(partETagsFutures).get();
} catch (InterruptedException ie) {


log.info("interrupted partupload");
}
}

};