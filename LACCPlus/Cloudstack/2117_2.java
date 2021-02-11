//,temp,ServerDBSyncImpl.java,591,652,temp,ServerDBSyncImpl.java,509,545
//,3
public class xxx {
    public void createVirtualNetwork(NetworkVO dbNet, StringBuffer syncLogMesg) throws IOException {
        syncLogMesg.append("VN# DB: " + _manager.getCanonicalName(dbNet) + "(" + dbNet.getUuid() + "); VNC: none;  action: create\n");

        if (_manager.getDatabase().lookupVirtualNetwork(dbNet.getUuid(), _manager.getCanonicalName(dbNet), dbNet.getTrafficType()) != null) {
            s_logger.warn("VN model object is already present in DB: " + dbNet.getUuid() + ", name: " + dbNet.getName());
        }

        VirtualNetworkModel vnModel = new VirtualNetworkModel(dbNet, dbNet.getUuid(), _manager.getCanonicalName(dbNet), dbNet.getTrafficType());
        if (dbNet.getTrafficType() == TrafficType.Guest && dbNet.getNetworkACLId() != null) {
            NetworkACLVO acl = _networkACLDao.findById(dbNet.getNetworkACLId());
            NetworkPolicyModel policyModel = _manager.getDatabase().lookupNetworkPolicy(acl.getUuid());
            if (policyModel == null) {
                s_logger.error("Network(" + dbNet.getName() + ") has ACL but policy model not created: " +
                                       acl.getUuid() + ", name: " + acl.getName());
            } else {
                vnModel.addToNetworkPolicy(policyModel);
            }
        }
        vnModel.build(_manager.getModelController(), dbNet);

        if (_rwMode) {
            try {
                if (!vnModel.verify(_manager.getModelController())) {
                    vnModel.update(_manager.getModelController());
                }
            } catch (InternalErrorException ex) {
                s_logger.warn("create virtual-network", ex);
                syncLogMesg.append("Error: VN# VNC : Unable to create network " + dbNet.getName() + "\n");
                return;
            }
            s_logger.debug("add model " + vnModel.getName());
            _manager.getDatabase().getVirtualNetworks().add(vnModel);
            syncLogMesg.append("VN# VNC: " + dbNet.getUuid() + ", " + vnModel.getName() + " created\n");
        } else {
            syncLogMesg.append("VN# VNC: " + vnModel.getName() + " created \n");
        }
    }

};