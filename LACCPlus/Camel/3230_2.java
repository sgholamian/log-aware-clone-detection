//,temp,DumpModelAsXmlRouteExpressionTest.java,62,70,temp,DumpModelAsXmlDanishCharactersTest.java,41,50
//,3
public class xxx {
    @Test
    public void testDumpModelAsXml() throws Exception {
        ExtendedCamelContext ecc = context.adapt(ExtendedCamelContext.class);
        String xml = ecc.getModelToXMLDumper().dumpModelAsXml(context, context.getRouteDefinition("myRoute"));
        assertNotNull(xml);
        log.info(xml);

        assertTrue(xml.contains("<simple>Hello ${body}</simple>"));
        assertTrue(xml.contains("<description>Hello danish \u00C6\u00D8\u00C5</description>"));
    }

};