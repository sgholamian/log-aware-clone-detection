//,temp,ContrailManagerImpl.java,648,706,temp,ContrailManagerImpl.java,584,629
//,3
public class xxx {
    @Override
    public List<NetworkVO> findManagedNetworks(List<TrafficType> types) {

        SearchBuilder<NetworkVO> searchBuilder = _networksDao.createSearchBuilder();
        searchBuilder.and("trafficType", searchBuilder.entity().getTrafficType(), Op.IN);
        searchBuilder.and("networkOfferingId", searchBuilder.entity().getNetworkOfferingId(), Op.IN);

        SearchCriteria<NetworkVO> sc = searchBuilder.create();
        List<Long> offerings = new ArrayList<Long>();
        offerings.add(getRouterOffering().getId());
        offerings.add(getVpcRouterOffering().getId());
        offerings.add(getPublicRouterOffering().getId());
        sc.setParameters("networkOfferingId", offerings.toArray());

        if (types == null || types.isEmpty()) {
            types = new ArrayList<TrafficType>();
            types.add(TrafficType.Control);
            types.add(TrafficType.Management);
            types.add(TrafficType.Public);
            types.add(TrafficType.Storage);
            types.add(TrafficType.Guest);
        }
        sc.setParameters("trafficType", types.toArray());

        List<NetworkVO> dbNets = _networksDao.search(sc, null);
        if (dbNets == null) {
            s_logger.debug("no juniper managed networks for the given traffic types: " + types.toString());
            dbNets = new ArrayList<NetworkVO>();
        }

        List<PhysicalNetworkVO> phys_list = _physicalNetworkDao.listAll();
        final String provider = Network.Provider.JuniperContrailRouter.getName();
        final String vpcProvider = Provider.JuniperContrailVpcRouter.getName();
        for (Iterator<PhysicalNetworkVO> iter = phys_list.iterator(); iter.hasNext();) {
            PhysicalNetworkVO phys = iter.next();
            if (_physProviderDao.findByServiceProvider(phys.getId(), provider) != null ||
                    _physProviderDao.findByServiceProvider(phys.getId(), vpcProvider) != null) {
                List<NetworkVO> infraNets = new ArrayList<NetworkVO>();
                findInfrastructureNetworks(phys, infraNets);
                for (NetworkVO net : infraNets) {
                    if (types == null || types.isEmpty()) {
                        if (!dbNets.contains(net)) {
                            dbNets.add(net);
                        }
                        continue;
                    }
                    for (TrafficType type : types) {
                        if (net.getTrafficType() == type) {
                            if (!dbNets.contains(net)) {
                                dbNets.add(net);
                            }
                            break;
                        }
                    }
                }
            }
        }
        return dbNets;
    }

};