//,temp,DockerStatsConsumer.java,77,88,temp,Web3jConsumer.java,168,173
//,3
public class xxx {
        @Override
        public void onNext(Statistics statistics) {
            LOGGER.debug("Received Docker Statistics Event: {}", statistics);

            final Exchange exchange = createExchange(true);
            Message message = exchange.getIn();
            message.setBody(statistics);

            // use default consumer callback
            AsyncCallback cb = defaultConsumerCallback(exchange, true);
            getAsyncProcessor().process(exchange, cb);
        }

};