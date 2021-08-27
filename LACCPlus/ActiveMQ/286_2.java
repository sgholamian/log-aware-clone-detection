//,temp,HTTPDiscoveryAgent.java,161,214,temp,SimpleDiscoveryAgent.java,138,183
//,3
public class xxx {
                @Override
                public void run() {
                    SimpleDiscoveryEvent event = new SimpleDiscoveryEvent(sevent);

                    // We detect a failed connection attempt because the service
                    // fails right away.
                    if (event.connectTime + minConnectTime > System.currentTimeMillis()) {
                        LOG.debug("Failure occurred soon after the discovery event was generated.  It will be classified as a connection failure: {}", event);

                        event.connectFailures++;

                        if (maxReconnectAttempts > 0 && event.connectFailures >= maxReconnectAttempts) {
                            LOG.warn("Reconnect attempts exceeded {} tries.  Reconnecting has been disabled for: {}", maxReconnectAttempts, event);
                            return;
                        }

                        if (!useExponentialBackOff || event.reconnectDelay == -1) {
                            event.reconnectDelay = initialReconnectDelay;
                        } else {
                            // Exponential increment of reconnect delay.
                            event.reconnectDelay *= backOffMultiplier;
                            if (event.reconnectDelay > maxReconnectDelay) {
                                event.reconnectDelay = maxReconnectDelay;
                            }
                        }

                        doReconnectDelay(event);

                    } else {
                        LOG.trace("Failure occurred to long after the discovery event was generated.  " +
                                  "It will not be classified as a connection failure: {}", event);
                        event.connectFailures = 0;
                        event.reconnectDelay = initialReconnectDelay;

                        doReconnectDelay(event);
                    }

                    if (!running.get()) {
                        LOG.debug("Reconnecting disabled: stopped");
                        return;
                    }

                    event.connectTime = System.currentTimeMillis();
                    event.failed.set(false);
                    listener.onServiceAdd(event);
                }

};