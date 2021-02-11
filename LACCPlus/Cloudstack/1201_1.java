//,temp,ContrailManagerImpl.java,904,929,temp,ContrailManagerImpl.java,793,816
//,3
public class xxx {
    @Override
    public VirtualNetworkModel lookupPublicNetworkModel() {
        List<TrafficType> types = new ArrayList<TrafficType>();
        types.add(TrafficType.Public);
        List<NetworkVO> dbNets = findManagedNetworks(types);
        if (dbNets == null) {
            return null;
        }
        NetworkVO net = dbNets.get(0);

        VirtualNetworkModel vnModel = getDatabase().lookupVirtualNetwork(net.getUuid(), getCanonicalName(net), TrafficType.Public);
        if (vnModel == null) {
            vnModel = new VirtualNetworkModel(net, net.getUuid(),
                    getCanonicalName(net), net.getTrafficType());
            vnModel.setProperties(getModelController(), net);
        }
        try {
            if (!vnModel.verify(getModelController())) {
                vnModel.update(getModelController());
            }
            getDatabase().getVirtualNetworks().add(vnModel);
        } catch (Exception ex) {
            s_logger.warn("virtual-network update: ", ex);
        }
        return vnModel;
    }

};