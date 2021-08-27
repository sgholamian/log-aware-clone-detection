//,temp,AmqpTempDestinationTest.java,94,113,temp,AmqpTempDestinationTest.java,63,82
//,2
public class xxx {
    protected void doTestCannotCreateSenderWithNamedTempDestination(boolean topic) throws Exception {

        AmqpClient client = createAmqpClient();
        AmqpConnection connection = trackConnection(client.connect());
        AmqpSession session = connection.createSession();

        String address = null;
        if (topic) {
            address = "temp-topic://" + getTestName();
        } else {
            address = "temp-queue://" + getTestName();
        }

        try {
            session.createSender(address);
            fail("Should not be able to create sender to a temp destination that doesn't exist.");
        } catch (Exception ex) {
            LOG.info("Error creating sender: {}", ex.getMessage());
        }
    }

};