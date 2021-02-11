//,temp,OvsTunnelManagerImpl.java,894,911,temp,OvsTunnelManagerImpl.java,733,750
//,3
public class xxx {
    public boolean sendVpcTopologyChangeUpdate(OvsVpcPhysicalTopologyConfigCommand updateCmd, long hostId, String bridgeName) {
        try {
            s_logger.debug("Sending VPC topology change update to the host " + hostId);
            updateCmd.setHostId(hostId);
            updateCmd.setBridgeName(bridgeName);
            Answer ans = _agentMgr.send(hostId, updateCmd);
            if (ans.getResult()) {
                s_logger.debug("Successfully updated the host " + hostId + " with latest VPC topology." );
                return true;
            }  else {
                s_logger.debug("Failed to update the host " + hostId + " with latest VPC topology." );
                return false;
            }
        } catch (Exception e) {
            s_logger.debug("Failed to updated the host " + hostId + " with latest VPC topology.", e );
            return false;
        }
    }

};