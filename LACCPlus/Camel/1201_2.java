//,temp,DumpModelAsXmlRouteExpressionTest.java,72,81,temp,DumpModelAsXmlChoiceFilterRouteTest.java,33,45
//,3
public class xxx {
    @Test
    public void testDumpModelAsXmlNoEmptyLines() throws Exception {
        ExtendedCamelContext ecc = context.adapt(ExtendedCamelContext.class);
        String xml = ecc.getModelToXMLDumper().dumpModelAsXml(context, context.getRouteDefinition("myRoute"));
        assertNotNull(xml);
        log.info(xml);

        // should not contain empty lines
        for (String line : xml.split("\n")) {
            line = line.trim();
            assertFalse(line.isEmpty(), "Should not contain empty lines");
        }
    }

};