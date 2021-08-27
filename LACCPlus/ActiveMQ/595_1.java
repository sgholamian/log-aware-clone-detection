//,temp,JMSClientContext.java,135,152,temp,JMSClientContext.java,61,78
//,2
public class xxx {
    public QueueConnection createQueueConnection(URI remoteURI, String username, String password, String clientId, boolean syncPublish) throws JMSException {
        QueueConnectionFactory factory = createQueueConnectionFactory(remoteURI, username, password, syncPublish);

        QueueConnection connection = factory.createQueueConnection();
        connection.setExceptionListener(new ExceptionListener() {
            @Override
            public void onException(JMSException exception) {
                LOG.error("Unexpected exception ", exception);
                exception.printStackTrace();
            }
        });

        if (clientId != null && !clientId.isEmpty()) {
            connection.setClientID(clientId);
        }

        return connection;
    }

};