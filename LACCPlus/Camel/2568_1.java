//,temp,CxfConsumer.java,170,220,temp,CxfRsInvoker.java,85,136
//,3
public class xxx {
        private Object asyncInvoke(Exchange cxfExchange, final Continuation continuation) {
            LOG.trace("asyncInvoke continuation: {}", continuation);
            synchronized (continuation) {
                if (continuation.isNew()) {
                    final org.apache.camel.Exchange camelExchange = prepareCamelExchange(cxfExchange);

                    // Now we don't set up the timeout value
                    LOG.trace("Suspending continuation of exchangeId: {}", camelExchange.getExchangeId());

                    // The continuation could be called before the suspend is called
                    continuation.suspend(cxfEndpoint.getContinuationTimeout());

                    continuation.setObject(camelExchange);

                    // use the asynchronous API to process the exchange
                    getAsyncProcessor().process(camelExchange, new AsyncCallback() {
                        public void done(boolean doneSync) {
                            // make sure the continuation resume will not be called before the suspend method in other thread
                            synchronized (continuation) {
                                LOG.trace("Resuming continuation of exchangeId: {}", camelExchange.getExchangeId());
                                // resume processing after both, sync and async callbacks
                                continuation.resume();
                            }
                        }
                    });

                } else if (!continuation.isTimeout() && continuation.isResumed()) {
                    org.apache.camel.Exchange camelExchange = (org.apache.camel.Exchange) continuation.getObject();
                    try {
                        setResponseBack(cxfExchange, camelExchange);
                    } catch (Exception ex) {
                        CxfConsumer.this.doneUoW(camelExchange);
                        throw ex;
                    }

                } else if (continuation.isTimeout() || (!continuation.isResumed() && !continuation.isPending())) {
                    org.apache.camel.Exchange camelExchange = (org.apache.camel.Exchange) continuation.getObject();
                    try {
                        if (!continuation.isPending()) {
                            camelExchange.setException(
                                    new ExchangeTimedOutException(camelExchange, cxfEndpoint.getContinuationTimeout()));
                        }
                        setResponseBack(cxfExchange, camelExchange);
                    } catch (Exception ex) {
                        CxfConsumer.this.doneUoW(camelExchange);
                        throw ex;
                    }
                }
            }
            return null;
        }

};