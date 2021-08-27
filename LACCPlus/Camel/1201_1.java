//,temp,DumpModelAsXmlRouteExpressionTest.java,72,81,temp,DumpModelAsXmlChoiceFilterRouteTest.java,33,45
//,3
public class xxx {
    @Test
    public void testDumpModelAsXmlBean() throws Exception {
        ExtendedCamelContext ecc = context.adapt(ExtendedCamelContext.class);
        String xml = ecc.getModelToXMLDumper().dumpModelAsXml(context, context.getRouteDefinition("myBeanRoute"));
        assertNotNull(xml);
        log.info(xml);

        assertThat(xml).containsPattern("<setHeader id=\"setHeader[0-9]+\" name=\"foo\">");
        assertTrue(xml.contains("<method ref=\"myCoolBean\"/>"));
    }

};