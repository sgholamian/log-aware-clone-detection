//,temp,OnCompletionProcessor.java,322,359,temp,OnCompletionProcessor.java,279,320
//,3
public class xxx {
        @Override
        public void onFailure(final Exchange exchange) {
            if (onCompleteOnly) {
                return;
            }

            if (onWhen != null && !onWhen.matches(exchange)) {
                // predicate did not match so do not route the onComplete
                return;
            }

            // must use a copy as we dont want it to cause side effects of the original exchange
            final Exchange copy = prepareExchange(exchange);
            final Exception original = copy.getException();
            if (original != null) {
                // must remove exception otherwise onFailure routing will fail as well
                // the caused exception is stored as a property (Exchange.EXCEPTION_CAUGHT) on the exchange
                copy.setException(null);
            }

            if (executorService != null) {
                executorService.submit(new Callable<Exchange>() {
                    public Exchange call() throws Exception {
                        LOG.debug("Processing onFailure: {}", copy);
                        doProcess(processor, copy);
                        // restore exception after processing
                        copy.setException(original);
                        return null;
                    }
                });
            } else {
                // run without thread-pool
                LOG.debug("Processing onFailure: {}", copy);
                doProcess(processor, copy);
                // restore exception after processing
                copy.setException(original);
            }
        }

};