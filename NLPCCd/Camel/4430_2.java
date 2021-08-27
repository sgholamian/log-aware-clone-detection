//,temp,sample_6141.java,2,11,temp,sample_1012.java,2,11
//,2
public class xxx {
protected void doStart() throws Exception {
if (producerCache == null) {
if (cacheSize < 0) {
producerCache = new EmptyProducerCache(this, camelContext);


log.info("dynamicsendto is not using producercache");
}
}
}

};