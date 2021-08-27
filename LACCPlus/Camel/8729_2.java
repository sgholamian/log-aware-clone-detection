//,temp,ConsumeJmsMapMessageTest.java,65,80,temp,ConsumeJmsBytesMessageTest.java,78,92
//,3
public class xxx {
    protected void assertCorrectBytesReceived() {
        Exchange exchange = endpoint.getReceivedExchanges().get(0);
        // This should be a JMS Exchange
        assertNotNull(ExchangeHelper.getBinding(exchange, JmsBinding.class));
        JmsMessage in = (JmsMessage) exchange.getIn();
        assertNotNull(in);

        byte[] bytes = exchange.getIn().getBody(byte[].class);
        LOG.info("Received bytes: " + Arrays.toString(bytes));

        assertNotNull(bytes, "Should have received a bytes message!");
        assertIsInstanceOf(BytesMessage.class, in.getJmsMessage());
        assertEquals(1, bytes[0], "Wrong byte 1");
        assertEquals(3, bytes.length);
    }

};