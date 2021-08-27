//,temp,DumpModelAsXmlChoiceFilterRoutePropertyPlaceholderTest.java,34,45,temp,DumpModelAsXmlRoutePredicateTest.java,61,69
//,3
public class xxx {
    @Test
    public void testDumpModelAsXml() throws Exception {
        ExtendedCamelContext ecc = context.adapt(ExtendedCamelContext.class);
        String xml = ecc.getModelToXMLDumper().dumpModelAsXml(context, context.getRouteDefinition("myRoute"));
        assertNotNull(xml);
        log.info(xml);

        assertTrue(xml.contains("<header>{{duke}}</header>"));
        assertTrue(xml.contains("<header>{{best}}</header>"));
        assertTrue(xml.contains("<header>{{extra}}</header>"));
        assertTrue(xml.contains("<simple>${body} contains 'Camel'</simple>"));
    }

};