//,temp,ServerDBSyncImpl.java,902,937,temp,ContrailManagerImpl.java,931,965
//,3
public class xxx {
    public Boolean equalFloatingIp(IPAddressVO db, FloatingIp vnc, StringBuffer syncLogMsg) throws IOException {

        syncLogMsg.append("fip# DB: " + db.getAddress().addr() + "; VNC: " + vnc.getAddress() + "; action: equal" + "\n");

        VirtualNetworkModel vnModel = _manager.lookupPublicNetworkModel();
        assert vnModel != null : "public network vn model is null";

        FloatingIpPoolModel fipPoolModel = vnModel.getFipPoolModel();
        if (fipPoolModel == null) {
            fipPoolModel = new FloatingIpPoolModel();
            fipPoolModel.addToVirtualNetwork(vnModel);
            fipPoolModel.build(_manager.getModelController());
            try {
                fipPoolModel.update(_manager.getModelController());
                vnModel.setFipPoolModel(fipPoolModel);
            } catch (Exception ex) {
                s_logger.warn("floating-ip-pool create: ", ex);
                return false;
            }
        }

        FloatingIpModel current = fipPoolModel.getFloatingIpModel(db.getUuid());
        if (current == null) {
            s_logger.debug("add model " + db.getAddress().addr());
            FloatingIpModel fipModel = new FloatingIpModel(db.getUuid());
            fipModel.addToFloatingIpPool(fipPoolModel);
            fipModel.build(_manager.getModelController(), PublicIp.createFromAddrAndVlan(db, _vlanDao.findById(db.getVlanId())));
            try {
                fipModel.update(_manager.getModelController());
            } catch (Exception ex) {
                s_logger.warn("floating-ip create: ", ex);
                return false;
            }
        }
        return true;
    }

};