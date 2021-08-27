//,temp,JMSClientContext.java,135,152,temp,JMSClientContext.java,61,78
//,2
public class xxx {
    public Connection createConnection(URI remoteURI, String username, String password, String clientId, boolean syncPublish) throws JMSException {
        ConnectionFactory factory = createConnectionFactory(remoteURI, username, password, syncPublish);

        Connection connection = factory.createConnection();
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