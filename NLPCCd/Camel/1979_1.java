//,temp,sample_6142.java,2,13,temp,sample_1013.java,2,13
//,2
public class xxx {
protected void doStart() throws Exception {
if (consumerCache == null) {
if (cacheSize < 0) {
consumerCache = new EmptyConsumerCache(this, camelContext);
} else if (cacheSize == 0) {
consumerCache = new ConsumerCache(this, camelContext);


log.info("pollenrich using consumercache with default cache size");
}
}
}

};