//,temp,HTTPDiscoveryAgent.java,153,220,temp,SimpleDiscoveryAgent.java,130,186
//,3
public class xxx {
    public void serviceFailed(DiscoveryEvent devent) throws IOException {

        final SimpleDiscoveryEvent event = (SimpleDiscoveryEvent) devent;
        if (event.failed.compareAndSet(false, true)) {
            discoveryListener.get().onServiceRemove(event);
            if (!event.removed.get()) {
                // Setup a thread to re-raise the event...
                Thread thread = new Thread() {
                    public void run() {

                        // We detect a failed connection attempt because the
                        // service
                        // fails right away.
                        if (event.connectTime + minConnectTime > System.currentTimeMillis()) {
                            LOG.debug("Failure occured soon after the discovery event was generated.  " +
                                      "It will be clasified as a connection failure: " + event);

                            event.connectFailures++;

                            if (maxReconnectAttempts > 0 && event.connectFailures >= maxReconnectAttempts) {
                                LOG.debug("Reconnect attempts exceeded " + maxReconnectAttempts +
                                          " tries.  Reconnecting has been disabled.");
                                return;
                            }

                            synchronized (sleepMutex) {
                                try {
                                    if (!running.get() || event.removed.get()) {
                                        return;
                                    }
                                    LOG.debug("Waiting " + event.reconnectDelay +
                                              " ms before attepting to reconnect.");
                                    sleepMutex.wait(event.reconnectDelay);
                                } catch (InterruptedException ie) {
                                    Thread.currentThread().interrupt();
                                    return;
                                }
                            }

                            if (!useExponentialBackOff) {
                                event.reconnectDelay = initialReconnectDelay;
                            } else {
                                // Exponential increment of reconnect delay.
                                event.reconnectDelay *= backOffMultiplier;
                                if (event.reconnectDelay > maxReconnectDelay) {
                                    event.reconnectDelay = maxReconnectDelay;
                                }
                            }

                        } else {
                            event.connectFailures = 0;
                            event.reconnectDelay = initialReconnectDelay;
                        }

                        if (!running.get() || event.removed.get()) {
                            return;
                        }

                        event.connectTime = System.currentTimeMillis();
                        event.failed.set(false);
                        discoveryListener.get().onServiceAdd(event);
                    }
                };
                thread.setDaemon(true);
                thread.start();
            }
        }
    }

};