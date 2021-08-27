//,temp,SelectorParserTest.java,51,55,temp,SelectorParserTest.java,38,49
//,3
public class xxx {
    public void testParseXPath() throws Exception {
        BooleanExpression filter = parse("XPATH '//title[@lang=''eng'']'");
        assertTrue("Created XPath expression", filter instanceof XPathExpression);
        LOG.info("Expression: " + filter);
    }

};