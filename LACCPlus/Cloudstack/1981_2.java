//,temp,SecondaryStorageManagerImpl.java,1044,1075,temp,ConsoleProxyManagerImpl.java,1178,1209
//,3
public class xxx {
    @Override
    public boolean rebootProxy(long proxyVmId) {
        final ConsoleProxyVO proxy = _consoleProxyDao.findById(proxyVmId);

        if (proxy == null || proxy.getState() == State.Destroyed) {
            return false;
        }

        if (proxy.getState() == State.Running && proxy.getHostId() != null) {
            final RebootCommand cmd = new RebootCommand(proxy.getInstanceName(), _itMgr.getExecuteInSequence(proxy.getHypervisorType()));
            final Answer answer = _agentMgr.easySend(proxy.getHostId(), cmd);

            if (answer != null && answer.getResult()) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Successfully reboot console proxy " + proxy.getHostName());
                }

                SubscriptionMgr.getInstance().notifySubscribers(ConsoleProxyManager.ALERT_SUBJECT, this,
                    new ConsoleProxyAlertEventArgs(ConsoleProxyAlertEventArgs.PROXY_REBOOTED, proxy.getDataCenterId(), proxy.getId(), proxy, null));

                return true;
            } else {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("failed to reboot console proxy : " + proxy.getHostName());
                }

                return false;
            }
        } else {
            return startProxy(proxyVmId, false) != null;
        }
    }

};