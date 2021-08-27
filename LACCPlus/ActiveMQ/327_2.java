//,temp,AmqpSessionTest.java,84,118,temp,AmqpReceiverTest.java,202,237
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testCreateQueueReceiverWithJMSSelector() throws Exception {
        AmqpClient client = createAmqpClient();

        client.setValidator(new AmqpValidator() {

            @SuppressWarnings("unchecked")
            @Override
            public void inspectOpenedResource(Receiver receiver) {
                LOG.info("Receiver opened: {}", receiver);

                if (receiver.getRemoteSource() == null) {
                    markAsInvalid("Link opened with null source.");
                }

                Source source = (Source) receiver.getRemoteSource();
                Map<Symbol, Object> filters = source.getFilter();

                if (findFilter(filters, JMS_SELECTOR_FILTER_IDS) == null) {
                    markAsInvalid("Broker did not return the JMS Filter on Attach");
                }
            }
        });

        AmqpConnection connection = trackConnection(client.connect());
        AmqpSession session = connection.createSession();

        assertEquals(0, brokerService.getAdminView().getQueues().length);

        session.createReceiver("queue://" + getTestName(), "JMSPriority > 8");

        assertEquals(1, brokerService.getAdminView().getQueueSubscribers().length);

        connection.getStateInspector().assertValid();
        connection.close();
    }

};