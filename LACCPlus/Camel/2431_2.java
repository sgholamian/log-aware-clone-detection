//,temp,HttpClientRouteTest.java,51,73,temp,HttpsAsyncRouteTest.java,94,123
//,3
public class xxx {
    @Override
    @Test
    public void testEndpoint() throws Exception {
        // these tests does not run well on Windows
        if (isPlatform("windows")) {
            return;
        }

        MockEndpoint mockEndpointA = resolveMandatoryEndpoint("mock:a", MockEndpoint.class);
        mockEndpointA.expectedBodiesReceived(expectedBody);
        MockEndpoint mockEndpointB = resolveMandatoryEndpoint("mock:b", MockEndpoint.class);
        mockEndpointB.expectedBodiesReceived(expectedBody);

        invokeHttpEndpoint();

        mockEndpointA.assertIsSatisfied();
        mockEndpointB.assertIsSatisfied();
        List<Exchange> list = mockEndpointA.getReceivedExchanges();
        Exchange exchange = list.get(0);
        assertNotNull(exchange, "exchange");

        Message in = exchange.getIn();
        assertNotNull(in, "in");

        Map<String, Object> headers = in.getHeaders();

        LOG.info("Headers: " + headers);

        assertTrue(headers.size() > 0, "Should be more than one header but was: " + headers);
    }

};