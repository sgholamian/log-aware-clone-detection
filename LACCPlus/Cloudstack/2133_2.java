//,temp,SecondaryStorageManagerImpl.java,723,812,temp,ConsoleProxyManagerImpl.java,936,1001
//,3
public class xxx {
    private void allocCapacity(long dataCenterId) {
        if (s_logger.isTraceEnabled()) {
            s_logger.trace("Allocate console proxy standby capacity for data center : " + dataCenterId);
        }

        ConsoleProxyVO proxy = null;
        String errorString = null;
        try {
            boolean consoleProxyVmFromStoppedPool = false;
            proxy = assignProxyFromStoppedPool(dataCenterId);
            if (proxy == null) {
                if (s_logger.isInfoEnabled()) {
                    s_logger.info("No stopped console proxy is available, need to allocate a new console proxy");
                }

                if (_allocProxyLock.lock(ACQUIRE_GLOBAL_LOCK_TIMEOUT_FOR_SYNC)) {
                    try {
                        proxy = startNew(dataCenterId);
                    } catch (ConcurrentOperationException e) {
                        s_logger.info("Concurrent operation exception caught " + e);
                    } finally {
                        _allocProxyLock.unlock();
                    }
                } else {
                    if (s_logger.isInfoEnabled()) {
                        s_logger.info("Unable to acquire synchronization lock for console proxy vm allocation, wait for next scan");
                    }
                }
            } else {
                if (s_logger.isInfoEnabled()) {
                    s_logger.info("Found a stopped console proxy, starting it. Vm id : " + proxy.getId());
                }
                consoleProxyVmFromStoppedPool = true;
            }

            if (proxy != null) {
                long proxyVmId = proxy.getId();
                proxy = startProxy(proxyVmId, false);

                if (proxy != null) {
                    if (s_logger.isInfoEnabled()) {
                        s_logger.info("Console proxy " + proxy.getHostName() + " is started");
                    }
                    SubscriptionMgr.getInstance().notifySubscribers(ConsoleProxyManager.ALERT_SUBJECT, this,
                        new ConsoleProxyAlertEventArgs(ConsoleProxyAlertEventArgs.PROXY_UP, dataCenterId, proxy.getId(), proxy, null));
                } else {
                    if (s_logger.isInfoEnabled()) {
                        s_logger.info("Unable to start console proxy vm for standby capacity, vm id : " + proxyVmId + ", will recycle it and start a new one");
                    }

                    if (consoleProxyVmFromStoppedPool) {
                        destroyProxy(proxyVmId);
                    }
                }
            }
        } catch (Exception e) {
           errorString = e.getMessage();
           throw e;
        } finally {
            // TODO - For now put all the alerts as creation failure. Distinguish between creation vs start failure in future.
            // Also add failure reason since startvm masks some of them.
            if (proxy == null || proxy.getState() != State.Running)
                SubscriptionMgr.getInstance().notifySubscribers(ConsoleProxyManager.ALERT_SUBJECT, this,
                    new ConsoleProxyAlertEventArgs(ConsoleProxyAlertEventArgs.PROXY_CREATE_FAILURE, dataCenterId, 0l, null, errorString));
        }
    }

};