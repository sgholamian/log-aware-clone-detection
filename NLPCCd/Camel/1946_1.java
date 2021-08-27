//,temp,sample_4317.java,2,14,temp,sample_4318.java,2,15
//,2
public class xxx {
public JAXRSClientFactoryBean get(String address) throws Exception {
JAXRSClientFactoryBean retVal = null;
synchronized (cache) {
retVal = cache.get(address);
if (retVal == null) {
retVal = ((CxfRsEndpoint)getEndpoint()).createJAXRSClientFactoryBean(address);
cache.put(address, retVal);


log.info("created client factory bean and add to cache for address");
}
}
}

};