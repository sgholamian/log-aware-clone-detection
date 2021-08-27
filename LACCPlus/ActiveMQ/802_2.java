//,temp,MQTTVirtualTopicSubscriptionStrategy.java,226,245,temp,AmqpConnection.java,777,793
//,3
public class xxx {
    void deleteTemporaryDestination(ActiveMQTempDestination destination) {
        DestinationInfo info = new DestinationInfo();
        info.setConnectionId(connectionId);
        info.setOperationType(DestinationInfo.REMOVE_OPERATION_TYPE);
        info.setDestination(destination);

        sendToActiveMQ(info, new ResponseHandler() {

            @Override
            public void onResponse(AmqpProtocolConverter converter, Response response) throws IOException {
                if (response.isException()) {
                    Throwable exception = ((ExceptionResponse) response).getException();
                    LOG.debug("Error during temp destination removeal: {}", exception.getMessage());
                }
            }
        });
    }

};