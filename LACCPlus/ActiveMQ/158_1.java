//,temp,AmqpReceiverTest.java,566,581,temp,AmqpReceiverTest.java,245,260
//,3
public class xxx {
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

};