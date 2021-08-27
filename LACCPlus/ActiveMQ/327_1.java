//,temp,AmqpSessionTest.java,84,118,temp,AmqpReceiverTest.java,202,237
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testSessionClosedDoesNotGetReceiverDetachFromRemote() throws Exception {
        AmqpClient client = createAmqpClient();
        assertNotNull(client);

        client.setValidator(new AmqpValidator() {

            @Override
            public void inspectClosedResource(Session session) {
                LOG.info("Session closed: {}", session.getContext());
            }

            @Override
            public void inspectDetachedResource(Receiver receiver) {
                markAsInvalid("Broker should not detach receiver linked to closed session.");
            }

            @Override
            public void inspectClosedResource(Receiver receiver) {
                markAsInvalid("Broker should not close receiver linked to closed session.");
            }
        });

        AmqpConnection connection = trackConnection(client.connect());
        assertNotNull(connection);
        AmqpSession session = connection.createSession();
        assertNotNull(session);
        AmqpReceiver receiver = session.createReceiver("queue://" + getTestName());
        assertNotNull(receiver);

        session.close();

        connection.getStateInspector().assertValid();
        connection.close();
    }

};