//,temp,ServerDBSyncImpl.java,1060,1105,temp,ServerDBSyncImpl.java,986,1032
//,3
public class xxx {
    public Boolean equalNetworkPolicy(NetworkACLVO db, NetworkPolicy policy, StringBuffer syncLogMesg) {
        syncLogMesg.append("Policy# DB: " + db.getName() +
                "; VNC: " + policy.getName() + "; action: equal\n");
        NetworkPolicyModel current = _manager.getDatabase().lookupNetworkPolicy(policy.getUuid());
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
        }
        policyModel.setProject(project);
        List<NetworkACLItemVO> rules = _networkACLItemDao.listByACL(db.getId());
        try {
            policyModel.build(_manager.getModelController(), rules);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (_rwMode) {
            if (current != null) {
                _manager.getDatabase().getNetworkPolicys().remove(current);
            }
            s_logger.debug("add policy model " + policyModel.getName());
            _manager.getDatabase().getNetworkPolicys().add(policyModel);
            try {
                if (!policyModel.verify(_manager.getModelController())) {
                    policyModel.update(_manager.getModelController());
                }
            } catch (Exception ex) {
                s_logger.warn("update network-policy", ex);
            }
        } else {
            //compare
            if (current != null && current.compare(_manager.getModelController(), policyModel) == false) {
                syncLogMesg.append("Policy# DB: " + db.getName() +
                        "; VNC: " + policy.getName() + "; attributes differ\n");
                return false;
            }
        }
        return true;
    }

};