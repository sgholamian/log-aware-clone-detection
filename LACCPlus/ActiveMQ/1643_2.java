//,temp,AbstractInactivityMonitor.java,233,267,temp,AbstractInactivityMonitor.java,182,231
//,3
public class xxx {
    final void writeCheck() {
        if (inSend.get()) {
            LOG.trace("Send in progress. Skipping write check.");
            return;
        }

        if (!commandSent.get() && useKeepAlive && monitorStarted.get() && !ASYNC_TASKS.isShutdown()) {
            LOG.trace("{} no message sent since last write check, sending a KeepAliveInfo", this);

            try {
                ASYNC_TASKS.execute(new Runnable() {
                    @Override
                    public void run() {
                        LOG.debug("Running {}", this);
                        if (monitorStarted.get()) {
                            try {
                                // If we can't get the lock it means another
                                // write beat us into the
                                // send and we don't need to heart beat now.
                                if (sendLock.writeLock().tryLock()) {
                                    KeepAliveInfo info = new KeepAliveInfo();
                                    info.setResponseRequired(keepAliveResponseRequired);
                                    doOnewaySend(info);
                                }
                            } catch (IOException e) {
                                onException(e);
                            } finally {
                                if (sendLock.writeLock().isHeldByCurrentThread()) {
                                    sendLock.writeLock().unlock();
                                }
                            }
                        }
                    }

                    @Override
                    public String toString() {
                        return "WriteCheck[" + getRemoteAddress() + "]";
                    };
                });
            } catch (RejectedExecutionException ex) {
                if (!ASYNC_TASKS.isShutdown()) {
                    LOG.warn("Async write check was rejected from the executor: ", ex);
                }
            }
        } else {
            LOG.trace("{} message sent since last write check, resetting flag.", this);
        }

        commandSent.set(false);
    }

};