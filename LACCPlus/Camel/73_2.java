//,temp,DumpModelAsXmlRouteExpressionTest.java,42,50,temp,DumpModelAsXmlRoutePredicateTest.java,41,49
//,2
public class xxx {
    @Test
    public void testDumpModelAsXml() throws Exception {
        ExtendedCamelContext ecc = context.adapt(ExtendedCamelContext.class);
        String xml = ecc.getModelToXMLDumper().dumpModelAsXml(context, context.getRouteDefinition("myRoute"));
        assertNotNull(xml);
        log.info(xml);

        assertTrue(xml.contains("<simple>${body} &gt; 10</simple>"));
    }

};