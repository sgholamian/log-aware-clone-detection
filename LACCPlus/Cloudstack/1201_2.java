//,temp,ContrailManagerImpl.java,904,929,temp,ContrailManagerImpl.java,793,816
//,3
public class xxx {
    private void initializeDefaultVirtualNetworkModels() {
        List<TrafficType> types = new ArrayList<TrafficType>();
        types.add(TrafficType.Management);
        types.add(TrafficType.Storage);
        types.add(TrafficType.Control);

        List<NetworkVO> dbNets = findManagedNetworks(types);
        for (NetworkVO net : dbNets) {

            VirtualNetworkModel vnModel = getDatabase().lookupVirtualNetwork(null, getCanonicalName(net), net.getTrafficType());
            if (vnModel == null) {
                vnModel = new VirtualNetworkModel(net, null, getCanonicalName(net), net.getTrafficType());
                vnModel.build(getModelController(), net);
                try {
                    if (!vnModel.verify(getModelController())) {
                        vnModel.update(getModelController());
                    }
                } catch (Exception ex) {
                    s_logger.warn("virtual-network update: ", ex);
                }
                getDatabase().getVirtualNetworks().add(vnModel);
            }
        }
    }

};