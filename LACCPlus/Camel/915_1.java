//,temp,DumpModelAsXmlChoiceFilterRouteTest.java,60,69,temp,RestApiMatchUriNettyTest.java,33,42
//,3
public class xxx {
    @Test
    public void testDumpModelAsXmAl() throws Exception {
        ExtendedCamelContext ecc = context.adapt(ExtendedCamelContext.class);
        String xml = ecc.getModelToXMLDumper().dumpModelAsXml(context, context.getRouteDefinition("a"));
        assertNotNull(xml);
        log.info(xml);

        assertTrue(xml.contains("<constant>bar</constant>"));
        assertTrue(xml.contains("<expressionDefinition>header{test} is not null</expressionDefinition>"));
    }

};