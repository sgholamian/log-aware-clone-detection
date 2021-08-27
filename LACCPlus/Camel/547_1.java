//,temp,IrcOnReplyTest.java,37,47,temp,IrcRouteTest.java,37,46
//,3
public class xxx {
    @Test
    public void testIrcMessages() throws Exception {
        resultEndpoint.expectedBodiesReceived(resultEnd);

        resultEndpoint.assertIsSatisfied();

        List<Exchange> list = resultEndpoint.getReceivedExchanges();
        for (Exchange exchange : list) {
            LOGGER.info("Received exchange: " + exchange + " headers: " + exchange.getIn().getHeaders());
        }
    }

};