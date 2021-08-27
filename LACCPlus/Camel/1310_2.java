//,temp,GenerateXmlTest.java,55,64,temp,GenerateXmFromCamelContextTest.java,45,55
//,3
public class xxx {
    protected void dump(Object object) throws Exception {
        JAXBContext jaxbContext = XmlTestSupport.createJaxbContext();
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter buffer = new StringWriter();
        marshaller.marshal(object, buffer);
        log.info("Created: " + buffer);
        assertNotNull(buffer);
        String out = buffer.toString();
        assertTrue(out.indexOf("<from uri=\"direct:start\"/>") > 0, "Should contain the description");
    }

};