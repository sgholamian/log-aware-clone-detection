//,temp,SharedCamelInternalProcessor.java,132,234,temp,CamelInternalProcessor.java,288,416
//,3
public class xxx {
    public boolean process(
            Exchange exchange, AsyncCallback originalCallback, AsyncProcessor processor, Processor resultProcessor) {
        // ----------------------------------------------------------
        // CAMEL END USER - READ ME FOR DEBUGGING TIPS
        // ----------------------------------------------------------
        // If you want to debug the Camel routing engine, then there is a lot of internal functionality
        // the routing engine executes during routing messages. You can skip debugging this internal
        // functionality and instead debug where the routing engine continues routing to the next node
        // in the routes. The CamelInternalProcessor is a vital part of the routing engine, as its
        // being used in between the nodes. As an end user you can just debug the code in this class
        // in between the:
        //   CAMEL END USER - DEBUG ME HERE +++ START +++
        //   CAMEL END USER - DEBUG ME HERE +++ END +++
        // you can see in the code below.
        // ----------------------------------------------------------

        if (processor == null || !continueProcessing(exchange, processor)) {
            // no processor or we should not continue then we are done
            originalCallback.done(true);
            return true;
        }

        // optimise to use object array for states, and only for the number of advices that keep state
        final Object[] states = statefulAdvices > 0 ? new Object[statefulAdvices] : EMPTY_STATES;
        // optimise for loop using index access to avoid creating iterator object
        for (int i = 0, j = 0; i < advices.size(); i++) {
            CamelInternalProcessorAdvice task = advices.get(i);
            try {
                Object state = task.before(exchange);
                if (task.hasState()) {
                    states[j++] = state;
                }
            } catch (Throwable e) {
                exchange.setException(e);
                originalCallback.done(true);
                return true;
            }
        }

        // create internal callback which will execute the advices in reverse order when done
        AsyncCallback callback = new InternalCallback(states, exchange, originalCallback, resultProcessor);

        // UNIT_OF_WORK_PROCESS_SYNC is @deprecated and we should remove it from Camel 3.0
        Object synchronous = exchange.removeProperty(Exchange.UNIT_OF_WORK_PROCESS_SYNC);
        if (exchange.isTransacted() || synchronous != null) {
            // must be synchronized for transacted exchanges
            if (LOG.isTraceEnabled()) {
                if (exchange.isTransacted()) {
                    LOG.trace("Transacted Exchange must be routed synchronously for exchangeId: {} -> {}",
                            exchange.getExchangeId(), exchange);
                } else {
                    LOG.trace("Synchronous UnitOfWork Exchange must be routed synchronously for exchangeId: {} -> {}",
                            exchange.getExchangeId(), exchange);
                }
            }
            // ----------------------------------------------------------
            // CAMEL END USER - DEBUG ME HERE +++ START +++
            // ----------------------------------------------------------
            try {
                processor.process(exchange);
            } catch (Throwable e) {
                exchange.setException(e);
            }
            // ----------------------------------------------------------
            // CAMEL END USER - DEBUG ME HERE +++ END +++
            // ----------------------------------------------------------
            callback.done(true);
            return true;
        } else {
            final UnitOfWork uow = exchange.getUnitOfWork();

            // do uow before processing and if a value is returned the the uow wants to be processed after
            // was well in the same thread
            AsyncCallback async = callback;
            boolean beforeAndAfter = uow.isBeforeAfterProcess();
            if (beforeAndAfter) {
                async = uow.beforeProcess(processor, exchange, async);
            }

            // ----------------------------------------------------------
            // CAMEL END USER - DEBUG ME HERE +++ START +++
            // ----------------------------------------------------------
            if (LOG.isTraceEnabled()) {
                LOG.trace("Processing exchange for exchangeId: {} -> {}", exchange.getExchangeId(), exchange);
            }
            boolean sync = processor.process(exchange, async);
            // ----------------------------------------------------------
            // CAMEL END USER - DEBUG ME HERE +++ END +++
            // ----------------------------------------------------------

            // optimize to only do after uow processing if really needed
            if (beforeAndAfter) {
                // execute any after processor work (in current thread, not in the callback)
                uow.afterProcess(processor, exchange, callback, sync);
            }

            if (LOG.isTraceEnabled()) {
                LOG.trace("Exchange processed and is continued routed asynchronously for exchangeId: {} -> {}",
                        exchange.getExchangeId(), exchange);
            }
            return sync;
        }
    }

};