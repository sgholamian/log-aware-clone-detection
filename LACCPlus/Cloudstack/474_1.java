//,temp,ClusterAlertAdapter.java,79,102,temp,ClusterAlertAdapter.java,60,77
//,3
public class xxx {
    private void onClusterNodeLeft(Object sender, ClusterNodeLeftEventArgs args) {

        if (s_logger.isDebugEnabled()) {
            for (ManagementServerHostVO mshost : args.getLeftNodes()) {
                s_logger.debug("Handle cluster node left alert, leaving node: " + mshost.getServiceIP() + ", msid: " + mshost.getMsid());
            }
        }

        for (ManagementServerHostVO mshost : args.getLeftNodes()) {
            if (mshost.getId() != args.getSelf().longValue()) {
                if (_mshostDao.increaseAlertCount(mshost.getId()) > 0) {
                    if (s_logger.isDebugEnabled()) {
                        s_logger.debug("Detected management server node " + mshost.getServiceIP() + " is down, send alert");
                    }
                    _alertMgr.sendAlert(AlertManager.AlertType.ALERT_TYPE_MANAGMENT_NODE, 0, new Long(0), "Management server node " + mshost.getServiceIP() + " is down",
                        "");
                } else {
                    if (s_logger.isDebugEnabled()) {
                        s_logger.debug("Detected management server node " + mshost.getServiceIP() + " is down, but alert has already been set");
                    }
                }
            }
        }
    }

};