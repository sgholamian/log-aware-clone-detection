//,temp,RabbitMQEventBus.java,557,624,temp,RabbitMQEventBus.java,185,248
//,3
public class xxx {
        @Override
        protected void runInContext() {

            while (!connected) {
                try {
                    Thread.sleep(retryInterval);
                } catch (InterruptedException ie) {
                    // ignore timer interrupts
                }

                try {
                    try {
                        connection = createConnection();
                        connected = true;
                    } catch (IOException ie) {
                        continue; // can't establish connection to AMQP server yet, so continue
                    }

                    // prepare consumer on AMQP server for each of subscriber
                    for (String subscriberId : s_subscribers.keySet()) {
                        Ternary<String, Channel, EventSubscriber> subscriberDetails = s_subscribers.get(subscriberId);
                        String bindingKey = subscriberDetails.first();
                        EventSubscriber subscriber = subscriberDetails.third();

                        /** create a queue with subscriber ID as queue name and bind it to the exchange
                         *  with binding key formed from event topic
                         */
                        Channel channel = createChannel(connection);
                        createExchange(channel, amqpExchangeName);
                        channel.queueDeclare(subscriberId, false, false, false, null);
                        channel.queueBind(subscriberId, amqpExchangeName, bindingKey);

                        // register a callback handler to receive the events that a subscriber subscribed to
                        channel.basicConsume(subscriberId, s_autoAck, subscriberId, new DefaultConsumer(channel) {
                            @Override
                            public void handleDelivery(String queueName, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                                Ternary<String, Channel, EventSubscriber> subscriberDetails = s_subscribers.get(queueName); // queue name == subscriber ID

                                if (subscriberDetails != null) {
                                    EventSubscriber subscriber = subscriberDetails.third();
                                    String routingKey = envelope.getRoutingKey();
                                    String eventSource = getEventSourceFromRoutingKey(routingKey);
                                    String eventCategory = getEventCategoryFromRoutingKey(routingKey);
                                    String eventType = getEventTypeFromRoutingKey(routingKey);
                                    String resourceType = getResourceTypeFromRoutingKey(routingKey);
                                    String resourceUUID = getResourceUUIDFromRoutingKey(routingKey);

                                    // create event object from the message details obtained from AMQP server
                                    Event event = new Event(eventSource, eventCategory, eventType, resourceType, resourceUUID);
                                    event.setDescription(new String(body));

                                    // deliver the event to call back object provided by subscriber
                                    subscriber.onEvent(event);
                                }
                            }
                        });

                        // update the channel details for the subscription
                        subscriberDetails.second(channel);
                        s_subscribers.put(subscriberId, subscriberDetails);
                    }
                } catch (Exception e) {
                    s_logger.warn("Failed to recreate queues and binding for the subscribers due to " + e.getMessage());
                }
            }
            return;
        }

};