//,temp,AMQ4636Test.java,190,228,temp,ConnectionPerMessageTest.java,41,80
//,3
public class xxx {
	public void testConnectionPerMessage() throws Exception {
		final String topicName = "test.topic";

		LOG.info("Initializing connection factory for JMS to URL: "
				+ bindAddress);
		final ActiveMQConnectionFactory normalFactory = new ActiveMQConnectionFactory();
		normalFactory.setBrokerURL(bindAddress);
		for (int i = 0; i < COUNT; i++) {

			if (i % 100 == 0) {
				LOG.info(new Integer(i).toString());
			}

			Connection conn = null;
			try {

				conn = normalFactory.createConnection();
				final Session session = conn.createSession(false,
						Session.AUTO_ACKNOWLEDGE);
				final Topic topic = session.createTopic(topicName);
				final MessageProducer producer = session.createProducer(topic);
				producer.setDeliveryMode(DeliveryMode.PERSISTENT);

				final MapMessage m = session.createMapMessage();
				m.setInt("hey", i);

				producer.send(m);

			} catch (JMSException e) {
				LOG.warn(e.getMessage(), e);
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (JMSException e) {
						LOG.warn(e.getMessage(), e);
					}
			}
		}
	}

};