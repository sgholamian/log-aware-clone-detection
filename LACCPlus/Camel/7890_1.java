//,temp,RoasterCSimpleRouteBuilderConfigureTest.java,39,56,temp,RoasterSimpleRouteBuilderConfigureTest.java,39,56
//,2
public class xxx {
    @Test
    void parse() throws Exception {
        JavaClassSource clazz = (JavaClassSource) Roaster
                .parse(new File("src/test/java/org/apache/camel/parser/java/MyCSimpleRouteBuilder.java"));
        MethodSource<JavaClassSource> method = clazz.getMethod("configure");

        List<ParserResult> list = CamelJavaParserHelper.parseCamelLanguageExpressions(method, "csimple");
        for (ParserResult csimple : list) {
            LOG.info("CSimple: " + csimple.getElement());
            LOG.info("   Line: " + findLineNumber(csimple.getPosition()));
        }
        assertEquals("${body} > 99", list.get(0).getElement());
        assertEquals(true, list.get(0).getPredicate());
        assertEquals(27, findLineNumber(list.get(0).getPosition()));
        assertEquals("${body} > 201", list.get(1).getElement());
        assertEquals(true, list.get(1).getPredicate());
        assertEquals(30, findLineNumber(list.get(1).getPosition()));
    }

};