//,temp,SimpleQueueReceiver.java,55,129,temp,SimpleConsumer.java,56,129
//,3
public class xxx {
    public static void main(String[] args) {
        String queueName = null;
        Context jndiContext = null;
        QueueConnectionFactory queueConnectionFactory = null;
        QueueConnection queueConnection = null;
        QueueSession queueSession = null;
        Queue queue = null;
        QueueReceiver queueReceiver = null;
        TextMessage message = null;

        /*
         * Read queue name from command line and display it.
         */
        if (args.length != 1) {
            LOG.info("Usage: java " + "SimpleQueueReceiver <queue-name>");
            System.exit(1);
        }
        queueName = args[0];
        LOG.info("Queue name is " + queueName);

        /*
         * Create a JNDI API InitialContext object if none exists yet.
         */
        try {
            jndiContext = new InitialContext();
        } catch (NamingException e) {
            LOG.info("Could not create JNDI API " + "context: " + e.toString());
            System.exit(1);
        }

        /*
         * Look up connection factory and queue. If either does not exist, exit.
         */
        try {
            queueConnectionFactory = (QueueConnectionFactory)jndiContext.lookup("QueueConnectionFactory");
            queue = (Queue)jndiContext.lookup(queueName);
        } catch (NamingException e) {
            LOG.info("JNDI API lookup failed: " + e.toString());
            System.exit(1);
        }

        /*
         * Create connection. Create session from connection; false means
         * session is not transacted. Create receiver, then start message
         * delivery. Receive all text messages from queue until a non-text
         * message is received indicating end of message stream. Close
         * connection.
         */
        try {
            queueConnection = queueConnectionFactory.createQueueConnection();
            queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            queueReceiver = queueSession.createReceiver(queue);
            queueConnection.start();
            while (true) {
                Message m = queueReceiver.receive(1);
                if (m != null) {
                    if (m instanceof TextMessage) {
                        message = (TextMessage)m;
                        LOG.info("Reading message: " + message.getText());
                    } else {
                        break;
                    }
                }
            }
        } catch (JMSException e) {
            LOG.info("Exception occurred: " + e.toString());
        } finally {
            if (queueConnection != null) {
                try {
                    queueConnection.close();
                } catch (JMSException e) {
                }
            }
        }
    }

};