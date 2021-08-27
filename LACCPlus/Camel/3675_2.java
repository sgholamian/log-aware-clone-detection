//,temp,RedeliveryErrorHandler.java,1312,1326,temp,RedeliveryErrorHandler.java,521,544
//,3
public class xxx {
        protected void prepareExchangeAfterFailure(final Exchange exchange) {
            ExtendedExchange ee = (ExtendedExchange) exchange;

            // we could not process the exchange so we let the failure processor handled it
            ExchangeHelper.setFailureHandled(exchange);

            // honor if already set a handling
            boolean alreadySet = ee.isErrorHandlerHandledSet();
            if (alreadySet) {
                boolean handled = ee.isErrorHandlerHandled();
                LOG.trace("This exchange has already been marked for handling: {}", handled);
                if (!handled) {
                    // exception not handled, put exception back in the exchange
                    exchange.setException(exchange.getProperty(ExchangePropertyKey.EXCEPTION_CAUGHT, Exception.class));
                    // and put failure endpoint back as well
                    exchange.setProperty(ExchangePropertyKey.FAILURE_ENDPOINT,
                            exchange.getProperty(ExchangePropertyKey.TO_ENDPOINT));
                }
                return;
            }

            // not handled by default
            prepareExchangeAfterFailureNotHandled(exchange);
        }

};