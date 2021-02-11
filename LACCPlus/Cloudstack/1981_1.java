//,temp,SecondaryStorageManagerImpl.java,1044,1075,temp,ConsoleProxyManagerImpl.java,1178,1209
//,3
public class xxx {
    @Override
    public boolean rebootSecStorageVm(long secStorageVmId) {
        final SecondaryStorageVmVO secStorageVm = _secStorageVmDao.findById(secStorageVmId);

        if (secStorageVm == null || secStorageVm.getState() == State.Destroyed) {
            return false;
        }

        if (secStorageVm.getState() == State.Running && secStorageVm.getHostId() != null) {
            final RebootCommand cmd = new RebootCommand(secStorageVm.getInstanceName(), _itMgr.getExecuteInSequence(secStorageVm.getHypervisorType()));
            final Answer answer = _agentMgr.easySend(secStorageVm.getHostId(), cmd);

            if (answer != null && answer.getResult()) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Successfully reboot secondary storage vm " + secStorageVm.getHostName());
                }

                SubscriptionMgr.getInstance().notifySubscribers(ALERT_SUBJECT, this,
                    new SecStorageVmAlertEventArgs(SecStorageVmAlertEventArgs.SSVM_REBOOTED, secStorageVm.getDataCenterId(), secStorageVm.getId(), secStorageVm, null));

                return true;
            } else {
                String msg = "Rebooting Secondary Storage VM failed - " + secStorageVm.getHostName();
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug(msg);
                }
                return false;
            }
        } else {
            return startSecStorageVm(secStorageVmId) != null;
        }
    }

};