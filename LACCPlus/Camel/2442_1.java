//,temp,DumpModelAsXmlRouteTemplateTest.java,37,68,temp,DumpModelAsXmlSplitBodyRouteTest.java,37,61
//,3
public class xxx {
    @Test
    public void testDumpModelAsXml() throws Exception {
        ExtendedCamelContext ecc = context.adapt(ExtendedCamelContext.class);
        String xml = ecc.getModelToXMLDumper().dumpModelAsXml(context, context.getRouteTemplateDefinition("myTemplate"));
        assertNotNull(xml);
        log.info(xml);

        Document doc = new XmlConverter().toDOMDocument(xml, null);
        NodeList nodes = doc.getElementsByTagName("simple");
        assertEquals(1, nodes.getLength());
        Element node = (Element) nodes.item(0);
        assertNotNull(node, "Node <simple> expected to be instanceof Element");
        assertEquals("{{greeting}}", node.getTextContent());

        nodes = doc.getElementsByTagName("to");
        assertEquals(1, nodes.getLength());
        node = (Element) nodes.item(0);
        assertNotNull(node, "Node <to> expected to be instanceof Element");
        assertEquals("mock:{{whereto}}", node.getAttribute("uri"));

        nodes = doc.getElementsByTagName("routeTemplate");
        assertEquals(1, nodes.getLength());
        node = (Element) nodes.item(0);
        assertEquals("myTemplate", node.getAttribute("id"));

        nodes = doc.getElementsByTagName("templateParameter");
        assertEquals(2, nodes.getLength());
        node = (Element) nodes.item(0);
        assertEquals("greeting", node.getAttribute("name"));
        node = (Element) nodes.item(1);
        assertEquals("whereto", node.getAttribute("name"));
    }

};