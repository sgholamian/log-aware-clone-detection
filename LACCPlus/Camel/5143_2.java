//,temp,SharedCamelInternalProcessor.java,132,234,temp,CamelInternalProcessor.java,288,416
//,3
public class xxx {
    @Override
    @SuppressWarnings("unchecked")
    public boolean process(Exchange exchange, AsyncCallback originalCallback) {
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

        if (processor == null || exchange.isRouteStop()) {
            // no processor or we should not continue then we are done
            originalCallback.done(true);
            return true;
        }

        if (shutdownStrategy.isForceShutdown()) {
            String msg = "Run not allowed as ShutdownStrategy is forcing shutting down, will reject executing exchange: "
                         + exchange;
            LOG.debug(msg);
            if (exchange.getException() == null) {
                exchange.setException(new RejectedExecutionException(msg));
            }
            // force shutdown so we should not continue
            originalCallback.done(true);
            return true;
        }

        Object[] states;

        // create internal callback which will execute the advices in reverse order when done
        CamelInternalTask afterTask = taskFactory != null ? taskFactory.acquire() : null;
        if (afterTask == null) {
            states = statefulAdvices > 0 ? new Object[statefulAdvices] : EMPTY_STATES;
            afterTask = new AsyncAfterTask(states);
        } else {
            states = afterTask.getStates();
        }
        afterTask.prepare(exchange, originalCallback);

        // optimise to use object array for states, and only for the number of advices that keep state
        // optimise for loop using index access to avoid creating iterator object
        for (int i = 0, j = 0; i < advices.size(); i++) {
            CamelInternalProcessorAdvice<?> task = advices.get(i);
            try {
                Object state = task.before(exchange);
                if (task.hasState()) {
                    states[j++] = state;
                }
            } catch (Throwable e) {
                // error in before so break out
                exchange.setException(e);
                try {
                    originalCallback.done(true);
                } finally {
                    // task is done so reset
                    if (taskFactory != null) {
                        taskFactory.release(afterTask);
                    }
                }
                return true;
            }
        }

        if (exchange.isTransacted()) {
            // must be synchronized for transacted exchanges
            if (LOG.isTraceEnabled()) {
                LOG.trace("Transacted Exchange must be routed synchronously for exchangeId: {} -> {}", exchange.getExchangeId(),
                        exchange);
            }
            try {
                // ----------------------------------------------------------
                // CAMEL END USER - DEBUG ME HERE +++ START +++
                // ----------------------------------------------------------
                processor.process(exchange);
                // ----------------------------------------------------------
                // CAMEL END USER - DEBUG ME HERE +++ END +++
                // ----------------------------------------------------------
            } catch (Throwable e) {
                exchange.setException(e);
            } finally {
                // processing is done
                afterTask.done(true);
            }
            // we are done synchronously - must return true
            return true;
        } else {
            final UnitOfWork uow = exchange.getUnitOfWork();

            // do uow before processing and if a value is returned the the uow wants to be processed after
            // was well in the same thread
            AsyncCallback async = afterTask;
            boolean beforeAndAfter = uow != null && uow.isBeforeAfterProcess();
            if (beforeAndAfter) {
                async = uow.beforeProcess(processor, exchange, async);
            }

            // ----------------------------------------------------------
            // CAMEL END USER - DEBUG ME HERE +++ START +++
            // ----------------------------------------------------------
            if (LOG.isTraceEnabled()) {
                LOG.trace("Processing exchange for exchangeId: {} -> {}", exchange.getExchangeId(), exchange);
            }
            processor.process(exchange, async);
            // ----------------------------------------------------------
            // CAMEL END USER - DEBUG ME HERE +++ END +++
            // ----------------------------------------------------------

            // optimize to only do after uow processing if really needed
            if (beforeAndAfter) {
                // execute any after processor work (in current thread, not in the callback)
                uow.afterProcess(processor, exchange, afterTask, false);
            }

            if (LOG.isTraceEnabled()) {
                LOG.trace("Exchange processed and is continued routed asynchronously for exchangeId: {} -> {}",
                        exchange.getExchangeId(), exchange);
            }
            // we are done asynchronously - must return false
            return false;
        }
    }

};