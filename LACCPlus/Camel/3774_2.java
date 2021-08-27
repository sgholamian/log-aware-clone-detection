//,temp,SchematronProcessorTest.java,57,67,temp,SchematronProcessorTest.java,43,55
//,3
public class xxx {
    @Test
    public void testValidXML() throws Exception {

        String payload = IOUtils.toString(ClassLoader.getSystemResourceAsStream("xml/article-1.xml"));
        logger.info("Validating payload: {}", payload);

        // validate
        String result = getProcessor("sch/schematron-1.sch", null).validate(payload);
        logger.info("Schematron Report: {}", result);
        assertEquals(0, Integer.valueOf(Utils.evaluate("count(//svrl:failed-assert)", result)).intValue());
        assertEquals(0, Integer.valueOf(Utils.evaluate("count(//svrl:successful-report)", result)).intValue());

    }

};