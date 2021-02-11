//,temp,ServerDBSyncImpl.java,349,361,temp,ServerDBSyncImpl.java,248,260
//,2
public class xxx {
    @Override
    public void createDomain(DomainVO db, StringBuffer syncLogMesg) throws IOException {
        final ApiConnector api = _manager.getApiConnector();
        net.juniper.contrail.api.types.Domain vnc = new net.juniper.contrail.api.types.Domain();
        vnc.setName(db.getName());
        vnc.setUuid(db.getUuid());
        if (!api.create(vnc)) {
            s_logger.error("Unable to create domain " + vnc.getName());
            syncLogMesg.append("Error: Virtual domain# VNC : Unable to create domain: " + vnc.getName() + "\n");
            return;
        }
        syncLogMesg.append("Domain# VNC: " + vnc.getName() + " created \n");
    }

};