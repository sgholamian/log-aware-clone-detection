//,temp,UndertowSharedPortTest.java,42,57,temp,UndertowComponentTest.java,32,47
//,3
public class xxx {
    @Test
    public void testUndertow() throws Exception {
        MockEndpoint mockEndpoint = getMockEndpoint("mock:myapp");
        mockEndpoint.expectedHeaderReceived(Exchange.HTTP_METHOD, "POST");
        LOG.debug("Number of exchanges in mock:myapp " + mockEndpoint.getExchanges().size());

        String response = template.requestBody("undertow:http://localhost:{{port}}/myapp", "Hello Camel!", String.class);
        assertNotNull(response);
        assertEquals("Bye Camel!", response);

        mockEndpoint.assertIsSatisfied();

        for (Exchange exchange : mockEndpoint.getExchanges()) {
            assertEquals("Bye Camel!", exchange.getIn().getBody(String.class));
        }
    }

};