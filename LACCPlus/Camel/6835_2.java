//,temp,RoasterJavaDslTwoRoutesTest.java,43,77,temp,RoasterJavaDslTest.java,43,71
//,3
public class xxx {
    @Test
    void parseTree() throws Exception {
        JavaClassSource clazz = (JavaClassSource) Roaster
                .parse(new File("src/test/java/org/apache/camel/parser/java/MyJavaDslRouteBuilder.java"));

        List<CamelNodeDetails> list = RouteBuilderParser.parseRouteBuilderTree(clazz, ".",
                "src/test/java/org/apache/camel/parser/java/MyJavaDslRouteBuilder.java", true);
        assertEquals(1, list.size());
        CamelNodeDetails details = list.get(0);
        assertEquals("src/test/java/org/apache/camel/parser/java/MyJavaDslRouteBuilder.java", details.getFileName());
        assertEquals("bar", details.getRouteId());
        assertEquals("configure", details.getMethodName());
        assertEquals("org.apache.camel.parser.java.MyJavaDslRouteBuilder", details.getClassName());
        assertEquals("28", list.get(0).getLineNumber());
        assertEquals("28", list.get(0).getLineNumberEnd());

        String tree = details.dump(0);
        LOG.info("\n" + tree);

        assertTrue(tree.contains("28\tfrom"));
        assertTrue(tree.contains("29\t  log"));
        assertTrue(tree.contains("30\t  setHeader"));
        assertTrue(tree.contains("31\t  choice"));
        assertTrue(tree.contains("33\t    to"));
        assertTrue(tree.contains("34\t    toD"));
        assertTrue(tree.contains("36\t    toD"));
        assertTrue(tree.contains("38\t    log"));
        assertTrue(tree.contains("40\t  to"));
    }

};