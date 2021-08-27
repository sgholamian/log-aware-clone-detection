//,temp,DispatchMultipleConsumersTest.java,190,204,temp,DispatchMultipleConsumersTest.java,128,139
//,3
public class xxx {
        public ProducerThread(ActiveMQConnectionFactory connFactory, int count, String name) {
            super();
            this.count = count;
            this.setName(name);
            logger.trace("Created new producer thread:" + name);
            try {
                conn = connFactory.createConnection();
                conn.start();
                session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
                producer = session.createProducer(dest);
                start();
            } catch (JMSException e) {
                logger.error("Failed to start producer thread:" + name, e);
            }
        }

};