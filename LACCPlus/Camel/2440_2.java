//,temp,TidyMarkupDataFormatAsDomNodeTest.java,39,78,temp,TidyMarkupDataFormatAsStringTest.java,38,65
//,3
public class xxx {
    @Test
    public void testUnMarshalToStringOfXml() throws Exception {
        MockEndpoint resultEndpoint = resolveMandatoryEndpoint("mock:result", MockEndpoint.class);
        resultEndpoint.expectedMessageCount(2);

        String badHtml = TidyMarkupTestSupport.loadFileAsString(new File(
                "src/test/resources/org/apache/camel/dataformat/tagsoup/testfile1.html"));
        String evilHtml = TidyMarkupTestSupport.loadFileAsString(new File(
                "src/test/resources/org/apache/camel/dataformat/tagsoup/testfile2-evilHtml.html"));

        template.sendBody("direct:start", badHtml);
        template.sendBody("direct:start", evilHtml);

        resultEndpoint.assertIsSatisfied();
        List<Exchange> list = resultEndpoint.getReceivedExchanges();
        for (Exchange exchange : list) {
            try {
                Message in = exchange.getIn();
                String tidyMarkup = in.getBody(String.class);

                log.debug("Received " + tidyMarkup);
                assertNotNull(tidyMarkup, "Should be able to convert received body to a string");

            } catch (Exception e) {
                fail("Failed to convert the resulting String to XML: " + e.getLocalizedMessage());
            }
        }
    }

};