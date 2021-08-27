//,temp,BrowsableQueueTest.java,65,88,temp,BrowsableQueueTest.java,40,63
//,3
public class xxx {
    @Test
    public void testSendMessagesThenBrowseQueueLimitNotHit() throws Exception {
        // send some messages
        for (int i = 0; i < expectedBodies.length; i++) {
            Object expectedBody = expectedBodies[i];
            template.sendBodyAndHeader("activemq:test.b", expectedBody, "counter", i);
        }

        // now lets browse the queue
        JmsQueueEndpoint endpoint = getMandatoryEndpoint("activemq:test.b?maximumBrowseSize=10", JmsQueueEndpoint.class);
        assertEquals(10, endpoint.getMaximumBrowseSize());
        List<Exchange> list = endpoint.getExchanges();
        LOG.debug("Received: " + list);
        assertEquals(8, endpoint.getExchanges().size(), "Size of list");

        int index = -1;
        for (Exchange exchange : list) {
            String actual = exchange.getIn().getBody(String.class);
            LOG.debug("Received body: " + actual);

            Object expected = expectedBodies[++index];
            assertEquals(expected, actual, "Body: " + index);
        }
    }

};