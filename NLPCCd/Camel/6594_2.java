//,temp,sample_2145.java,2,10,temp,sample_4481.java,2,11
//,3
public class xxx {
protected void dump(Object object) throws Exception {
JAXBContext jaxbContext = XmlTestSupport.createJaxbContext();
Marshaller marshaller = jaxbContext.createMarshaller();
marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
StringWriter buffer = new StringWriter();
marshaller.marshal(object, buffer);


log.info("created");
}

};