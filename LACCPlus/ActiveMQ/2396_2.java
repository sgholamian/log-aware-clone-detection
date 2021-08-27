//,temp,MessagePriorityTest.java,402,459,temp,MessagePriorityTest.java,321,395
//,3
public class xxx {
    public void testHighPriorityDelivery() throws Exception {

        // get zero prefetch
        ActiveMQPrefetchPolicy prefetch = new ActiveMQPrefetchPolicy();
        prefetch.setAll(0);
        factory.setPrefetchPolicy(prefetch);
        conn.close();
        conn = factory.createConnection();
        conn.setClientID("priority");
        conn.start();
        sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

        ActiveMQTopic topic = (ActiveMQTopic)sess.createTopic("TEST");
        final String subName = "priorityDisconnect";
        TopicSubscriber sub = sess.createDurableSubscriber(topic, subName);
        sub.close();

        final int numToProduce = 2000;
        final int[] dups = new int[numToProduce*2];
        ProducerThread producerThread = new ProducerThread(topic, numToProduce, LOW_PRI+1);
        producerThread.run();
        LOG.info("Low priority messages sent");

        sub = sess.createDurableSubscriber(topic, subName);
        final int batchSize = 250;
        int lowLowCount = 0;
        for (int i=0; i<numToProduce; i++) {
            Message msg = sub.receive(15000);
            LOG.info("received i=" + i + ", " + (msg!=null? msg.getJMSMessageID() + ", priority:" + msg.getJMSPriority() : null));
            assertNotNull("Message " + i + " was null", msg);
            assertEquals("Message " + i + " has wrong priority", LOW_PRI+1, msg.getJMSPriority());
            assertTrue("not duplicate ", dups[i] == 0);
            dups[i] = 1;

            if (i % batchSize == 0) {
                producerThread.setMessagePriority(HIGH_PRI);
                producerThread.setMessageCount(1);
                producerThread.run();
                LOG.info("High priority message sent, should be able to receive immediately");

                if (i % batchSize*2 == 0) {
                    producerThread.setMessagePriority(HIGH_PRI -1);
                    producerThread.setMessageCount(1);
                    producerThread.run();
                    LOG.info("High -1 priority message sent, should be able to receive immediately");
                }

                if (i % batchSize*4 == 0) {
                    producerThread.setMessagePriority(LOW_PRI);
                    producerThread.setMessageCount(1);
                    producerThread.run();
                    lowLowCount++;
                    LOG.info("Low low priority message sent, should not be able to receive immediately");
                }

                msg = sub.receive(15000);
                assertNotNull("Message was null", msg);
                LOG.info("received hi? : " + msg);
                assertEquals("high priority", HIGH_PRI, msg.getJMSPriority());

                if (i % batchSize*2 == 0) {
                    msg = sub.receive(15000);
                    assertNotNull("Message was null", msg);
                    LOG.info("received hi -1 ? i=" + i + ", " + msg);
                    assertEquals("high priority", HIGH_PRI -1, msg.getJMSPriority());
                }
            }
        }
        for (int i=0; i<lowLowCount; i++) {
            Message msg = sub.receive(15000);
            LOG.debug("received i=" + i + ", " + (msg!=null? msg.getJMSMessageID() : null));
            assertNotNull("Message " + i + " was null", msg);
            assertEquals("Message " + i + " has wrong priority", LOW_PRI, msg.getJMSPriority());
        }
    }

};