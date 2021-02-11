//,temp,SecondaryStorageManagerImpl.java,494,533,temp,ConsoleProxyManagerImpl.java,632,671
//,3
public class xxx {
    public ConsoleProxyVO startNew(long dataCenterId) throws ConcurrentOperationException {

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Assign console proxy from a newly started instance for request from data center : " + dataCenterId);
        }

        if (!allowToLaunchNew(dataCenterId)) {
            s_logger.warn("The number of launched console proxy on zone " + dataCenterId + " has reached to limit");
            return null;
        }

        VMTemplateVO template = null;
        HypervisorType availableHypervisor = _resourceMgr.getAvailableHypervisor(dataCenterId);
        template = _templateDao.findSystemVMReadyTemplate(dataCenterId, availableHypervisor);
        if (template == null) {
            throw new CloudRuntimeException("Not able to find the System templates or not downloaded in zone " + dataCenterId);
        }

        Map<String, Object> context = createProxyInstance(dataCenterId, template);

        long proxyVmId = (Long)context.get("proxyVmId");
        if (proxyVmId == 0) {
            if (s_logger.isTraceEnabled()) {
                s_logger.trace("Creating proxy instance failed, data center id : " + dataCenterId);
            }
            return null;
        }

        ConsoleProxyVO proxy = _consoleProxyDao.findById(proxyVmId);
        if (proxy != null) {
            SubscriptionMgr.getInstance().notifySubscribers(ConsoleProxyManager.ALERT_SUBJECT, this,
                new ConsoleProxyAlertEventArgs(ConsoleProxyAlertEventArgs.PROXY_CREATED, dataCenterId, proxy.getId(), proxy, null));
            return proxy;
        } else {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Unable to allocate console proxy storage, remove the console proxy record from DB, proxy id: " + proxyVmId);
            }
        }
        return null;
    }

};