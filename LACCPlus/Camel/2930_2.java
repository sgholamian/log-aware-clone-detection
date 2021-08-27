//,temp,XmlWithPrefixParseTreeTest.java,44,65,temp,XmlParseTreeTest.java,44,64
//,2
public class xxx {
    @Test
    void testXmlTree() throws Exception {
        InputStream is = new FileInputStream("src/test/resources/org/apache/camel/parser/xml/mycamel.xml");
        String fqn = "src/test/resources/org/apache/camel/camel/parser/xml/mycamel.xml";
        String baseDir = "src/test/resources";
        List<CamelNodeDetails> list = XmlRouteParser.parseXmlRouteTree(is, baseDir, fqn);

        assertEquals(1, list.size());
        CamelNodeDetails details = list.get(0);
        assertEquals("src/test/resources/org/apache/camel/camel/parser/xml/mycamel.xml", details.getFileName());
        assertEquals("myRoute", details.getRouteId());
        assertNull(details.getMethodName());
        assertNull(details.getClassName());

        String tree = details.dump(0);
        LOG.info("\n" + tree);

        assertTrue(tree.contains("32\tfrom"));
        assertTrue(tree.contains("35\t  transform"));
        assertTrue(tree.contains("39\t  to"));
    }

};