//,temp,sample_8161.java,2,13,temp,sample_5852.java,2,11
//,3
public class xxx {
protected void doStart() throws Exception {
if (producerCache == null) {
if (cacheSize < 0) {
producerCache = new EmptyProducerCache(this, camelContext);
} else if (cacheSize == 0) {
producerCache = new ProducerCache(this, camelContext);


log.info("recipientlist using producercache with default cache size");
}
}
}

};