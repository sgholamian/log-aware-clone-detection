//,temp,ResourceManagerImpl.java,2903,2920,temp,ResourceManagerImpl.java,2764,2784
//,3
public class xxx {
    @Override
    public HostStats getHostStatistics(final long hostId) {
        final Answer answer = _agentMgr.easySend(hostId, new GetHostStatsCommand(_hostDao.findById(hostId).getGuid(), _hostDao.findById(hostId).getName(), hostId));

        if (answer != null && answer instanceof UnsupportedAnswer) {
            return null;
        }

        if (answer == null || !answer.getResult()) {
            final String msg = "Unable to obtain host " + hostId + " statistics. ";
            s_logger.warn(msg);
            return null;
        } else {

            // now construct the result object
            if (answer instanceof GetHostStatsAnswer) {
                return ((GetHostStatsAnswer)answer).getHostStats();
            }
        }
        return null;
    }

};