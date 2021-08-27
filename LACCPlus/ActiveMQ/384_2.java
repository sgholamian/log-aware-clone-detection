//,temp,CustomVirtualTopicInterceptorWithLeadingWildcardTest.java,57,91,temp,VirtualTopicSelectorTest.java,52,85
//,3
public class xxx {
    @Override
    protected void assertMessagesArrived(ConsumerBean messageList1, ConsumerBean messageList2) {
        messageList1.assertMessagesArrived(total/2);
        messageList2.assertMessagesArrived(total/2);
 
        messageList1.flushMessages();
        messageList2.flushMessages();
        
        LOG.info("validate no other messages on queues");
        try {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                
            Destination destination1 = getConsumer1Dsetination();
            Destination destination2 = getConsumer2Dsetination();
            MessageConsumer c1 = session.createConsumer(destination1, null);
            MessageConsumer c2 = session.createConsumer(destination2, null);
            c1.setMessageListener(messageList1);
            c2.setMessageListener(messageList2);
            
            
            LOG.info("send one simple message that should go to both consumers");
            MessageProducer producer = session.createProducer(getProducerDestination());
            assertNotNull(producer);
            
            producer.send(session.createTextMessage("Last Message"));
            
            messageList1.assertMessagesArrived(1);
            messageList2.assertMessagesArrived(1);
        
        } catch (JMSException e) {
            e.printStackTrace();
            fail("unexpeced ex while waiting for last messages: " + e);
        }
    }

};