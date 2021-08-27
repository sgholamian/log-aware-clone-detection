//,temp,DumpModelAsXmlChoiceFilterRoutePropertyPlaceholderTest.java,47,57,temp,DumpModelAsXmlAggregateRouteTest.java,33,43
//,2
public class xxx {
    @Test
    public void testDumpModelAsXml() throws Exception {
        ExtendedCamelContext ecc = context.adapt(ExtendedCamelContext.class);
        String xml = ecc.getModelToXMLDumper().dumpModelAsXml(context, context.getRouteDefinition("myRoute"));
        assertNotNull(xml);
        log.info(xml);

        assertTrue(xml.contains("<correlationExpression>"));
        assertTrue(xml.contains("<header>userId</header>"));
        assertTrue(xml.contains("</correlationExpression>"));
    }

};