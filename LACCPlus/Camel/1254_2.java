//,temp,HttpRouteTest.java,68,88,temp,HttpClientRouteEnableChunkedTest.java,45,72
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

        // should get the Content-Length
        assertEquals("chunked", headers.get("Transfer-Encoding"), "Should get the transfer-encoding as chunked");
        // remove the system property
        System.clearProperty("HTTPClient.dontChunkRequests");
    }

};