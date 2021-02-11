//,temp,ManagementServerImpl.java,3141,3171,temp,ManagementServerImpl.java,3107,3137
//,2
public class xxx {
        @Override
        protected void runInContext() {
            try {
                final GlobalLock lock = GlobalLock.getInternLock("AlertPurge");
                if (lock == null) {
                    s_logger.debug("Couldn't get the global lock");
                    return;
                }
                if (!lock.lock(30)) {
                    s_logger.debug("Couldn't lock the db");
                    return;
                }
                try {
                    final Calendar purgeCal = Calendar.getInstance();
                    purgeCal.add(Calendar.DAY_OF_YEAR, -_alertPurgeDelay);
                    final Date purgeTime = purgeCal.getTime();
                    s_logger.debug("Deleting alerts older than: " + purgeTime.toString());
                    final List<AlertVO> oldAlerts = _alertDao.listOlderAlerts(purgeTime);
                    s_logger.debug("Found " + oldAlerts.size() + " events to be purged");
                    for (final AlertVO alert : oldAlerts) {
                        _alertDao.expunge(alert.getId());
                    }
                } catch (final Exception e) {
                    s_logger.error("Exception ", e);
                } finally {
                    lock.unlock();
                }
            } catch (final Exception e) {
                s_logger.error("Exception ", e);
            }
        }

};