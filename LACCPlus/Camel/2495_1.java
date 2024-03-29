//,temp,DisruptorProducer.java,72,178,temp,SedaProducer.java,55,161
//,3
public class xxx {
    @Override
    public boolean process(final Exchange exchange, final AsyncCallback callback) {
        WaitForTaskToComplete wait = waitForTaskToComplete;
        if (exchange.getProperty(Exchange.ASYNC_WAIT) != null) {
            wait = exchange.getProperty(Exchange.ASYNC_WAIT, WaitForTaskToComplete.class);
        }

        if (wait == WaitForTaskToComplete.Always
                || (wait == WaitForTaskToComplete.IfReplyExpected && ExchangeHelper.isOutCapable(exchange))) {

            // do not handover the completion as we wait for the copy to complete, and copy its result back when it done
            final Exchange copy = prepareCopy(exchange, false);

            // latch that waits until we are complete
            final CountDownLatch latch = new CountDownLatch(1);

            // we should wait for the reply so install a on completion so we know when its complete
            copy.adapt(ExtendedExchange.class).addOnCompletion(new SynchronizationAdapter() {
                @Override
                public void onDone(final Exchange response) {
                    // check for timeout, which then already would have invoked the latch
                    if (latch.getCount() == 0) {
                        if (LOG.isTraceEnabled()) {
                            LOG.trace("{}. Timeout occurred so response will be ignored: {}", this,
                                    response.getMessage());
                        }
                    } else {
                        if (LOG.isTraceEnabled()) {
                            LOG.trace("{} with response: {}", this,
                                    response.getMessage());
                        }
                        try {
                            ExchangeHelper.copyResults(exchange, response);
                        } finally {
                            // always ensure latch is triggered
                            latch.countDown();
                        }
                    }
                }

                @Override
                public boolean allowHandover() {
                    // do not allow handover as we want to seda producer to have its completion triggered
                    // at this point in the routing (at this leg), instead of at the very last (this ensure timeout is honored)
                    return false;
                }

                @Override
                public String toString() {
                    return "onDone at endpoint: " + endpoint;
                }
            });

            doPublish(copy);

            if (timeout > 0) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("Waiting for task to complete using timeout (ms): {} at [{}]", timeout,
                            endpoint.getEndpointUri());
                }
                // lets see if we can get the task done before the timeout
                boolean done = false;
                try {
                    done = latch.await(timeout, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    // ignore
                }
                if (!done) {
                    // Remove timed out Exchange from disruptor endpoint.

                    // We can't actually remove a published exchange from an active Disruptor.
                    // Instead we prevent processing of the exchange by setting a Property on the exchange and the value
                    // would be an AtomicBoolean. This is set by the Producer and the Consumer would look up that Property and
                    // check the AtomicBoolean. If the AtomicBoolean says that we are good to proceed, it will process the
                    // exchange. If false, it will simply disregard the exchange.
                    // But since the Property map is a Concurrent one, maybe we don't need the AtomicBoolean. Check with Simon.
                    // Also check the TimeoutHandler of the new Disruptor 3.0.0, consider making the switch to the latest version.
                    exchange.setProperty(DisruptorEndpoint.DISRUPTOR_IGNORE_EXCHANGE, true);

                    exchange.setException(new ExchangeTimedOutException(exchange, timeout));

                    // count down to indicate timeout
                    latch.countDown();
                }
            } else {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("Waiting for task to complete (blocking) at [{}]", endpoint.getEndpointUri());
                }
                // no timeout then wait until its done
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    // ignore
                }
            }
        } else {
            // no wait, eg its a InOnly then just publish to the ringbuffer and return
            // handover the completion so its the copy which performs that, as we do not wait
            final Exchange copy = prepareCopy(exchange, true);
            doPublish(copy);
        }

        // we use OnCompletion on the Exchange to callback and wait for the Exchange to be done
        // so we should just signal the callback we are done synchronously
        callback.done(true);
        return true;
    }

};