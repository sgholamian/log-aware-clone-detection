//,temp,DispatchMultipleConsumersTest.java,190,204,temp,DispatchMultipleConsumersTest.java,128,139
//,3
public class xxx {
        public ConsumerThread(Connection conn, String name) {
            super();
            this.setName(name);
            logger.trace("Created new consumer thread:" + name);
            try {
                session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
                consumer = session.createConsumer(dest);
                start();
            } catch (JMSException e) {
                logger.error("Failed to start consumer thread:" + name, e);
            }
        }

};