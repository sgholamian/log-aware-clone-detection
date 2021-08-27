//,temp,GenerateXmlTest.java,55,64,temp,GenerateXmFromCamelContextTest.java,45,55
//,3
public class xxx {
    protected void dump(RouteContainer context) throws Exception {
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter buffer = new StringWriter();
        marshaller.marshal(context, buffer);
        log.info("Created: " + buffer);
        assertNotNull(buffer);
        String out = buffer.toString();
        assertTrue(out.indexOf("This is a description of the route") > 0, "Should contain the description");
    }

};