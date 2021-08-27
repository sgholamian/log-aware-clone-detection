//,temp,HttpClientRouteTest.java,51,73,temp,HttpsAsyncRouteTest.java,94,123
//,3
public class xxx {
    private void testHttpClient(String uri) throws Exception {
        System.setProperty("HTTPClient.dontChunkRequests", "yes");

        MockEndpoint mockEndpoint = getMockEndpoint("mock:a");
        mockEndpoint.expectedBodiesReceived("<b>Hello World</b>");

        template.requestBodyAndHeader(uri, new ByteArrayInputStream("This is a test".getBytes()), "Content-Type",
                "application/xml");

        mockEndpoint.assertIsSatisfied();
        List<Exchange> list = mockEndpoint.getReceivedExchanges();
        Exchange exchange = list.get(0);
        assertNotNull(exchange, "exchange");

        Message in = exchange.getIn();
        assertNotNull(in, "in");

        Map<String, Object> headers = in.getHeaders();

        LOG.info("Headers: " + headers);

        assertTrue(headers.size() > 0, "Should be more than one header but was: " + headers);
    }

};