//,temp,Queue.java,614,761,temp,Topic.java,363,507
//,3
public class xxx {
    @Override
    public void send(final ProducerBrokerExchange producerExchange, final Message message) throws Exception {
        final ConnectionContext context = producerExchange.getConnectionContext();

        final ProducerInfo producerInfo = producerExchange.getProducerState().getInfo();
        producerExchange.incrementSend();
        final boolean sendProducerAck = !message.isResponseRequired() && producerInfo.getWindowSize() > 0
                && !context.isInRecoveryMode();

        message.setRegionDestination(this);

        // There is delay between the client sending it and it arriving at the
        // destination.. it may have expired.
        if (message.isExpired()) {
            broker.messageExpired(context, message, null);
            getDestinationStatistics().getExpired().increment();
            if (sendProducerAck) {
                ProducerAck ack = new ProducerAck(producerInfo.getProducerId(), message.getSize());
                context.getConnection().dispatchAsync(ack);
            }
            return;
        }

        if (memoryUsage.isFull()) {
            isFull(context, memoryUsage);
            fastProducer(context, producerInfo);

            if (isProducerFlowControl() && context.isProducerFlowControl()) {

                if (isFlowControlLogRequired()) {
                    LOG.warn("{}, Usage Manager memory limit reached {}. Producers will be throttled to the rate at which messages are removed from this destination to prevent flooding it. See http://activemq.apache.org/producer-flow-control.html for more info.",
                            getActiveMQDestination().getQualifiedName(), memoryUsage.getLimit());
                } else {
                    LOG.debug("{}, Usage Manager memory limit reached {}. Producers will be throttled to the rate at which messages are removed from this destination to prevent flooding it. See http://activemq.apache.org/producer-flow-control.html for more info.",
                            getActiveMQDestination().getQualifiedName(), memoryUsage.getLimit());
                }

                if (!context.isNetworkConnection() && systemUsage.isSendFailIfNoSpace()) {
                    throw new javax.jms.ResourceAllocationException("Usage Manager memory limit ("
                            + memoryUsage.getLimit() + ") reached. Rejecting send for producer (" + message.getProducerId()
                            + ") to prevent flooding " + getActiveMQDestination().getQualifiedName() + "."
                            + " See http://activemq.apache.org/producer-flow-control.html for more info");
                }

                // We can avoid blocking due to low usage if the producer is sending a sync message or
                // if it is using a producer window
                if (producerInfo.getWindowSize() > 0 || message.isResponseRequired()) {
                    synchronized (messagesWaitingForSpace) {
                        messagesWaitingForSpace.add(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    // While waiting for space to free up...
                                    // the transaction may be done
                                    if (message.isInTransaction()) {
                                        if (context.getTransaction() == null || context.getTransaction().getState() > IN_USE_STATE) {
                                            throw new JMSException("Send transaction completed while waiting for space");
                                        }
                                    }

                                    // the message may have expired.
                                    if (message.isExpired()) {
                                        broker.messageExpired(context, message, null);
                                        getDestinationStatistics().getExpired().increment();
                                    } else {
                                        doMessageSend(producerExchange, message);
                                    }

                                    if (sendProducerAck) {
                                        ProducerAck ack = new ProducerAck(producerInfo.getProducerId(), message
                                                .getSize());
                                        context.getConnection().dispatchAsync(ack);
                                    } else {
                                        Response response = new Response();
                                        response.setCorrelationId(message.getCommandId());
                                        context.getConnection().dispatchAsync(response);
                                    }

                                } catch (Exception e) {
                                    if (!sendProducerAck && !context.isInRecoveryMode()) {
                                        ExceptionResponse response = new ExceptionResponse(e);
                                        response.setCorrelationId(message.getCommandId());
                                        context.getConnection().dispatchAsync(response);
                                    }
                                }
                            }
                        });

                        registerCallbackForNotFullNotification();
                        context.setDontSendReponse(true);
                        return;
                    }

                } else {
                    // Producer flow control cannot be used, so we have do the flow control
                    // at the broker by blocking this thread until there is space available.

                    if (memoryUsage.isFull()) {
                        if (context.isInTransaction()) {

                            int count = 0;
                            while (!memoryUsage.waitForSpace(1000)) {
                                if (context.getStopping().get()) {
                                    throw new IOException("Connection closed, send aborted.");
                                }
                                if (count > 2 && context.isInTransaction()) {
                                    count = 0;
                                    int size = context.getTransaction().size();
                                    LOG.warn("Waiting for space to send transacted message - transaction elements = {} need more space to commit. Message = {}", size, message);
                                }
                                count++;
                            }
                        } else {
                            waitForSpace(
                                    context,
                                    producerExchange,
                                    memoryUsage,
                                    "Usage Manager Memory Usage limit reached. Stopping producer ("
                                            + message.getProducerId()
                                            + ") to prevent flooding "
                                            + getActiveMQDestination().getQualifiedName()
                                            + "."
                                            + " See http://activemq.apache.org/producer-flow-control.html for more info");
                        }
                    }

                    // The usage manager could have delayed us by the time
                    // we unblock the message could have expired..
                    if (message.isExpired()) {
                        getDestinationStatistics().getExpired().increment();
                        LOG.debug("Expired message: {}", message);
                        return;
                    }
                }
            }
        }

        doMessageSend(producerExchange, message);
        messageDelivered(context, message);
        if (sendProducerAck) {
            ProducerAck ack = new ProducerAck(producerInfo.getProducerId(), message.getSize());
            context.getConnection().dispatchAsync(ack);
        }
    }

};