//,temp,WalkOIDTest.java,35,47,temp,AsteriskConsumerTest.java,38,50
//,2
public class xxx {
    @Disabled
    @Test
    public void testOIDWalk() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(1);
        mock.assertIsSatisfied();
        List<Exchange> oids = mock.getExchanges();
        if (LOG.isInfoEnabled()) {
            for (Exchange e : oids) {
                LOG.info("OID: " + e.getIn().getBody(String.class));
            }
        }
    }

};