//,temp,sample_2920.java,2,10,temp,sample_2966.java,2,10
//,3
public class xxx {
public void notifyMonitorsOfNewlyAddedHost(long hostId) {
for (final Pair<Integer, Listener> monitor : _hostMonitors) {
if (s_logger.isDebugEnabled()) {


log.info("sending host added to listener");
}
}
}

};