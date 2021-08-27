//,temp,RedeliveryErrorHandler.java,1057,1074,temp,RedeliveryErrorHandler.java,1034,1051
//,3
public class xxx {
        protected void onExceptionOccurred() {
            if (onExceptionProcessor == null) {
                return;
            }

            // run this synchronously as its just a Processor
            try {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("OnExceptionOccurred processor {} is processing Exchange: {} due exception occurred",
                            onExceptionProcessor, exchange);
                }
                onExceptionProcessor.process(exchange);
            } catch (Throwable e) {
                // we dont not want new exception to override existing, so log it as a WARN
                LOG.warn("Error during processing OnExceptionOccurred. This exception is ignored.", e);
            }
            LOG.trace("OnExceptionOccurred processor done");
        }

};