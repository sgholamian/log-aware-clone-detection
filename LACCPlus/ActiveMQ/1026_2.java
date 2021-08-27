//,temp,CompositeDestinationSendWhenNotMatchedTest.java,278,314,temp,VirtualTopicWildcardTest.java,70,111
//,3
public class xxx {
    @Test
    public void testWildcardAndSimpleConsumerShareMessages() throws Exception {

        ConsumerBean messageList1 = new ConsumerBean("1:");
        ConsumerBean messageList2 = new ConsumerBean("2:");
        ConsumerBean messageList3 = new ConsumerBean("3:");

        messageList1.setVerbose(true);
        messageList2.setVerbose(true);
        messageList3.setVerbose(true);

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination producerDestination = new ActiveMQTopic("VirtualTopic.TEST.A.IT");
        Destination destination1 = new ActiveMQQueue("Consumer.1.VirtualTopic.TEST.>");
        Destination destination2 = new ActiveMQQueue("Consumer.1.VirtualTopic.TEST.A.IT");
        Destination destination3 = new ActiveMQQueue("Consumer.1.VirtualTopic.TEST.B.IT");

        LOG.info("Sending to: " + producerDestination);
        LOG.info("Consuming from: " + destination1 + " and " + destination2 + ", and " + destination3);

        MessageConsumer c1 = session.createConsumer(destination1, null);
        MessageConsumer c2 = session.createConsumer(destination2, null);
        // this consumer should get no messages
        MessageConsumer c3 = session.createConsumer(destination3, null);

        c1.setMessageListener(messageList1);
        c2.setMessageListener(messageList2);
        c3.setMessageListener(messageList3);

        // create topic producer
        MessageProducer producer = session.createProducer(producerDestination);
        assertNotNull(producer);

        for (int i = 0; i < total; i++) {
            producer.send(createMessage(session, i));
        }

        assertMessagesArrived(messageList1, messageList2);
        assertEquals(0, messageList3.getMessages().size());

    }

};