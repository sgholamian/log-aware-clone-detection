//,temp,UndertowSharedPortTest.java,42,57,temp,UndertowComponentTest.java,32,47
//,3
public class xxx {
    private void testPath(String pathSuffix) throws InterruptedException {
        MockEndpoint mockEndpoint = getMockEndpoint("mock:" + pathSuffix);
        mockEndpoint.expectedHeaderReceived(Exchange.HTTP_METHOD, "POST");
        LOG.debug("Number of exchanges in mock:myapp " + mockEndpoint.getExchanges().size());

        String response
                = template.requestBody("undertow:http://localhost:{{port}}/" + pathSuffix, "Hello Camel!", String.class);
        assertNotNull(response);
        assertEquals("Bye Camel! " + pathSuffix, response);

        mockEndpoint.assertIsSatisfied();

        for (Exchange exchange : mockEndpoint.getExchanges()) {
            assertEquals("Bye Camel! " + pathSuffix, exchange.getIn().getBody(String.class));
        }
    }

};