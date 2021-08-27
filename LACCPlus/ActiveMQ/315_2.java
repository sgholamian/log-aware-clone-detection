//,temp,ObjectMessageNotSerializableTest.java,167,190,temp,ObjectMessageNotSerializableTest.java,72,94
//,3
public class xxx {
			public void run() {
				try {
                    ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
                    factory.setOptimizedMessageDispatch(true);
                    factory.setObjectMessageSerializationDefered(true);
                    factory.setCopyMessageOnSend(false);

                    Connection connection = factory.createConnection();
		            Session session = (ActiveMQSession)connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		            MessageConsumer consumer = session.createConsumer(destination);
		            connection.start();
                    consumerStarted.countDown();
                    ActiveMQObjectMessage message = (ActiveMQObjectMessage)consumer.receive(30000);
                    if ( message != null ) {
                        MyObject object = (MyObject)message.getObject();
                        LOG.info("Got message " + object.getMessage());
                        numReceived.incrementAndGet();
                    }
					consumer.close();
				} catch (Throwable ex) {
					exceptions.add(ex);
				}
			}

};