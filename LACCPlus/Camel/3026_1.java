//,temp,CamelJGroupsReceiver.java,70,81,temp,CamelJGroupsReceiver.java,50,68
//,3
public class xxx {
    @Override
    public void receive(Message message) {
        Exchange exchange = endpoint.createExchange(message);
        try {
            LOG.debug("Processing message: {}", message);
            processor.process(exchange, doneSync -> {
                // noop
            });
        } catch (Exception e) {
            throw new JGroupsException("Error in consumer while dispatching exchange containing message " + message, e);
        }
    }

};