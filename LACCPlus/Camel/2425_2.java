//,temp,SmppConsumer.java,142,175,temp,SmppProducer.java,177,225
//,3
public class xxx {
    private void reconnect(final long initialReconnectDelay) {
        if (connectLock.tryLock()) {
            try {
                Runnable r = new Runnable() {
                    public void run() {
                        boolean reconnected = false;

                        LOG.info("Schedule reconnect after {} millis", initialReconnectDelay);
                        try {
                            Thread.sleep(initialReconnectDelay);
                        } catch (InterruptedException e) {
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
                connectLock.unlock();
            }
        }
    }

};