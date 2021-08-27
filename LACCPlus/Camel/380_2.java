//,temp,DirectRequestReplyAndSedaInOnlyTest.java,28,37,temp,DirectRequestReplyAndDisruptorInOnlyTest.java,32,41
//,3
public class xxx {
    @Test
    void testInOut() throws Exception {
        getMockEndpoint("mock:log").expectedBodiesReceived("Logging: Bye World");

        final String out = template.requestBody("direct:start", "Hello World", String.class);
        assertEquals("Bye World", out);
        LOG.info("Got reply " + out);

        assertMockEndpointsSatisfied();
    }

};