//,temp,OvsTunnelManagerImpl.java,894,911,temp,OvsTunnelManagerImpl.java,733,750
//,3
public class xxx {
    private boolean sendVpcRoutingPolicyChangeUpdate(OvsVpcRoutingPolicyConfigCommand updateCmd, long hostId, String bridgeName) {
        try {
            s_logger.debug("Sending VPC routing policies change update to the host " + hostId);
            updateCmd.setHostId(hostId);
            updateCmd.setBridgeName(bridgeName);
            Answer ans = _agentMgr.send(hostId, updateCmd);
            if (ans.getResult()) {
                s_logger.debug("Successfully updated the host " + hostId + " with latest VPC routing policies." );
                return true;
            }  else {
                s_logger.debug("Failed to update the host " + hostId + " with latest routing policies." );
                return false;
            }
        } catch (Exception e) {
            s_logger.debug("Failed to updated the host " + hostId + " with latest routing policies due to" , e );
            return false;
        }
    }

};