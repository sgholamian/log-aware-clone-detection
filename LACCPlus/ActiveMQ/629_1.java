//,temp,AmqpSession.java,114,127,temp,AmqpSession.java,103,112
//,3
public class xxx {
    @Override
    public void close() {
        LOG.debug("Session {} closed", getSessionId());

        connection.sendToActiveMQ(new RemoveInfo(getSessionId()), new ResponseHandler() {

            @Override
            public void onResponse(AmqpProtocolConverter converter, Response response) throws IOException {
                getEndpoint().setContext(null);
                getEndpoint().close();
                getEndpoint().free();
            }
        });
    }

};