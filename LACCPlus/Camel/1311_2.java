//,temp,RestApiNettyTest.java,40,51,temp,DumpModelAsXmlChoiceFilterRouteTest.java,47,58
//,3
public class xxx {
    @Test
    public void testDumpModelAsXml() throws Exception {
        ExtendedCamelContext ecc = context.adapt(ExtendedCamelContext.class);
        String xml = ecc.getModelToXMLDumper().dumpModelAsXml(context, context.getRouteDefinition("myRoute"));
        assertNotNull(xml);
        log.info(xml);

        assertTrue(xml.contains("<header>dude</header>"));
        assertTrue(xml.contains("<header>gold</header>"));
        assertTrue(xml.contains("<header>extra-gold</header>"));
        assertTrue(xml.contains("<simple>${body} contains 'Camel'</simple>"));
    }

};