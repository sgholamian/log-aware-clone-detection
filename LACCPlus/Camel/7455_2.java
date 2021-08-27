//,temp,ResilienceProcessor.java,607,681,temp,FaultToleranceProcessor.java,472,527
//,3
public class xxx {
        @Override
        public Exchange call() throws Exception {
            Throwable throwable = exchange.getException();
            if (fallbackProcessor == null) {
                if (throwable instanceof TimeoutException) {
                    // the circuit breaker triggered a timeout (and there is no
                    // fallback) so lets mark the exchange as failed
                    exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_SUCCESSFUL_EXECUTION, false);
                    exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_FROM_FALLBACK, false);
                    exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_SHORT_CIRCUITED, false);
                    exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_TIMED_OUT, true);
                    exchange.setException(throwable);
                    return exchange;
                } else if (throwable instanceof CircuitBreakerOpenException) {
                    // the circuit breaker triggered a call rejected
                    exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_SUCCESSFUL_EXECUTION, false);
                    exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_FROM_FALLBACK, false);
                    exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_SHORT_CIRCUITED, true);
                    exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_REJECTED, true);
                    return exchange;
                } else {
                    // throw exception so fault tolerance know it was a failure
                    throw RuntimeExchangeException.wrapRuntimeException(throwable);
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
                LOG.debug("Running fallback: {} with exchange: {}", fallbackProcessor, exchange);
                // process the fallback until its fully done
                fallbackProcessor.process(exchange);
                LOG.debug("Running fallback: {} with exchange: {} done", fallbackProcessor, exchange);
            } catch (Exception e) {
                exchange.setException(e);
            }

            return exchange;
        }

};