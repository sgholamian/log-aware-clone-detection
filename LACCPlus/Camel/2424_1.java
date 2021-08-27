//,temp,SmppConsumer.java,138,187,temp,SmppProducer.java,181,213
//,3
public class xxx {
    private void reconnect(final long initialReconnectDelay) {
        if (reconnectLock.tryLock()) {
            try {
                Runnable r = new Runnable() {
                    public void run() {
                        boolean reconnected = false;

                        LOG.info("Schedule reconnect after {} millis", initialReconnectDelay);
                        try {
                            Thread.sleep(initialReconnectDelay);
                        } catch (InterruptedException e) {
                            // ignore
                        }

                        int attempt = 0;
                        while (!(isStopping() || isStopped())
                                && (session == null || session.getSessionState().equals(SessionState.CLOSED))
                                && attempt < configuration.getMaxReconnect()) {
                            try {
                                attempt++;
                                LOG.info("Trying to reconnect to {} - attempt #{}", getEndpoint().getConnectionString(),
                                        attempt);
                                session = createSession();
                                reconnected = true;
                            } catch (IOException e) {
                                LOG.warn("Failed to reconnect to {}", getEndpoint().getConnectionString());
                                closeSession();
                                try {
                                    Thread.sleep(configuration.getReconnectDelay());
                                } catch (InterruptedException ee) {
                                }
                            }
                        }

                        if (reconnected) {
                            LOG.info("Reconnected to {}", getEndpoint().getConnectionString());
                        }
                    }
                };

                Thread t = new Thread(r);
                t.start();
                t.join();
            } catch (InterruptedException e) {
                // noop
            } finally {
                reconnectLock.unlock();
            }
        }
    }

};