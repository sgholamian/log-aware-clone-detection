//,temp,ReplyManagerSupport.java,89,109,temp,RedeliveryErrorHandler.java,502,519
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