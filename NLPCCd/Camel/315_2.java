//,temp,sample_8160.java,2,11,temp,sample_5853.java,2,13
//,3
public class xxx {
protected void doStart() throws Exception {
if (producerCache == null) {
if (cacheSize < 0) {
producerCache = new EmptyProducerCache(this, camelContext);
} else if (cacheSize == 0) {
producerCache = new ProducerCache(this, camelContext);


log.info("routingslip using producercache with default cache size");
}
}
}

};