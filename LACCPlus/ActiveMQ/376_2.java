//,temp,JMSClientProducerFlowSendFailIfNoSpace.java,107,147,temp,ProducerFlowControlTest.java,136,182
//,3
public class xxx {
    public void testAsyncPubisherRecoverAfterBlock() throws Exception {
        ActiveMQConnectionFactory factory = (ActiveMQConnectionFactory)createConnectionFactory();
        factory.setProducerWindowSize(1024 * 5);
        factory.setUseAsyncSend(true);
        connection = (ActiveMQConnection)factory.createConnection();
        connections.add(connection);
        connection.start();

        final Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        final MessageProducer producer = session.createProducer(queueA);
        
        final AtomicBoolean done = new AtomicBoolean(true);
        final AtomicBoolean keepGoing = new AtomicBoolean(true);
        
   
        Thread thread = new Thread("Filler") {
            int i;
            @Override
            public void run() {
                while (keepGoing.get()) {
                    done.set(false);
                    try {
                        producer.send(session.createTextMessage("Test message " + ++i));
                        LOG.info("sent: " + i);
                    } catch (JMSException e) {
                    }
                }
            }
        };
        thread.start();
        waitForBlockedOrResourceLimit(done);

        // after receiveing messges, producer should continue sending messages 
        // (done == false)
        MessageConsumer consumer = session.createConsumer(queueA);
        TextMessage msg;
        for (int idx = 0; idx < 5; ++idx) {
            msg = (TextMessage) consumer.receive(1000);
            assertNotNull("Got a message", msg);
            LOG.info("received: " + idx + ", msg: " + msg.getJMSMessageID());
            msg.acknowledge();
        }
        Thread.sleep(1000);
        keepGoing.set(false);
        
        assertFalse("producer has resumed", done.get());
    }

};