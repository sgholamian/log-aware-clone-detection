//,temp,RoasterJavaDslTwoRoutesTest.java,43,77,temp,RoasterJavaDslTest.java,43,71
//,3
public class xxx {
    @Test
    void parseTree() throws Exception {
        JavaClassSource clazz = (JavaClassSource) Roaster
                .parse(new File("src/test/java/org/apache/camel/parser/java/TwoRoutesRouteBuilder.java"));

        List<CamelNodeDetails> list = RouteBuilderParser.parseRouteBuilderTree(clazz, ".",
                "src/test/java/org/apache/camel/parser/java/TwoRoutesRouteBuilder.java", true);
        assertEquals(2, list.size());

        CamelNodeDetails details = list.get(0);
        CamelNodeDetails details2 = list.get(1);
        assertEquals("src/test/java/org/apache/camel/parser/java/TwoRoutesRouteBuilder.java", details.getFileName());
        assertEquals("src/test/java/org/apache/camel/parser/java/TwoRoutesRouteBuilder.java", details2.getFileName());

        assertEquals("foo", details.getRouteId());
        assertEquals("org.apache.camel.parser.java.TwoRoutesRouteBuilder", details.getClassName());
        assertEquals("configure", details.getMethodName());
        assertEquals("bar", details2.getRouteId());
        assertEquals("configure", details2.getMethodName());
        assertEquals("org.apache.camel.parser.java.TwoRoutesRouteBuilder", details2.getClassName());

        String tree = details.dump(0);
        LOG.info("\n" + tree);

        String tree2 = details2.dump(0);
        LOG.info("\n" + tree2);

        assertTrue(tree.contains("25\tfrom"));
        assertTrue(tree.contains("26\t  log"));
        assertTrue(tree.contains("27\t  to"));

        assertTrue(tree2.contains("29\tfrom"));
        assertTrue(tree2.contains("30\t  transform"));
        assertTrue(tree2.contains("31\t  to"));
    }

};