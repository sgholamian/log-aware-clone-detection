//,temp,SelectorParserTest.java,51,55,temp,SelectorParserTest.java,38,49
//,3
public class xxx {
    public void testFunctionCall() throws Exception {
        Object filter = parse("REGEX('sales.*', group)");
        assertTrue("expected type", filter instanceof BooleanFunctionCallExpr);
        LOG.info("function exp:" + filter);

        // non existent function
        try {
            parse("DoesNotExist('sales.*', group)");
            fail("expect ex on non existent function");
        } catch (InvalidSelectorException expected) {}

    }

};