//,temp,sample_6142.java,2,13,temp,sample_1013.java,2,13
//,2
public class xxx {
protected void doStart() throws Exception {
if (producerCache == null) {
if (cacheSize < 0) {
producerCache = new EmptyProducerCache(this, camelContext);
} else if (cacheSize == 0) {
producerCache = new ProducerCache(this, camelContext);


log.info("dynamicsendto using producercache with default cache size");
}
}
}

};