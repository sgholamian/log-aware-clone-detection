//,temp,SimpleProducer.java,62,136,temp,SimpleQueueSender.java,59,134
//,3
public class xxx {
    public static void main(String[] args) {
        String queueName = null;
        Context jndiContext = null;
        QueueConnectionFactory queueConnectionFactory = null;
        QueueConnection queueConnection = null;
        QueueSession queueSession = null;
        Queue queue = null;
        QueueSender queueSender = null;
        TextMessage message = null;
        final int numMsgs;

        if ((args.length < 1) || (args.length > 2)) {
            LOG.info("Usage: java SimpleQueueSender " + "<queue-name> [<number-of-messages>]");
            System.exit(1);
        }
        queueName = args[0];
        LOG.info("Queue name is " + queueName);
        if (args.length == 2) {
            numMsgs = (new Integer(args[1])).intValue();
        } else {
            numMsgs = 1;
        }

        /*
         * Create a JNDI API InitialContext object if none exists yet.
         */
        try {
            jndiContext = new InitialContext();
        } catch (NamingException e) {
            LOG.info("Could not create JNDI API context: " + e.toString());
            System.exit(1);
        }

        /*
         * Look up connection factory and queue. If either does not exist, exit.
         */
        try {
            queueConnectionFactory = (QueueConnectionFactory)jndiContext.lookup("QueueConnectionFactory");
            queue = (Queue)jndiContext.lookup(queueName);
        } catch (NamingException e) {
            LOG.info("JNDI API lookup failed: " + e);
            System.exit(1);
        }

        /*
         * Create connection. Create session from connection; false means
         * session is not transacted. Create sender and text message. Send
         * messages, varying text slightly. Send end-of-messages message.
         * Finally, close connection.
         */
        try {
            queueConnection = queueConnectionFactory.createQueueConnection();
            queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            queueSender = queueSession.createSender(queue);
            message = queueSession.createTextMessage();
            for (int i = 0; i < numMsgs; i++) {
                message.setText("This is message " + (i + 1));
                LOG.info("Sending message: " + message.getText());
                queueSender.send(message);
            }

            /*
             * Send a non-text control message indicating end of messages.
             */
            queueSender.send(queueSession.createMessage());
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