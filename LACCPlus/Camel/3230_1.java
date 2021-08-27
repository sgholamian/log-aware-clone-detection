//,temp,DumpModelAsXmlRouteExpressionTest.java,62,70,temp,DumpModelAsXmlDanishCharactersTest.java,41,50
//,3
public class xxx {
    @Test
    public void testDumpModelAsXmlHeader() throws Exception {
        ExtendedCamelContext ecc = context.adapt(ExtendedCamelContext.class);
        String xml = ecc.getModelToXMLDumper().dumpModelAsXml(context, context.getRouteDefinition("myFooRoute"));
        assertNotNull(xml);
        log.info(xml);

        assertTrue(xml.contains("<header>bar</header>"));
    }

};