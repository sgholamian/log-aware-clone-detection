//,temp,XmlOnExceptionRouteTest.java,36,54,temp,XmlRouteTest.java,46,61
//,3
public class xxx {
    @Test
    void testXml() throws Exception {
        List<CamelEndpointDetails> endpoints = new ArrayList<>();

        InputStream is = new FileInputStream("src/test/resources/org/apache/camel/parser/xml/mycamel-onexception.xml");
        String fqn = "src/test/resources/org/apache/camel/parser/xml/mycamel-onexception.xml";
        String baseDir = "src/test/resources";
        XmlRouteParser.parseXmlRouteEndpoints(is, baseDir, fqn, endpoints);

        for (CamelEndpointDetails detail : endpoints) {
            LOG.info(detail.getEndpointUri());
        }

        assertEquals("log:all", endpoints.get(0).getEndpointUri());
        assertEquals("mock:dead", endpoints.get(1).getEndpointUri());
        assertEquals("log:done", endpoints.get(2).getEndpointUri());
        assertEquals("stream:in?promptMessage=Enter something:", endpoints.get(3).getEndpointUri());
        assertEquals("stream:out", endpoints.get(4).getEndpointUri());
    }

};