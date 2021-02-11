//,temp,ContrailVpcElementImpl.java,102,186,temp,ServerDBSyncImpl.java,591,652
//,3
public class xxx {
    public Boolean equalVirtualNetwork(NetworkVO dbn, VirtualNetwork vnet, StringBuffer syncLogMesg) {
        syncLogMesg.append("VN# DB: " + _manager.getCanonicalName(dbn) + "; VNC: " + vnet.getName() + "; action: equal\n");

        VirtualNetworkModel current = _manager.getDatabase().lookupVirtualNetwork(vnet.getUuid(), _manager.getCanonicalName(dbn), dbn.getTrafficType());

        VirtualNetworkModel vnModel = new VirtualNetworkModel(dbn, vnet.getUuid(), _manager.getCanonicalName(dbn), dbn.getTrafficType());
        if (dbn.getTrafficType() == TrafficType.Guest && dbn.getNetworkACLId() != null) {
            NetworkACLVO acl = _networkACLDao.findById(dbn.getNetworkACLId());
            NetworkPolicyModel policyModel = _manager.getDatabase().lookupNetworkPolicy(acl.getUuid());
            if (policyModel == null) {
                s_logger.error("Network(" + dbn.getName() + ") has ACL but policy model not created: " +
                                       acl.getUuid() + ", name: " + acl.getName());
            } else {
                vnModel.addToNetworkPolicy(policyModel);
            }
        }
        vnModel.build(_manager.getModelController(), dbn);

        if (_rwMode) {
            if (current != null) {
                FloatingIpPoolModel fipPoolModel = current.getFipPoolModel();
                if (fipPoolModel != null) {
                    vnModel.setFipPoolModel(fipPoolModel);
                    fipPoolModel.addToVirtualNetwork(vnModel);
                }
                _manager.getDatabase().getVirtualNetworks().remove(current);
            }
            s_logger.debug("add model " + vnModel.getName());
            _manager.getDatabase().getVirtualNetworks().add(vnModel);
            try {
                if (!vnModel.verify(_manager.getModelController())) {
                    vnModel.update(_manager.getModelController());
                }
            } catch (Exception ex) {
                s_logger.warn("update virtual-network", ex);
            }
            if (current != null) {
                NetworkPolicyModel oldPolicyModel = current.getNetworkPolicyModel();
                if (oldPolicyModel != vnModel.getNetworkPolicyModel()) {
                    /*
                     * if no other VNs are associated with the old policy,
                     * we could delete it from the Contrail VNC
                     */
                    if (oldPolicyModel != null && !oldPolicyModel.hasDescendents()) {
                        try {
                            oldPolicyModel.delete(_manager.getModelController());
                            _manager.getDatabase().getNetworkPolicys().remove(oldPolicyModel);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            //compare
            if (current != null && current.compare(_manager.getModelController(), vnModel) == false) {
                syncLogMesg.append("VN# DB: " + _manager.getCanonicalName(dbn) + "; VNC: " + vnet.getName() + "; attributes differ\n");
                return false;
            }
        }
        return true;
    }

};