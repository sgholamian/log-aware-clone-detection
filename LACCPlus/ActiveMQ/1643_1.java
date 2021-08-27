//,temp,AbstractInactivityMonitor.java,233,267,temp,AbstractInactivityMonitor.java,182,231
//,3
public class xxx {
    final void readCheck() {
        int currentCounter = next.getReceiveCounter();
        int previousCounter = lastReceiveCounter.getAndSet(currentCounter);
        if (inReceive.get() || currentCounter != previousCounter) {
            LOG.trace("A receive is in progress, skipping read check.");
            return;
        }
        if (!commandReceived.get() && monitorStarted.get() && !ASYNC_TASKS.isShutdown()) {
            LOG.debug("No message received since last read check for {}. Throwing InactivityIOException.", this);

            try {
                ASYNC_TASKS.execute(new Runnable() {
                    @Override
                    public void run() {
                        LOG.debug("Running {}", this);
                        onException(new InactivityIOException("Channel was inactive for too (>" + readCheckTime + ") long: " + next.getRemoteAddress()));
                    }

                    @Override
                    public String toString() {
                        return "ReadCheck[" + getRemoteAddress() + "]";
                    };
                });
            } catch (RejectedExecutionException ex) {
                if (!ASYNC_TASKS.isShutdown()) {
                    LOG.warn("Async read check was rejected from the executor: ", ex);
                }
            }
        } else {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Message received since last read check, resetting flag: {}", this);
            }
        }
        commandReceived.set(false);
    }

};