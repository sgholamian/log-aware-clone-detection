//,temp,DockerEventsConsumer.java,87,98,temp,PahoMqtt5Consumer.java,104,112
//,3
public class xxx {
        @Override
        public void onNext(Event event) {
            LOG.debug("Received Docker Event: {}", event);

            final Exchange exchange = createExchange(true);
            Message message = exchange.getIn();
            message.setBody(event);

            // use default consumer callback
            AsyncCallback cb = defaultConsumerCallback(exchange, true);
            getAsyncProcessor().process(exchange, cb);
        }

};