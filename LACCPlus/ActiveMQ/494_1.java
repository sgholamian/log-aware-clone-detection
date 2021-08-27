//,temp,Queue.java,614,761,temp,Topic.java,363,507
//,3
public class xxx {
    @Override
    public void send(final ProducerBrokerExchange producerExchange, final Message message) throws Exception {
        final ConnectionContext context = producerExchange.getConnectionContext();
        // There is delay between the client sending it and it arriving at the
        // destination.. it may have expired.
        message.setRegionDestination(this);
        ProducerState state = producerExchange.getProducerState();
        if (state == null) {
            LOG.warn("Send failed for: {}, missing producer state for: {}", message, producerExchange);
            throw new JMSException("Cannot send message to " + getActiveMQDestination() + " with invalid (null) producer state");
        }
        final ProducerInfo producerInfo = producerExchange.getProducerState().getInfo();
        final boolean sendProducerAck = !message.isResponseRequired() && producerInfo.getWindowSize() > 0
                && !context.isInRecoveryMode();
        if (message.isExpired()) {
            // message not stored - or added to stats yet - so chuck here
            broker.getRoot().messageExpired(context, message, null);
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
                    LOG.warn("Usage Manager Memory Limit ({}) reached on {}, size {}. Producers will be throttled to the rate at which messages are removed from this destination to prevent flooding it. See http://activemq.apache.org/producer-flow-control.html for more info.",
                                memoryUsage.getLimit(), getActiveMQDestination().getQualifiedName(), destinationStatistics.getMessages().getCount());
                } else {
                    LOG.debug("Usage Manager Memory Limit ({}) reached on {}, size {}. Producers will be throttled to the rate at which messages are removed from this destination to prevent flooding it. See http://activemq.apache.org/producer-flow-control.html for more info.",
                            memoryUsage.getLimit(), getActiveMQDestination().getQualifiedName(), destinationStatistics.getMessages().getCount());
                }
                if (!context.isNetworkConnection() && systemUsage.isSendFailIfNoSpace()) {
                    ResourceAllocationException resourceAllocationException = sendMemAllocationException;
                    if (resourceAllocationException == null) {
                        synchronized (this) {
                            resourceAllocationException = sendMemAllocationException;
                            if (resourceAllocationException == null) {
                                sendMemAllocationException = resourceAllocationException = new ResourceAllocationException("Usage Manager Memory Limit reached on "
                                        + getActiveMQDestination().getQualifiedName() + "."
                                        + " See http://activemq.apache.org/producer-flow-control.html for more info");
                            }
                        }
                    }
                    throw resourceAllocationException;
                }

                // We can avoid blocking due to low usage if the producer is
                // sending
                // a sync message or if it is using a producer window
                if (producerInfo.getWindowSize() > 0 || message.isResponseRequired()) {
                    // copy the exchange state since the context will be
                    // modified while we are waiting
                    // for space.
                    final ProducerBrokerExchange producerExchangeCopy = producerExchange.copy();
                    synchronized (messagesWaitingForSpace) {
                     // Start flow control timeout task
                        // Prevent trying to start it multiple times
                        if (!flowControlTimeoutTask.isAlive()) {
                            flowControlTimeoutTask.setName(getName()+" Producer Flow Control Timeout Task");
                            flowControlTimeoutTask.start();
                        }
                        messagesWaitingForSpace.put(message.getMessageId(), new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    // While waiting for space to free up... the
                                    // transaction may be done
                                    if (message.isInTransaction()) {
                                        if (context.getTransaction() == null || context.getTransaction().getState() > IN_USE_STATE) {
                                            throw new JMSException("Send transaction completed while waiting for space");
                                        }
                                    }

                                    // the message may have expired.
                                    if (message.isExpired()) {
                                        LOG.error("message expired waiting for space");
                                        broker.messageExpired(context, message, null);
                                        destinationStatistics.getExpired().increment();
                                    } else {
                                        doMessageSend(producerExchangeCopy, message);
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
                                    if (!sendProducerAck && !context.isInRecoveryMode() && !brokerService.isStopping()) {
                                        ExceptionResponse response = new ExceptionResponse(e);
                                        response.setCorrelationId(message.getCommandId());
                                        context.getConnection().dispatchAsync(response);
                                    } else {
                                        LOG.debug("unexpected exception on deferred send of: {}", message, e);
                                    }
                                } finally {
                                    getDestinationStatistics().getBlockedSends().decrement();
                                    producerExchangeCopy.blockingOnFlowControl(false);
                                }
                            }
                        });

                        getDestinationStatistics().getBlockedSends().increment();
                        producerExchange.blockingOnFlowControl(true);
                        if (!context.isNetworkConnection() && systemUsage.getSendFailIfNoSpaceAfterTimeout() != 0) {
                            flowControlTimeoutMessages.add(new TimeoutMessage(message, context, systemUsage
                                    .getSendFailIfNoSpaceAfterTimeout()));
                        }

                        registerCallbackForNotFullNotification();
                        context.setDontSendReponse(true);
                        return;
                    }

                } else {

                    if (memoryUsage.isFull()) {
                        waitForSpace(context, producerExchange, memoryUsage, "Usage Manager Memory Limit reached. Producer ("
                                + message.getProducerId() + ") stopped to prevent flooding "
                                + getActiveMQDestination().getQualifiedName() + "."
                                + " See http://activemq.apache.org/producer-flow-control.html for more info");
                    }

                    // The usage manager could have delayed us by the time
                    // we unblock the message could have expired..
                    if (message.isExpired()) {
                        LOG.debug("Expired message: {}", message);
                        broker.getRoot().messageExpired(context, message, null);
                        return;
                    }
                }
            }
        }
        doMessageSend(producerExchange, message);
        if (sendProducerAck) {
            ProducerAck ack = new ProducerAck(producerInfo.getProducerId(), message.getSize());
            context.getConnection().dispatchAsync(ack);
        }
    }

};