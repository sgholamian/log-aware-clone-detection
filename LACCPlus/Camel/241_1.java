//,temp,DumpModelAsXmlChoiceFilterRoutePropertyPlaceholderTest.java,47,57,temp,DumpModelAsXmlAggregateRouteTest.java,33,43
//,2
public class xxx {
    @Test
    public void testDumpModelAsXmAl() throws Exception {
        ExtendedCamelContext ecc = context.adapt(ExtendedCamelContext.class);
        String xml = ecc.getModelToXMLDumper().dumpModelAsXml(context, context.getRouteDefinition("a"));
        assertNotNull(xml);
        log.info(xml);

        assertTrue(xml.contains("message=\"{{mypath}}\""));
        assertTrue(xml.contains("<constant>bar</constant>"));
        assertTrue(xml.contains("<expressionDefinition>header{test} is not null</expressionDefinition>"));
    }

};