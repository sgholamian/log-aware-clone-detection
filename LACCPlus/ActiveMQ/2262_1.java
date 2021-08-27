//,temp,AmqpReceiverTest.java,560,604,temp,AmqpReceiverTest.java,239,274
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testUnsupportedFiltersAreNotListedAsSupported() throws Exception {
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

                if (findFilter(filters, AmqpUnknownFilterType.UNKNOWN_FILTER_IDS) != null) {
                    markAsInvalid("Broker should not return unsupported filter on attach.");
                }
            }
        });

        Map<Symbol, DescribedType> filters = new HashMap<>();
        filters.put(AmqpUnknownFilterType.UNKNOWN_FILTER_NAME, AmqpUnknownFilterType.UNKOWN_FILTER);

        Source source = new Source();
        source.setAddress("queue://" + getTestName());
        source.setFilter(filters);
        source.setDurable(TerminusDurability.NONE);
        source.setExpiryPolicy(TerminusExpiryPolicy.LINK_DETACH);

        AmqpConnection connection = trackConnection(client.connect());
        AmqpSession session = connection.createSession();

        assertEquals(0, brokerService.getAdminView().getQueues().length);

        session.createReceiver(source);

        assertEquals(1, brokerService.getAdminView().getQueueSubscribers().length);

        connection.getStateInspector().assertValid();
        connection.close();
    }

};