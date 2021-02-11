//,temp,ClusterAlertAdapter.java,79,102,temp,ClusterAlertAdapter.java,60,77
//,3
public class xxx {
    private void onClusterNodeJoined(Object sender, ClusterNodeJoinEventArgs args) {
        if (s_logger.isDebugEnabled()) {
            for (ManagementServerHostVO mshost : args.getJoinedNodes()) {
                s_logger.debug("Handle cluster node join alert, joined node: " + mshost.getServiceIP() + ", msidL: " + mshost.getMsid());
            }
        }

        for (ManagementServerHostVO mshost : args.getJoinedNodes()) {
            if (mshost.getId() == args.getSelf().longValue()) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Management server node " + mshost.getServiceIP() + " is up, send alert");
                }

                _alertMgr.sendAlert(AlertManager.AlertType.ALERT_TYPE_MANAGMENT_NODE, 0, new Long(0), "Management server node " + mshost.getServiceIP() + " is up", "");
                break;
            }
        }
    }

};