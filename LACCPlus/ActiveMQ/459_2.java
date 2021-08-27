//,temp,SimpleQueueReceiver.java,55,129,temp,SimpleConsumer.java,56,129
//,3
public class xxx {
    public static void main(String[] args) {
        String destinationName = null;
        Context jndiContext = null;
        ConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session session = null;
        Destination destination = null;
        MessageConsumer consumer = null;

        /*
         * Read destination name from command line and display it.
         */
        if (args.length != 1) {
            LOG.info("Usage: java SimpleConsumer <destination-name>");
            System.exit(1);
        }
        destinationName = args[0];
        LOG.info("Destination name is " + destinationName);

        /*
         * Create a JNDI API InitialContext object
         */
        try {
            jndiContext = new InitialContext();
        } catch (NamingException e) {
            LOG.info("Could not create JNDI API " + "context: " + e.toString());
            System.exit(1);
        }

        /*
         * Look up connection factory and destination.
         */
        try {
            connectionFactory = (ConnectionFactory)jndiContext.lookup("ConnectionFactory");
            destination = (Destination)jndiContext.lookup(destinationName);
        } catch (NamingException e) {
            LOG.info("JNDI API lookup failed: " + e.toString());
            System.exit(1);
        }

        /*
         * Create connection. Create session from connection; false means
         * session is not transacted. Create receiver, then start message
         * delivery. Receive all text messages from destination until a non-text
         * message is received indicating end of message stream. Close
         * connection.
         */
        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            consumer = session.createConsumer(destination);
            connection.start();
            while (true) {
                Message m = consumer.receive(1);
                if (m != null) {
                    if (m instanceof TextMessage) {
                        TextMessage message = (TextMessage)m;
                        LOG.info("Reading message: " + message.getText());
                    } else {
                        break;
                    }
                }
            }
        } catch (JMSException e) {
            LOG.info("Exception occurred: " + e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                }
            }
        }
    }

};