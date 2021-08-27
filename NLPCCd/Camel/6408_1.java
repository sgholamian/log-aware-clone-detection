//,temp,sample_2681.java,2,12,temp,sample_2682.java,2,14
//,3
public class xxx {
private Exchange receive(KeyedDataQueue queue, long timeout) throws Exception {
String key = getEndpoint().getSearchKey();
String searchType = getEndpoint().getSearchType().name();
KeyedDataQueueEntry entry;
if (timeout >= 0) {
int seconds = (int) timeout / 1000;


log.info("reading from data queue with seconds timeout");
}
}

};