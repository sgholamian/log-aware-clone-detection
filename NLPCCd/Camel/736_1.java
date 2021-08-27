//,temp,sample_3746.java,2,11,temp,sample_7693.java,2,11
//,3
public class xxx {
protected JAXBContext createContext(String contextPath) throws JAXBException {
ClassLoader cl = NotificationXmlFormatter.class.getClassLoader();
try {
return JAXBContext.newInstance(contextPath, cl);
} catch (Exception e) {


log.info("creating jaxbcontext with contextpath");
}
}

};