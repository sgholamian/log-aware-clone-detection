//,temp,ContrailVpcElementImpl.java,102,186,temp,ServerDBSyncImpl.java,591,652
//,3
public class xxx {
    @Override
    public boolean applyNetworkACLs(Network net,
            List<? extends NetworkACLItem> rules)
                    throws ResourceUnavailableException {
        s_logger.debug("NetworkElement applyNetworkACLs");
        if (rules == null || rules.isEmpty()) {
            s_logger.debug("no rules to apply");
            return true;
        }

        Long aclId = rules.get(0).getAclId();
        NetworkACLVO acl = _networkACLDao.findById(aclId);
        NetworkPolicyModel policyModel = _manager.getDatabase().lookupNetworkPolicy(acl.getUuid());
        if (policyModel == null) {
            /*
             * For the first time, when a CS ACL applied to a network, create a network-policy in VNC
             * and when there are no networks associated to CS ACL, delete it from VNC.
             */
            policyModel = new NetworkPolicyModel(acl.getUuid(), acl.getName());
            net.juniper.contrail.api.types.Project project;
            try {
                project = _manager.getVncProject(net.getDomainId(), net.getAccountId());
                if (project == null) {
                    project = _manager.getDefaultVncProject();
                }
            } catch (IOException ex) {
                s_logger.warn("read project", ex);
                return false;
            }
            policyModel.setProject(project);
        }

        VirtualNetworkModel vnModel = _manager.getDatabase().lookupVirtualNetwork(net.getUuid(),
                _manager.getCanonicalName(net), net.getTrafficType());
        NetworkPolicyModel oldPolicyModel = null;
        /* this method is called when network is destroyed too, hence vn model might have been deleted already */
        if (vnModel != null) {
            oldPolicyModel = vnModel.getNetworkPolicyModel();
            vnModel.addToNetworkPolicy(policyModel);
        }

        try {
            policyModel.build(_manager.getModelController(), rules);
        } catch (Exception e) {
            s_logger.error(e);
            e.printStackTrace();
            return false;
        }

        try {
            if (!policyModel.verify(_manager.getModelController())) {
                policyModel.update(_manager.getModelController());
            }
            _manager.getDatabase().getNetworkPolicys().add(policyModel);
        } catch (Exception ex) {
            s_logger.error("network-policy update: ", ex);
            ex.printStackTrace();
            return false;
        }

        if (!policyModel.hasPolicyRules()) {
            try {
                policyModel.delete(_manager.getModelController());
                _manager.getDatabase().getNetworkPolicys().remove(policyModel);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        /*
         * if no other VNs are associated with the old policy,
         * we could delete it from the Contrail VNC
         */
        if (policyModel != oldPolicyModel && oldPolicyModel != null && !oldPolicyModel.hasDescendents()) {
            try {
                oldPolicyModel.delete(_manager.getModelController());
                _manager.getDatabase().getNetworkPolicys().remove(oldPolicyModel);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

};