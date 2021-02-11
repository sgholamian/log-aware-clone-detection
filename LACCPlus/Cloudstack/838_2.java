//,temp,ClusterManagerImpl.java,473,492,temp,ClusterManagerImpl.java,455,471
//,3
public class xxx {
    public void notifyNodeJoined(final List<ManagementServerHostVO> nodeList) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Notify management server node join to listeners.");

            for (final ManagementServerHostVO mshost : nodeList) {
                s_logger.debug("Joining node, IP: " + mshost.getServiceIP() + ", msid: " + mshost.getMsid());
            }
        }

        synchronized (_listeners) {
            for (final ClusterManagerListener listener : _listeners) {
                listener.onManagementNodeJoined(nodeList, _mshostId);
            }
        }

        SubscriptionMgr.getInstance().notifySubscribers(ClusterManager.ALERT_SUBJECT, this, new ClusterNodeJoinEventArgs(_mshostId, nodeList));
    }

};