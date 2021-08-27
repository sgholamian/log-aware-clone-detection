//,temp,TidyMarkupDataFormatAsDomNodeTest.java,39,78,temp,TidyMarkupDataFormatAsStringTest.java,38,65
//,3
public class xxx {
    @Test
    public void testUnMarshalToStringOfXml() throws Exception {
        MockEndpoint resultEndpoint = resolveMandatoryEndpoint("mock:result", MockEndpoint.class);
        resultEndpoint.expectedMessageCount(2);

        /*
         * each of these files has a <p>TidyMarkupNode section. (no closing tag)
         * 
         * See the route below, we send the tidyMarkup to xpath and boolean that out.
         */
        String badHtml = TidyMarkupTestSupport.loadFileAsString(new File(
                "src/test/resources/org/apache/camel/dataformat/tagsoup/testfile1.html"));
        String evilHtml = TidyMarkupTestSupport.loadFileAsString(new File(
                "src/test/resources/org/apache/camel/dataformat/tagsoup/testfile2-evilHtml.html"));

        template.sendBody("direct:start", badHtml);
        template.sendBody("direct:start", evilHtml);

        resultEndpoint.assertIsSatisfied();
        List<Exchange> list = resultEndpoint.getReceivedExchanges();
        for (Exchange exchange : list) {
            Message in = exchange.getIn();
            String response = in.getBody(String.class);

            log.debug("Received " + response);
            assertNotNull(response, "Should be able to convert received body to a string");

            try {
                /*
                 * our route xpaths the existence of our signature "<p>TidyMarkupNode"
                 * but of course, by the xpath time, it is well formed
                 */
                assertEquals("true", response);
            } catch (Exception e) {

                fail("Failed to convert the resulting String to XML: " + e.getLocalizedMessage());
            }

        }
    }

};