//,temp,sample_385.java,2,15,temp,sample_384.java,2,13
//,3
public class xxx {
protected JAXBContext createContext() throws JAXBException {
if (contextPath != null) {
ClassLoader cl = camelContext.getApplicationContextClassLoader();
if (cl != null) {
return JAXBContext.newInstance(contextPath, cl);
} else {


log.info("creating jaxbcontext with contextpath");
}
}
}

};