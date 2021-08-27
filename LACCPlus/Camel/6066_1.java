//,temp,AsyncMDCTest.java,43,58,temp,JmsRequestReplyProcessRepliesConcurrentUsingThreadsTest.java,38,52
//,3
public class xxx {
    @Test
    public void testThreeMessagesMDC() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedBodiesReceived("Bye Camel", "Bye Camel", "Bye Camel");

        log.info("#1 message");
        template.sendBody("direct:a", "Hello World");

        log.info("#2 message");
        template.sendBody("direct:a", "Hello Camel");

        log.info("#3 message");
        template.sendBody("direct:a", "Hi Camel");

        assertMockEndpointsSatisfied();
    }

};