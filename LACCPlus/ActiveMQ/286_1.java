//,temp,HTTPDiscoveryAgent.java,161,214,temp,SimpleDiscoveryAgent.java,138,183
//,3
public class xxx {
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