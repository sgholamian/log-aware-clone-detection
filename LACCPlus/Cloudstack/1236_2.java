//,temp,ServerDBSyncImpl.java,902,937,temp,ContrailManagerImpl.java,931,965
//,3
public class xxx {
    @Override
    public boolean createFloatingIp(PublicIpAddress ip) {
        VirtualNetworkModel vnModel = lookupPublicNetworkModel();
        assert vnModel != null : "public network vn model is null";
        FloatingIpPoolModel fipPoolModel = vnModel.getFipPoolModel();

        /* create only, no updates */
        if (fipPoolModel == null) {
            fipPoolModel = new FloatingIpPoolModel();
            fipPoolModel.addToVirtualNetwork(vnModel);
            fipPoolModel.build(getModelController());
            try {
                fipPoolModel.update(getModelController());
                vnModel.setFipPoolModel(fipPoolModel);
            } catch (Exception ex) {
                s_logger.warn("floating-ip-pool create: ", ex);
                return false;
            }
        }

        FloatingIpModel fipModel = fipPoolModel.getFloatingIpModel(ip.getUuid());
        /* create only, no updates*/
        if (fipModel == null) {
            fipModel = new FloatingIpModel(ip.getUuid());
            fipModel.addToFloatingIpPool(fipPoolModel);
            fipModel.build(getModelController(), ip);
            try {
                fipModel.update(getModelController());
            } catch (Exception ex) {
                s_logger.warn("floating-ip create: ", ex);
                return false;
            }
        }
        return true;
    }

};