//,temp,DirectRequestReplyAndSedaInOnlyTest.java,28,37,temp,DirectRequestReplyAndDisruptorInOnlyTest.java,32,41
//,3
public class xxx {
    @Test
    public void testInOut() throws Exception {
        getMockEndpoint("mock:log").expectedBodiesReceived("Logging: Bye World");

        String out = template.requestBody("direct:start", "Hello World", String.class);
        assertEquals("Bye World", out);
        log.info("Got reply " + out);

        assertMockEndpointsSatisfied();
    }

};