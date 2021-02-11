//,temp,HypervisorTemplateAdapter.java,139,152,temp,OCFS2ManagerImpl.java,105,120
//,3
public class xxx {
    private boolean prepareNodes(String clusterName, List<HostVO> hosts) {
        PrepareOCFS2NodesCommand cmd = new PrepareOCFS2NodesCommand(clusterName, marshalNodes(hosts));
        for (HostVO h : hosts) {
            Answer ans = _agentMgr.easySend(h.getId(), cmd);
            if (ans == null) {
                s_logger.debug("Host " + h.getId() + " is not in UP state, skip preparing OCFS2 node on it");
                continue;
            }
            if (!ans.getResult()) {
                s_logger.warn("PrepareOCFS2NodesCommand failed on host " + h.getId() + " " + ans.getDetails());
                return false;
            }
        }

        return true;
    }

};