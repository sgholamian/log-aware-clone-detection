//,temp,IOTAProducerTest.java,89,109,temp,IOTAProducerTest.java,67,87
//,2
public class xxx {
    @Test
    public void getNewAddressTest() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:iota-new-address-response");
        mock.expectedMinimumMessageCount(1);

        try {
            template.sendBody("direct:iota-new-address", "");
        } catch (Exception e) {
            if (e.getCause() instanceof InternalException) {
                boolean flaky = e.getCause().getMessage().contains("Couldn't get a response from nodes");
                if (!flaky) {
                    throw e;
                } else {
                    LOG.warn("Flaky test as IOTA is not online and returning a response in time");
                    return;
                }
            }
        }

        assertMockEndpointsSatisfied();
    }

};