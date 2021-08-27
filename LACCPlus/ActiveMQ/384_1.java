//,temp,CustomVirtualTopicInterceptorWithLeadingWildcardTest.java,57,91,temp,VirtualTopicSelectorTest.java,52,85
//,3
public class xxx {
    public void testVirtualTopicRouting() throws Exception {
    	if (connection == null) {
            connection = createConnection();
        }
        connection.start();
        
        LOG.info("validate no other messages on queues");        
        try {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            ActiveMQDestination destination1 = getConsumer1Destination();
            ActiveMQDestination destination2 = getConsumer2Destination();

            MessageConsumer c1 = session.createConsumer(destination1, null);
            MessageConsumer c2 = session.createConsumer(destination2, null);

            LOG.info("send one simple message that should go to both consumers");
            MessageProducer producer = session.createProducer(getProducerDestination());
            assertNotNull(producer);

            producer.send(session.createTextMessage("Last Message"));
            //check that c1 received the message as it should
            assertNotNull(c1.receive(3000));
            //check that c2 received the message as well - this breaks pre-patch,
            //when VirtualTopicInterceptor.shouldDispatch only returned true if the prefix
            //did not have ".*", or the destination name started with the first part of the
            //prefix (i.e. in the case of "*.*.", the destination name would have had
            //to be "*").
            assertNotNull(c2.receive(3000));
            
        } catch (JMSException e) {
            e.printStackTrace();
            fail("unexpected ex while waiting for last messages: " + e);
        }
    }

};