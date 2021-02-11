//,temp,DatabaseIntegrityChecker.java,269,294,temp,UploadMonitorImpl.java,450,469
//,3
public class xxx {
    @Override
    public void check() {
        GlobalLock lock = GlobalLock.getInternLock("DatabaseIntegrity");
        try {
            s_logger.info("Grabbing lock to check for database integrity.");
            if (!lock.lock(20 * 60)) {
                throw new CloudRuntimeException("Unable to acquire lock to check for database integrity.");
            }

            try {
                s_logger.info("Performing database integrity check");
                if (!checkDuplicateHostWithTheSameLocalStorage()) {
                    throw new CloudRuntimeException("checkDuplicateHostWithTheSameLocalStorage detected error");
                }

                if (!checkMissedPremiumUpgradeFor228()) {
                    s_logger.error("Your current database version is 2.2.8, management server detected some missed premium upgrade, please contact CloudStack support and attach log file. Thank you!");
                    throw new CloudRuntimeException("Detected missed premium upgrade");
                }
            } finally {
                lock.unlock();
            }
        } finally {
            lock.releaseRef();
        }
    }

};