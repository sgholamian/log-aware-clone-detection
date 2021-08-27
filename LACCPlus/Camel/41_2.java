//,temp,DumpModelAsXmlRoutePredicateTest.java,71,79,temp,DumpModelAsXmlRoutePredicateTest.java,51,59
//,2
public class xxx {
    @Test
    public void testDumpModelAsXmlXPath() throws Exception {
        ExtendedCamelContext ecc = context.adapt(ExtendedCamelContext.class);
        String xml = ecc.getModelToXMLDumper().dumpModelAsXml(context, context.getRouteDefinition("myOtherRoute"));
        assertNotNull(xml);
        log.info(xml);

        assertTrue(xml.contains("<xpath>/foo</xpath>"));
    }

};