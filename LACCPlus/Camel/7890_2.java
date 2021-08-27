//,temp,RoasterCSimpleRouteBuilderConfigureTest.java,39,56,temp,RoasterSimpleRouteBuilderConfigureTest.java,39,56
//,2
public class xxx {
    @Test
    void parse() throws Exception {
        JavaClassSource clazz = (JavaClassSource) Roaster
                .parse(new File("src/test/java/org/apache/camel/parser/java/MySimpleRouteBuilder.java"));
        MethodSource<JavaClassSource> method = clazz.getMethod("configure");

        List<ParserResult> list = CamelJavaParserHelper.parseCamelLanguageExpressions(method, "simple");
        for (ParserResult simple : list) {
            LOG.info("Simple: " + simple.getElement());
            LOG.info("  Line: " + findLineNumber(simple.getPosition()));
        }
        assertEquals("${body} > 100", list.get(0).getElement());
        assertEquals(true, list.get(0).getPredicate());
        assertEquals(27, findLineNumber(list.get(0).getPosition()));
        assertEquals("${body} > 200", list.get(1).getElement());
        assertEquals(true, list.get(1).getPredicate());
        assertEquals(30, findLineNumber(list.get(1).getPosition()));
    }

};