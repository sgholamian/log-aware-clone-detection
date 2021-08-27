//,temp,sample_8161.java,2,13,temp,sample_5852.java,2,11
//,3
public class xxx {
protected void doStart() throws Exception {
if (producerCache == null) {
if (cacheSize < 0) {
producerCache = new EmptyProducerCache(this, camelContext);


log.info("routingslip is not using producercache");
}
}
}

};