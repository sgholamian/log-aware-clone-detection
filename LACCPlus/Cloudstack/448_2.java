//,temp,ServerDBSyncImpl.java,363,379,temp,ServerDBSyncImpl.java,262,276
//,3
public class xxx {
    public void deleteDomain(net.juniper.contrail.api.types.Domain vnc, StringBuffer syncLogMesg) throws IOException {
        final ApiConnector api = _manager.getApiConnector();
        api.read(vnc);
        syncLogMesg.append("Domain# DB: none; VNC: " + vnc.getName() + "(" + vnc.getUuid() + "); action: delete\n");

        /* delete all projects under this domain */
        try {
            deleteChildren(vnc.getProjects(), net.juniper.contrail.api.types.Project.class, syncLogMesg);
        } catch (Exception ex) {
            s_logger.warn("deleteDomain", ex);
        }

        api.delete(vnc);
        syncLogMesg.append("Domain# VNC: " + vnc.getName() + " deleted\n");
    }

};