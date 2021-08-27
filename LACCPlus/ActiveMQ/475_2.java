//,temp,OptimizedAckTest.java,134,179,temp,OptimizedAckTest.java,92,132
//,3
public class xxx {
    public void testVerySlowReceivedMessageStillInflight() throws Exception {
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("test");
        MessageProducer producer = session.createProducer(queue);
        for (int i = 0; i < 10; i++) {
            producer.send(session.createTextMessage("Hello" + i));
        }

        final RegionBroker regionBroker = (RegionBroker) BrokerRegistry.getInstance().findFirst().getRegionBroker();
        MessageConsumer consumer = session.createConsumer(queue);

        assertTrue("prefetch full", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("inflight count: " + regionBroker.getDestinationStatistics().getInflight().getCount());
                return 10 == regionBroker.getDestinationStatistics().getInflight().getCount();
            }
        }));

        for (int i = 0; i < 6; i++) {
            Thread.sleep(400);
            javax.jms.Message msg = consumer.receive(4000);
            assertNotNull(msg);
            assertEquals("all prefetch is still in flight: " + i, 10, regionBroker.getDestinationStatistics().getInflight().getCount());
        }

        for (int i = 6; i < 10; i++) {
            Thread.sleep(400);
            javax.jms.Message msg = consumer.receive(4000);
            assertNotNull(msg);

            assertTrue("most are acked but 3 remain", Wait.waitFor(new Wait.Condition() {
                @Override
                public boolean isSatisified() throws Exception {
                    return 3 == regionBroker.getDestinationStatistics().getInflight().getCount();
                }
            }));
        }

    }

};