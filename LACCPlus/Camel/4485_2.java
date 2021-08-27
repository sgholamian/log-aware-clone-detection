//,temp,XmlOnExceptionRouteTest.java,36,54,temp,XmlRouteTest.java,46,61
//,3
public class xxx {
    private void test(String filename, int pos) throws Exception {
        List<CamelEndpointDetails> endpoints = new ArrayList<>();

        InputStream is = new FileInputStream("src/test/resources/org/apache/camel/parser/xml/" + filename + ".xml");
        String fqn = "src/test/resources/org/apache/camel/camel/parser/xml/" + filename + ".xml";
        String baseDir = "src/test/resources";
        XmlRouteParser.parseXmlRouteEndpoints(is, baseDir, fqn, endpoints);

        for (CamelEndpointDetails detail : endpoints) {
            LOG.info(detail.getEndpointUri());
        }
        assertEquals("stream:in?promptMessage=Enter something:", endpoints.get(0).getEndpointUri());
        assertEquals("stream:out", endpoints.get(1).getEndpointUri());
        assertEquals("39", endpoints.get(1).getLineNumber());
        assertEquals(pos, endpoints.get(1).getLinePosition());
    }

};