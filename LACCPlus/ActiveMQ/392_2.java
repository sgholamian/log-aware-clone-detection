//,temp,CompositeDestinationSendWhenNotMatchedTest.java,315,351,temp,CompositeDestinationSendWhenNotMatchedTest.java,128,164
//,3
public class xxx {
	@Test
	public void testForwardOnlyFalseSendWhenMatchedTrue1() throws Exception {
		if (connection == null) {
			connection = createConnection();
		}
		connection.start();

		ConsumerBean messageList1 = new ConsumerBean();
		ConsumerBean messageList2 = new ConsumerBean();
		messageList1.setVerbose(true);
		messageList2.setVerbose(true);

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Destination producerDestination = new ActiveMQQueue("A.D");
		Destination destination1 =new ActiveMQQueue("A.D");
		Destination destination2 = new ActiveMQQueue("A.E");

		LOG.info("Sending to: " + producerDestination);
		LOG.info("Consuming from: " + destination1 + " and " + destination2);

		MessageConsumer c1 = session.createConsumer(destination1);
		MessageConsumer c2 = session.createConsumer(destination2);

		c1.setMessageListener(messageList1);
		c2.setMessageListener(messageList2);

		// create topic producer
		MessageProducer producer = session.createProducer(producerDestination);
		assertNotNull(producer);

		producer.send(createMessage(session, "tes13"));
	
		messageList1.assertMessagesArrived(1);
		messageList2.assertMessagesArrived(0);	

	}

};