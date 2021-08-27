//,temp,RedeliveryErrorHandler.java,1312,1326,temp,RedeliveryErrorHandler.java,521,544
//,3
public class xxx {
        private void prepareExchangeAfterFailureNotHandled(Exchange exchange) {
            ExtendedExchange ee = (ExtendedExchange) exchange;

            LOG.trace("This exchange is not handled or continued so its marked as failed: {}", ee);
            // exception not handled, put exception back in the exchange
            ee.setErrorHandlerHandled(false);
            ee.setException(exchange.getProperty(ExchangePropertyKey.EXCEPTION_CAUGHT, Exception.class));
            // and put failure endpoint back as well
            ee.setProperty(ExchangePropertyKey.FAILURE_ENDPOINT, ee.getProperty(ExchangePropertyKey.TO_ENDPOINT));
            // and store the route id so we know in which route we failed
            String routeId = ExchangeHelper.getAtRouteId(ee);
            if (routeId != null) {
                ee.setProperty(ExchangePropertyKey.FAILURE_ROUTE_ID, routeId);
            }
        }

};