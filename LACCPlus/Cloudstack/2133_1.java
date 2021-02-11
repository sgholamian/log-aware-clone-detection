//,temp,SecondaryStorageManagerImpl.java,723,812,temp,ConsoleProxyManagerImpl.java,936,1001
//,3
public class xxx {
    private void allocCapacity(long dataCenterId, SecondaryStorageVm.Role role) {
        if (s_logger.isTraceEnabled()) {
            s_logger.trace("Allocate secondary storage vm standby capacity for data center : " + dataCenterId);
        }

        if (!isSecondaryStorageVmRequired(dataCenterId)) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Secondary storage vm not required in zone " + dataCenterId + " according to zone config");
            }
            return;
        }
        SecondaryStorageVmVO secStorageVm = null;
        String errorString = null;
        try {
            boolean secStorageVmFromStoppedPool = false;
            secStorageVm = assignSecStorageVmFromStoppedPool(dataCenterId, role);
            if (secStorageVm == null) {
                if (s_logger.isInfoEnabled()) {
                    s_logger.info("No stopped secondary storage vm is available, need to allocate a new secondary storage vm");
                }

                if (_allocLock.lock(ACQUIRE_GLOBAL_LOCK_TIMEOUT_FOR_SYNC)) {
                    try {
                        secStorageVm = startNew(dataCenterId, role);
                        for (UploadVO upload : _uploadDao.listAll()) {
                            _uploadDao.expunge(upload.getId());
                        }
                    } finally {
                        _allocLock.unlock();
                    }
                } else {
                    if (s_logger.isInfoEnabled()) {
                        s_logger.info("Unable to acquire synchronization lock for secondary storage vm allocation, wait for next scan");
                    }
                    return;
                }
            } else {
                if (s_logger.isInfoEnabled()) {
                    s_logger.info("Found a stopped secondary storage vm, starting it. Vm id : " + secStorageVm.getId());
                }
                secStorageVmFromStoppedPool = true;
            }

            if (secStorageVm != null) {
                long secStorageVmId = secStorageVm.getId();
                GlobalLock secStorageVmLock = GlobalLock.getInternLock(getSecStorageVmLockName(secStorageVmId));
                try {
                    if (secStorageVmLock.lock(ACQUIRE_GLOBAL_LOCK_TIMEOUT_FOR_SYNC)) {
                        try {
                            secStorageVm = startSecStorageVm(secStorageVmId);
                        } finally {
                            secStorageVmLock.unlock();
                        }
                    } else {
                        if (s_logger.isInfoEnabled()) {
                            s_logger.info("Unable to acquire synchronization lock for starting secondary storage vm id : " + secStorageVm.getId());
                        }
                        return;
                    }
                } finally {
                    secStorageVmLock.releaseRef();
                }

                if (secStorageVm == null) {
                    if (s_logger.isInfoEnabled()) {
                        s_logger.info("Unable to start secondary storage vm for standby capacity, vm id : " + secStorageVmId + ", will recycle it and start a new one");
                    }

                    if (secStorageVmFromStoppedPool) {
                        destroySecStorageVm(secStorageVmId);
                    }
                } else {
                    SubscriptionMgr.getInstance().notifySubscribers(ALERT_SUBJECT, this,
                            new SecStorageVmAlertEventArgs(SecStorageVmAlertEventArgs.SSVM_UP, dataCenterId, secStorageVmId, secStorageVm, null));
                    if (s_logger.isInfoEnabled()) {
                        s_logger.info("Secondary storage vm " + secStorageVm.getHostName() + " is started");
                    }
                }
            }
        } catch (Exception e) {
            errorString = e.getMessage();
            throw e;
        } finally {
            // TODO - For now put all the alerts as creation failure. Distinguish between creation vs start failure in future.
            // Also add failure reason since startvm masks some of them.
            if(secStorageVm == null || secStorageVm.getState() != State.Running)
                SubscriptionMgr.getInstance().notifySubscribers(ALERT_SUBJECT, this,
                        new SecStorageVmAlertEventArgs(SecStorageVmAlertEventArgs.SSVM_CREATE_FAILURE, dataCenterId, 0l, null, errorString));
        }
    }

};