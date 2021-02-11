//,temp,ServerDBSyncImpl.java,942,966,temp,ServerDBSyncImpl.java,450,473
//,3
public class xxx {
    @SuppressWarnings({"unchecked"})
    public boolean syncVirtualNetwork() throws Exception {
        final ApiConnector api = _manager.getApiConnector();
        try {

            List<TrafficType> types = new ArrayList<TrafficType>();
            types.add(TrafficType.Public);
            types.add(TrafficType.Guest);
            List<NetworkVO> dbNets = _manager.findManagedNetworks(types);

            List<VirtualNetwork> vList = (List<VirtualNetwork>)api.list(VirtualNetwork.class, null);
            List<VirtualNetwork> vncList = new ArrayList<VirtualNetwork>();
            for (VirtualNetwork vn : vList) {
                if (!_manager.isSystemDefaultNetwork(vn)) {
                    vncList.add(vn);
                }
            }
            s_logger.debug("sync VN - DB size: " + dbNets.size() + " VNC Size: " + vncList.size());
            return _dbSync.syncGeneric(VirtualNetwork.class, dbNets, vncList);
        } catch (Exception ex) {
            s_logger.warn("sync virtual-networks", ex);
            throw ex;
        }
    }

};