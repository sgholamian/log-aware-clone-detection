//,temp,AsyncMDCTest.java,43,58,temp,JmsRequestReplyProcessRepliesConcurrentUsingThreadsTest.java,38,52
//,3
public class xxx {
    @Test
    public void testRequestReplyWithConcurrent() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedBodiesReceivedInAnyOrder("Bye A", "Bye B", "Bye C", "Bye D", "Bye E");

        LOG.info("Sending messages ...");
        template.sendBody("seda:start", "A");
        template.sendBody("seda:start", "B");
        template.sendBody("seda:start", "C");
        template.sendBody("seda:start", "D");
        template.sendBody("seda:start", "E");
        LOG.info("... done sending messages");

        assertMockEndpointsSatisfied();
    }

};