//,temp,ConsumeJmsMapMessageTest.java,65,80,temp,ConsumeJmsBytesMessageTest.java,78,92
//,3
public class xxx {
    protected void assertCorrectMapReceived() {
        Exchange exchange = endpoint.getReceivedExchanges().get(0);
        // This should be a JMS Exchange
        assertNotNull(ExchangeHelper.getBinding(exchange, JmsBinding.class));
        JmsMessage in = (JmsMessage) exchange.getIn();
        assertNotNull(in);

        Map<?, ?> map = exchange.getIn().getBody(Map.class);
        LOG.info("Received map: " + map);

        assertNotNull(map, "Should have received a map message!");
        assertIsInstanceOf(MapMessage.class, in.getJmsMessage());
        assertEquals("abc", map.get("foo"), "map.foo");
        assertEquals("xyz", map.get("bar"), "map.bar");
        assertEquals(2, map.size(), "map.size");
    }

};