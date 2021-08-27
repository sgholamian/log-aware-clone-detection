//,temp,CdiEventProducer.java,39,48,temp,TagConsumerTest.java,54,59
//,3
public class xxx {
    @Override
    public void process(Exchange exchange) {
        logger.debug("Firing CDI event [{}] with {}", event, this);
        // TODO: leverage Camel type converter mechanism based on the endpoint type
        // The EventMetadata injection point will be that of the event which is not very useful
        // for the end user. Using BeanManager.fire would be a way to hide that internal though
        // it will be necessary to check whether the exchange event type is assignable to the
        // endpoint event type.
        event.fire((T) exchange.getIn().getBody());
    }

};