//,temp,SecondaryStorageManagerImpl.java,494,533,temp,ConsoleProxyManagerImpl.java,632,671
//,3
public class xxx {
    public SecondaryStorageVmVO startNew(long dataCenterId, SecondaryStorageVm.Role role) {

        if (!isSecondaryStorageVmRequired(dataCenterId)) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Secondary storage vm not required in zone " + dataCenterId + " acc. to zone config");
            }
            return null;
        }
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Assign secondary storage vm from a newly started instance for request from data center : " + dataCenterId);
        }

        Map<String, Object> context = createSecStorageVmInstance(dataCenterId, role);

        long secStorageVmId = (Long)context.get("secStorageVmId");
        if (secStorageVmId == 0) {
            if (s_logger.isTraceEnabled()) {
                s_logger.trace("Creating secondary storage vm instance failed, data center id : " + dataCenterId);
            }

            return null;
        }

        SecondaryStorageVmVO secStorageVm = _secStorageVmDao.findById(secStorageVmId);
        // SecondaryStorageVmVO secStorageVm =
        // allocSecStorageVmStorage(dataCenterId, secStorageVmId);
        if (secStorageVm != null) {
            SubscriptionMgr.getInstance().notifySubscribers(ALERT_SUBJECT, this,
                new SecStorageVmAlertEventArgs(SecStorageVmAlertEventArgs.SSVM_CREATED, dataCenterId, secStorageVmId, secStorageVm, null));
            return secStorageVm;
        } else {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Unable to allocate secondary storage vm storage, remove the secondary storage vm record from DB, secondary storage vm id: " +
                    secStorageVmId);
            }
            SubscriptionMgr.getInstance().notifySubscribers(ALERT_SUBJECT, this,
                new SecStorageVmAlertEventArgs(SecStorageVmAlertEventArgs.SSVM_CREATE_FAILURE, dataCenterId, secStorageVmId, null, "Unable to allocate storage"));
        }
        return null;
    }

};