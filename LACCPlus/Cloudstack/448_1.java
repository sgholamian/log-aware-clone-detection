//,temp,ServerDBSyncImpl.java,363,379,temp,ServerDBSyncImpl.java,262,276
//,3
public class xxx {
    public void deleteProject(net.juniper.contrail.api.types.Project vnc, StringBuffer syncLogMesg) throws IOException {
        final ApiConnector api = _manager.getApiConnector();
        api.read(vnc);
        syncLogMesg.append("Project# DB: none; VNC: " + vnc.getName() + "(" + vnc.getUuid() + "); action: delete\n");

        try {
            deleteChildren(vnc.getVirtualNetworks(), VirtualNetwork.class, syncLogMesg);
            deleteChildren(vnc.getSecurityGroups(), net.juniper.contrail.api.types.SecurityGroup.class, syncLogMesg);
            deleteChildren(vnc.getNetworkIpams(), net.juniper.contrail.api.types.NetworkIpam.class, syncLogMesg);
            deleteChildren(vnc.getNetworkPolicys(), net.juniper.contrail.api.types.NetworkPolicy.class, syncLogMesg);
        } catch (Exception ex) {
            s_logger.warn("deleteProject", ex);
        }

        api.delete(vnc);
        syncLogMesg.append("Project# VNC: " + vnc.getName() + " deleted\n");
    }

};