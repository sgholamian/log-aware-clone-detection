//,temp,XmlFilterRouteTest.java,38,55,temp,XmlCSimpleTest.java,36,53
//,2
public class xxx {
    @Test
    void testXml() throws Exception {
        List<CamelSimpleExpressionDetails> list = new ArrayList<>();

        InputStream is = new FileInputStream("src/test/resources/org/apache/camel/parser/xml/myfiltercamel.xml");
        String fqn = "src/test/resources/org/apache/camel/camel/parser/xml/myfiltercamel.xml";
        String baseDir = "src/test/resources";
        XmlRouteParser.parseXmlRouteSimpleExpressions(is, baseDir, fqn, list);

        for (CamelSimpleExpressionDetails detail : list) {
            LOG.info(detail.getSimple());
        }

        assertEquals(1, list.size());
        assertEquals("${body} == 'Camel'", list.get(0).getSimple());
        assertTrue(list.get(0).isPredicate());
        assertFalse(list.get(0).isExpression());
    }

};