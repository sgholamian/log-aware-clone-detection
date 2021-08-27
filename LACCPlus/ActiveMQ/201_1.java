//,temp,DurableSubscriptionHangTestCase.java,97,109,temp,AMQ6387Test.java,173,185
//,3
public class xxx {
	private void registerDurableSubscription() throws JMSException
	{
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://" + brokerName);
		TopicConnection connection = connectionFactory.createTopicConnection();
		connection.setClientID(clientID);
		TopicSession topicSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = topicSession.createTopic(topicName);
		TopicSubscriber durableSubscriber = topicSession.createDurableSubscriber(topic, durableSubName);
		connection.start();
		durableSubscriber.close();
		connection.close();
		LOG.info("Durable Sub Registered");
	}

};