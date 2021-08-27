//,temp,DumpModelAsXmlChoiceFilterRoutePropertyPlaceholderTest.java,34,45,temp,DumpModelAsXmlRoutePredicateTest.java,61,69
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