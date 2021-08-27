//,temp,Jt400DataQueueConsumer.java,142,168,temp,Jt400DataQueueConsumer.java,118,140
//,3
public class xxx {
    private Exchange receive(DataQueue queue, long timeout) throws Exception {
        DataQueueEntry entry;
        if (timeout >= 0) {
            int seconds = (int) timeout / 1000;
            LOG.trace("Reading from data queue: {} with {} seconds timeout", queue.getName(), seconds);
            entry = queue.read(seconds);
        } else {
            LOG.trace("Reading from data queue: {} with no timeout", queue.getName());
            entry = queue.read(-1);
        }

        Exchange exchange = getEndpoint().createExchange();
        if (entry != null) {
            exchange.getIn().setHeader(Jt400Endpoint.SENDER_INFORMATION, entry.getSenderInformation());
            if (getEndpoint().getFormat() == Jt400Configuration.Format.binary) {
                exchange.getIn().setBody(entry.getData());
            } else {
                exchange.getIn().setBody(entry.getString());
            }
            return exchange;
        }
        return null;
    }

};