//,temp,RedeliveryErrorHandler.java,1057,1074,temp,RedeliveryErrorHandler.java,1034,1051
//,3
public class xxx {
        protected void deliverToOnRedeliveryProcessor() {
            if (onRedeliveryProcessor == null) {
                return;
            }

            if (LOG.isTraceEnabled()) {
                LOG.trace("Redelivery processor {} is processing Exchange: {} before its redelivered",
                        onRedeliveryProcessor, exchange);
            }

            // run this synchronously as its just a Processor
            try {
                onRedeliveryProcessor.process(exchange);
            } catch (Throwable e) {
                exchange.setException(e);
            }
            LOG.trace("Redelivery processor done");
        }

};