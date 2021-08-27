//,temp,RedeliveryErrorHandler.java,697,719,temp,DelayProcessorSupport.java,155,181
//,3
public class xxx {
        @Override
        public void run() {
            // can we still run
            if (!isRunAllowed()) {
                LOG.trace("Run not allowed, will reject executing exchange: {}", exchange);
                if (exchange.getException() == null) {
                    exchange.setException(new RejectedExecutionException());
                }
                AsyncCallback cb = callback;
                taskFactory.release(this);
                cb.done(false);
                return;
            }

            try {
                doRun();
            } catch (Throwable e) {
                // unexpected exception during running so set exception and trigger callback
                // (do not do taskFactory.release as that happens later)
                exchange.setException(e);
                callback.done(false);
            }
        }

};