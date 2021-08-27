//,temp,Jt400DataQueueConsumer.java,142,168,temp,Jt400DataQueueConsumer.java,118,140
//,3
public class xxx {
    private Exchange receive(KeyedDataQueue queue, long timeout) throws Exception {
        String key = getEndpoint().getSearchKey();
        String searchType = getEndpoint().getSearchType().name();
        KeyedDataQueueEntry entry;
        if (timeout >= 0) {
            int seconds = (int) timeout / 1000;
            LOG.trace("Reading from data queue: {} with {} seconds timeout", queue.getName(), seconds);
            entry = queue.read(key, seconds, searchType);
        } else {
            LOG.trace("Reading from data queue: {} with no timeout", queue.getName());
            entry = queue.read(key, -1, searchType);
        }

        Exchange exchange = createExchange(true);
        if (entry != null) {
            exchange.getIn().setHeader(Jt400Endpoint.SENDER_INFORMATION, entry.getSenderInformation());
            if (getEndpoint().getFormat() == Jt400Configuration.Format.binary) {
                exchange.getIn().setBody(entry.getData());
                exchange.getIn().setHeader(Jt400Endpoint.KEY, entry.getKey());
            } else {
                exchange.getIn().setBody(entry.getString());
                exchange.getIn().setHeader(Jt400Endpoint.KEY, entry.getKeyString());
            }
            return exchange;
        }
        return null;
    }

};