//,temp,ThreeBrokerTempDestDemandSubscriptionCleanupTest.java,150,206,temp,ThreeBrokerTempDestDemandSubscriptionCleanupTest.java,77,117
//,3
public class xxx {
    public void testSubscriptionsCleanedUpAfterConnectionClose() throws Exception {

        final BrokerItem brokerA = brokers.get(BROKER_A);

        for (int i = 0; i < NUM_ITER; i++) {

            Connection conn = null;
            try {
                conn = brokerA.createConnection();

                conn.start();

                final Session sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
                Destination destination = sess.createQueue(ECHO_QUEUE_NAME);

                MessageProducer producer = sess.createProducer(destination);

                LOG.info("Starting iter: " + i);
                Destination replyTo = sess.createTemporaryQueue();
                MessageConsumer responseConsumer = sess.createConsumer(replyTo);

                Message message = sess.createTextMessage("Iteration: " + i);
                message.setJMSReplyTo(replyTo);

                producer.send(message);

                TextMessage response = (TextMessage)responseConsumer.receive(CONSUME_TIMEOUT);
                assertNotNull("We should have gotten a response, but didn't for iter: " + i, response);
                assertEquals("We got the wrong response from the echo service", "Iteration: " + i, response.getText());


                // so closing the connection without closing the consumer first will leak subscriptions
                // in a nob?
//              responseConsumer.close();
                conn.close();

            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }

        }

        // for the real test... we should not have any subscriptions left on broker C for the temp dests
        BrokerItem brokerC = brokers.get(BROKER_C);
        RegionBroker regionBroker = (RegionBroker) brokerC.broker.getRegionBroker();
        final AbstractRegion region = (AbstractRegion) regionBroker.getTempQueueRegion();

        assertTrue("There were no lingering temp-queue destinations", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("Lingering temps: " + region.getSubscriptions().size());
                return 0 == region.getSubscriptions().size();
            }
        }));

    }

};