//,temp,DockerEventsConsumer.java,87,98,temp,PahoMqtt5Consumer.java,104,112
//,3
public class xxx {
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                LOG.debug("Message arrived on topic: {} -> {}", topic, message);
                Exchange exchange = createExchange(message, topic);

                // use default consumer callback
                AsyncCallback cb = defaultConsumerCallback(exchange, true);
                getAsyncProcessor().process(exchange, cb);
            }

};