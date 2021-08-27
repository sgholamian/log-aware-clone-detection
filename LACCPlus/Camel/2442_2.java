//,temp,DumpModelAsXmlRouteTemplateTest.java,37,68,temp,DumpModelAsXmlSplitBodyRouteTest.java,37,61
//,3
public class xxx {
    @Test
    public void testDumpModelAsXml() throws Exception {
        ExtendedCamelContext ecc = context.adapt(ExtendedCamelContext.class);
        String xml = ecc.getModelToXMLDumper().dumpModelAsXml(context, context.getRouteDefinition("myRoute"));
        assertNotNull(xml);
        log.info(xml);

        Document doc = new XmlConverter().toDOMDocument(xml, null);
        NodeList nodes = doc.getElementsByTagName("simple");
        assertEquals(1, nodes.getLength());
        Element node = (Element) nodes.item(0);
        assertNotNull(node, "Node <simple> expected to be instanceof Element");
        assertEquals("${body}", node.getTextContent());

        nodes = doc.getElementsByTagName("split");
        assertEquals(1, nodes.getLength());

        nodes = doc.getElementsByTagName("to");
        assertEquals(1, nodes.getLength());
        node = (Element) nodes.item(0);
        assertNotNull(node, "Node <to> expected to be instanceof Element");
        assertEquals("mock:sub", node.getAttribute("uri"));
        assertEquals("myMock", node.getAttribute("id"));
        assertEquals("true", node.getAttribute("customId"));
    }

};