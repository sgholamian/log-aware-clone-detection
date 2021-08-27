//,temp,XmlFilterRouteTest.java,38,55,temp,XmlCSimpleTest.java,36,53
//,2
public class xxx {
    @Test
    void testXml() throws Exception {
        List<CamelCSimpleExpressionDetails> list = new ArrayList<>();

        InputStream is = new FileInputStream("src/test/resources/org/apache/camel/parser/xml/csimple.xml");
        String fqn = "src/test/resources/org/apache/camel/camel/parser/xml/csimple.xml";
        String baseDir = "src/test/resources";
        XmlRouteParser.parseXmlRouteCSimpleExpressions(is, baseDir, fqn, list);

        for (CamelCSimpleExpressionDetails detail : list) {
            LOG.info(detail.getCsimple());
        }

        assertEquals(1, list.size());
        assertEquals("${body} != 'Camel'", list.get(0).getCsimple());
        assertTrue(list.get(0).isPredicate());
        assertFalse(list.get(0).isExpression());
    }

};