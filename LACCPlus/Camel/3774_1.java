//,temp,SchematronProcessorTest.java,57,67,temp,SchematronProcessorTest.java,43,55
//,3
public class xxx {
    @Test
    public void testInvalidXMLWithClientResolver() throws Exception {
        String payload = IOUtils.toString(ClassLoader.getSystemResourceAsStream("xml/article-3.xml"));
        logger.info("Validating payload: {}", payload);

        // validate
        String result = getProcessor("sch/schematron-3.sch", new ClientUriResolver()).validate(payload);
        logger.info("Schematron Report: {}", result);
        assertEquals("A title should be at least two characters", Utils.evaluate("//svrl:failed-assert/svrl:text", result));
        assertEquals(0, Integer.valueOf(Utils.evaluate("count(//svrl:successful-report)", result)).intValue());
    }

};