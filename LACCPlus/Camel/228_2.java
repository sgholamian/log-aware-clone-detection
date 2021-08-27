//,temp,WalkOIDTest.java,35,47,temp,AsteriskConsumerTest.java,38,50
//,2
public class xxx {
    @Disabled
    @Test
    void testReceiveTraps() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(1);
        mock.assertIsSatisfied();
        List<Exchange> events = mock.getExchanges();
        if (LOG.isInfoEnabled()) {
            for (Exchange e : events) {
                LOG.info("ASTERISK EVENTS: " + e.getIn().getBody(String.class));
            }
        }
    }

};