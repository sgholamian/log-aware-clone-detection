//,temp,ServerDBSyncImpl.java,349,361,temp,ServerDBSyncImpl.java,248,260
//,2
public class xxx {
    @Override
    public void createProject(ProjectVO db, StringBuffer syncLogMesg) throws IOException {
        final ApiConnector api = _manager.getApiConnector();
        net.juniper.contrail.api.types.Project vnc = new net.juniper.contrail.api.types.Project();
        vnc.setName(db.getName());
        vnc.setUuid(db.getUuid());
        if (!api.create(vnc)) {
            s_logger.error("Unable to create project: " + vnc.getName());
            syncLogMesg.append("Error: Virtual project# VNC : Unable to create project: " + vnc.getName() + "\n");
            return;
        }
        syncLogMesg.append("Project# VNC: " + vnc.getName() + " created \n");
    }

};