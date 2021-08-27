//,temp,MessagePriorityTest.java,290,319,temp,JDBCMessagePriorityTest.java,65,108
//,3
public class xxx {
    public void testDurableSubsReconnectWithFourLevels() throws Exception {
        ActiveMQTopic topic = (ActiveMQTopic) sess.createTopic("TEST");
        final String subName = "priorityDisconnect";
        TopicSubscriber sub = sess.createDurableSubscriber(topic, subName);
        sub.close();

        final int MED_PRI = LOW_PRI + 1;
        final int MED_HIGH_PRI = HIGH_PRI - 1;

        ProducerThread lowPri = new ProducerThread(topic, MSG_NUM, LOW_PRI);
        ProducerThread medPri = new ProducerThread(topic, MSG_NUM, MED_PRI);
        ProducerThread medHighPri = new ProducerThread(topic, MSG_NUM, MED_HIGH_PRI);
        ProducerThread highPri = new ProducerThread(topic, MSG_NUM, HIGH_PRI);

        lowPri.start();
        highPri.start();
        medPri.start();
        medHighPri.start();

        lowPri.join();
        highPri.join();
        medPri.join();
        medHighPri.join();


        final int closeFrequency = MSG_NUM;
        final int[] priorities = new int[]{HIGH_PRI, MED_HIGH_PRI, MED_PRI, LOW_PRI};
        sub = sess.createDurableSubscriber(topic, subName);
        for (int i = 0; i < MSG_NUM * 4; i++) {
            Message msg = sub.receive(10000);
            LOG.debug("received i=" + i + ", m=" + (msg != null ?
                    msg.getJMSMessageID() + ", priority: " + msg.getJMSPriority()
                    : null));
            assertNotNull("Message " + i + " was null", msg);
            assertEquals("Message " + i + " has wrong priority", priorities[i / MSG_NUM], msg.getJMSPriority());
            if (i > 0 && i % closeFrequency == 0) {
                LOG.info("Closing durable sub.. on: " + i);
                sub.close();
                sub = sess.createDurableSubscriber(topic, subName);
            }
        }
        LOG.info("closing on done!");
        sub.close();
    }

};