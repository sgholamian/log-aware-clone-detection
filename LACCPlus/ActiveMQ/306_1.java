//,temp,MessagePriorityTest.java,290,319,temp,JDBCMessagePriorityTest.java,65,108
//,3
public class xxx {
    public void testDurableSubsReconnect() throws Exception {
        ActiveMQTopic topic = (ActiveMQTopic)sess.createTopic("TEST");
        final String subName = "priorityDisconnect";
        TopicSubscriber sub = sess.createDurableSubscriber(topic, subName);
        sub.close();

        ProducerThread lowPri = new ProducerThread(topic, MSG_NUM, LOW_PRI);
        ProducerThread highPri = new ProducerThread(topic, MSG_NUM, HIGH_PRI);

        lowPri.start();
        highPri.start();

        lowPri.join();
        highPri.join();


        final int closeFrequency = MSG_NUM/4;
        sub = sess.createDurableSubscriber(topic, subName);
        for (int i = 0; i < MSG_NUM * 2; i++) {
            Message msg = sub.receive(15000);
            LOG.debug("received i=" + i + ", " + (msg!=null? msg.getJMSMessageID() : null));
            assertNotNull("Message " + i + " was null", msg);
            assertEquals("Message " + i + " has wrong priority", i < MSG_NUM ? HIGH_PRI : LOW_PRI, msg.getJMSPriority());
            if (i>0 && i%closeFrequency==0) {
                LOG.info("Closing durable sub.. on: " + i);
                sub.close();
                sub = sess.createDurableSubscriber(topic, subName);
            }
        }
    }

};