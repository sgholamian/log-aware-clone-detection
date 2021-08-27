//,temp,ResilienceProcessor.java,503,550,temp,FaultToleranceProcessor.java,396,449
//,3
public class xxx {
        @Override
        public Exchange call() throws Exception {
            Exchange copy = null;
            UnitOfWork uow = null;
            Throwable cause;

            // turn of interruption to allow fault tolerance to process the exchange under its handling
            exchange.adapt(ExtendedExchange.class).setInterruptable(false);

            try {
                LOG.debug("Running processor: {} with exchange: {}", processor, exchange);

                // prepare a copy of exchange so downstream processors don't
                // cause side-effects if they mutate the exchange
                // in case timeout processing and continue with the fallback etc
                copy = processorExchangeFactory.createCorrelatedCopy(exchange, false);
                if (copy.getUnitOfWork() != null) {
                    uow = copy.getUnitOfWork();
                } else {
                    // prepare uow on copy
                    uow = copy.getContext().adapt(ExtendedCamelContext.class).getUnitOfWorkFactory().createUnitOfWork(copy);
                    copy.adapt(ExtendedExchange.class).setUnitOfWork(uow);
                }

                // process the processor until its fully done
                processor.process(copy);

                // handle the processing result
                if (copy.getException() != null) {
                    exchange.setException(copy.getException());
                } else {
                    // copy the result as its regarded as success
                    ExchangeHelper.copyResults(exchange, copy);
                    exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_SUCCESSFUL_EXECUTION, true);
                    exchange.setProperty(ExchangePropertyKey.CIRCUIT_BREAKER_RESPONSE_FROM_FALLBACK, false);
                }
            } catch (Exception e) {
                exchange.setException(e);
            } finally {
                // must done uow
                UnitOfWorkHelper.doneUow(uow, copy);
                // remember any thrown exception
                cause = exchange.getException();
            }

            // and release exchange back in pool
            processorExchangeFactory.release(exchange);

            if (cause != null) {
                // throw exception so resilient4j know it was a failure
                throw RuntimeExchangeException.wrapRuntimeException(cause);
            }
            return exchange;
        }

};