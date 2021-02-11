//,temp,ServerDBSyncImpl.java,1060,1105,temp,ServerDBSyncImpl.java,986,1032
//,3
public class xxx {
    public void createNetworkPolicy(NetworkACLVO db, StringBuffer syncLogMesg) throws IOException {
        syncLogMesg.append("Policy# DB: " + db.getName() +
                "(" + db.getUuid() + "); VNC: none;  action: create\n");

        if (_manager.getDatabase().lookupNetworkPolicy(db.getUuid()) != null) {
             s_logger.warn("Policy model object is already present in DB: " +
                                   db.getUuid() + ", name: " + db.getName());
        }
        NetworkPolicyModel policyModel = new NetworkPolicyModel(db.getUuid(), db.getName());
        net.juniper.contrail.api.types.Project project = null;
        try {
            VpcVO vpc = _vpcDao.findById(db.getVpcId());
            if (vpc != null) {
                project = _manager.getVncProject(vpc.getDomainId(), vpc.getAccountId());
            } else {
                project = _manager.getDefaultVncProject();
            }
        } catch (IOException ex) {
            s_logger.warn("read project", ex);
            throw ex;
        }
        policyModel.setProject(project);
        List<NetworkACLItemVO> rules = _networkACLItemDao.listByACL(db.getId());
        try {
            policyModel.build(_manager.getModelController(), rules);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (_rwMode) {
            try {
                if (!policyModel.verify(_manager.getModelController())) {
                    policyModel.update(_manager.getModelController());
                }
            } catch (Exception ex) {
                s_logger.warn("create network-policy", ex);
                syncLogMesg.append("Error: Policy# VNC : Unable to create network policy " +
                    db.getName() + "\n");
                return;
            }
            s_logger.debug("add model " + policyModel.getName());
            _manager.getDatabase().getNetworkPolicys().add(policyModel);
            syncLogMesg.append("Policy# VNC: " + db.getUuid() + ", " + policyModel.getName() + " created\n");
        } else {
            syncLogMesg.append("Policy# VNC: " + policyModel.getName() + " created \n");
        }
    }

};