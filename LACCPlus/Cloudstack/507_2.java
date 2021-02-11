//,temp,RabbitMQEventBus.java,557,624,temp,RabbitMQEventBus.java,185,248
//,3
public class xxx {
    @Override
    public UUID subscribe(EventTopic topic, EventSubscriber subscriber) throws EventBusException {

        if (subscriber == null || topic == null) {
            throw new EventBusException("Invalid EventSubscriber/EventTopic object passed.");
        }

        // create a UUID, that will be used for managing subscriptions and also used as queue name
        // for on the queue used for the subscriber on the AMQP broker
        UUID queueId = UUID.randomUUID();
        String queueName = queueId.toString();

        try {
            String bindingKey = createBindingKey(topic);

            // store the subscriber details before creating channel
            s_subscribers.put(queueName, new Ternary(bindingKey, null, subscriber));

            // create a channel dedicated for this subscription
            Connection connection = getConnection();
            Channel channel = createChannel(connection);

            // create a queue and bind it to the exchange with binding key formed from event topic
            createExchange(channel, amqpExchangeName);
            channel.queueDeclare(queueName, false, false, false, null);
            channel.queueBind(queueName, amqpExchangeName, bindingKey);

            // register a callback handler to receive the events that a subscriber subscribed to
            channel.basicConsume(queueName, s_autoAck, queueName, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String queueName, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    Ternary<String, Channel, EventSubscriber> queueDetails = s_subscribers.get(queueName);
                    if (queueDetails != null) {
                        EventSubscriber subscriber = queueDetails.third();
                        String routingKey = envelope.getRoutingKey();
                        String eventSource = getEventSourceFromRoutingKey(routingKey);
                        String eventCategory = getEventCategoryFromRoutingKey(routingKey);
                        String eventType = getEventTypeFromRoutingKey(routingKey);
                        String resourceType = getResourceTypeFromRoutingKey(routingKey);
                        String resourceUUID = getResourceUUIDFromRoutingKey(routingKey);
                        Event event = new Event(eventSource, eventCategory, eventType, resourceType, resourceUUID);
                        event.setDescription(new String(body));

                        // deliver the event to call back object provided by subscriber
                        subscriber.onEvent(event);
                    }
                }
            });

            // update the channel details for the subscription
            Ternary<String, Channel, EventSubscriber> queueDetails = s_subscribers.get(queueName);
            queueDetails.second(channel);
            s_subscribers.put(queueName, queueDetails);

        } catch (AlreadyClosedException closedException) {
            s_logger.warn("Connection to AMQP service is lost. Subscription:" + queueName + " will be active after reconnection", closedException);
        } catch (ConnectException connectException) {
            s_logger.warn("Connection to AMQP service is lost. Subscription:" + queueName + " will be active after reconnection", connectException);
        } catch (Exception e) {
            throw new EventBusException("Failed to subscribe to event due to " + e.getMessage());
        }

        return queueId;
    }

};