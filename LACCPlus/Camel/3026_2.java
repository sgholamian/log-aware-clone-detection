//,temp,CamelJGroupsReceiver.java,70,81,temp,CamelJGroupsReceiver.java,50,68
//,3
public class xxx {
    @Override
    public void viewAccepted(View view) {
        if (endpoint.isEnableViewMessages()) {
            Exchange exchange = createExchange(view);
            try {
                LOG.debug("Processing view: {}", view);
                processor.process(exchange, new AsyncCallback() {
                    @Override
                    public void done(boolean doneSync) {
                        // noop
                    }
                });
            } catch (Exception e) {
                throw new JGroupsException("Error in consumer while dispatching exchange containing view " + view, e);
            }
        } else {
            LOG.debug("Option enableViewMessages is set to false. Skipping processing of the view: {}", view);
        }
    }

};