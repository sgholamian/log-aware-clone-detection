//,temp,ClusterManagerImpl.java,473,492,temp,ClusterManagerImpl.java,455,471
//,3
public class xxx {
    public void notifyNodeLeft(final List<ManagementServerHostVO> nodeList) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Notify management server node left to listeners.");
        }

        for (final ManagementServerHostVO mshost : nodeList) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Leaving node, IP: " + mshost.getServiceIP() + ", msid: " + mshost.getMsid());
            }
            cancelClusterRequestToPeer(String.valueOf(mshost.getMsid()));
        }

        synchronized (_listeners) {
            for (final ClusterManagerListener listener : _listeners) {
                listener.onManagementNodeLeft(nodeList, _mshostId);
            }
        }

        SubscriptionMgr.getInstance().notifySubscribers(ClusterManager.ALERT_SUBJECT, this, new ClusterNodeLeftEventArgs(_mshostId, nodeList));
    }

};