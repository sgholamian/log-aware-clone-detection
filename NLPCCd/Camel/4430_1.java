//,temp,sample_6141.java,2,11,temp,sample_1012.java,2,11
//,2
public class xxx {
protected void doStart() throws Exception {
if (consumerCache == null) {
if (cacheSize < 0) {
consumerCache = new EmptyConsumerCache(this, camelContext);


log.info("pollenrich is not using consumercache");
}
}
}

};