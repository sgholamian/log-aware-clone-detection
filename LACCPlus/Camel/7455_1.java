//,temp,ResilienceProcessor.java,607,681,temp,FaultToleranceProcessor.java,472,527
//,3
public class xxx {
        @Override
        public Exchange apply(Throwable throwable) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Processing exchange: {} recover task using circuit breaker: {} from: {}", exchange.getExchangeId(),
                        id, throwable);
            }

            if (fallback == null) {
                if (throwable instanceof TimeoutException) {
                    // the circuit breaker triggered a timeout (and there is no
                    // fallback) so lets mark the exchange as failed
                    exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_SUCCESSFUL_EXECUTION, false);
                    exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_FROM_FALLBACK, false);
                    exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_SHORT_CIRCUITED, false);
                    exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_TIMED_OUT, true);
                    exchange.setException(throwable);
                    return exchange;
                } else if (throwable instanceof CallNotPermittedException) {
                    // the circuit breaker triggered a call rejected
                    // where the circuit breaker is half-open / open and therefore
                    // we should just set properties and do not set any exception
                    exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_SUCCESSFUL_EXECUTION, false);
                    exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_FROM_FALLBACK, false);
                    exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_SHORT_CIRCUITED, true);
                    exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_REJECTED, true);
                    return exchange;
                } else if (throwable instanceof BulkheadFullException) {
                    // the circuit breaker bulkhead is full
                    exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_SUCCESSFUL_EXECUTION, false);
                    exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_FROM_FALLBACK, false);
                    exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_SHORT_CIRCUITED, true);
                    exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_REJECTED, true);
                    exchange.setException(throwable);
                    return exchange;
                } else {
                    // other kind of exception
                    exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_SUCCESSFUL_EXECUTION, false);
                    exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_FROM_FALLBACK, false);
                    exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_SHORT_CIRCUITED, true);
                    exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_REJECTED, true);
                    exchange.setException(throwable);
                    return exchange;
                }
            }

            // fallback route is handling the exception so its short-circuited
            exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_SUCCESSFUL_EXECUTION, false);
            exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_FROM_FALLBACK, true);
            exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_SHORT_CIRCUITED, true);

            // store the last to endpoint as the failure endpoint
            if (exchange.getProperty(ExchangePropertyKey.FAILURE_ENDPOINT) == null) {
                exchange.setProperty(ExchangePropertyKey.FAILURE_ENDPOINT,
                        exchange.getProperty(ExchangePropertyKey.TO_ENDPOINT));
            }
            // give the rest of the pipeline another chance
            exchange.setProperty(ExchangePropertyKey.EXCEPTION_HANDLED, true);
            exchange.setProperty(ExchangePropertyKey.EXCEPTION_CAUGHT, exchange.getException());
            exchange.setRouteStop(false);
            exchange.setException(null);
            // and we should not be regarded as exhausted as we are in a try ..
            // catch block
            exchange.adapt(ExtendedExchange.class).setRedeliveryExhausted(false);
            // run the fallback processor
            try {
                LOG.debug("Running fallback: {} with exchange: {}", fallback, exchange);
                // process the fallback until its fully done
                fallback.process(exchange);
                LOG.debug("Running fallback: {} with exchange: {} done", fallback, exchange);
            } catch (Throwable e) {
                exchange.setException(e);
            }

            return exchange;
        }

};