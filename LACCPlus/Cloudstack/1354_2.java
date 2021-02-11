//,temp,ContrailManagerImpl.java,648,706,temp,ContrailManagerImpl.java,584,629
//,3
public class xxx {
    @Override
    public List<NetworkVO> findSystemNetworks(List<TrafficType> types) {
        SearchBuilder<NetworkVO> searchBuilder = _networksDao.createSearchBuilder();
        searchBuilder.and("trafficType", searchBuilder.entity().getTrafficType(), Op.IN);
        SearchCriteria<NetworkVO> sc = searchBuilder.create();
        if (types == null || types.isEmpty()) {
            types = new ArrayList<TrafficType>();
            types.add(TrafficType.Control);
            types.add(TrafficType.Management);
            types.add(TrafficType.Public);
            types.add(TrafficType.Storage);
        }
        sc.setParameters("trafficType", types.toArray());
        List<NetworkVO> dbNets = _networksDao.search(sc, null);
        if (dbNets == null) {
            s_logger.debug("no system networks for the given traffic types: " + types.toString());
            dbNets = new ArrayList<NetworkVO>();
        }

        List<PhysicalNetworkVO> phys_list = _physicalNetworkDao.listAll();
        final String provider = Provider.JuniperContrailRouter.getName();
        for (Iterator<PhysicalNetworkVO> iter = phys_list.iterator(); iter.hasNext(); ) {
            PhysicalNetworkVO phys = iter.next();
            if (_physProviderDao.findByServiceProvider(phys.getId(), provider) != null) {
                List<NetworkVO> infraNets = new ArrayList<NetworkVO>();
                findInfrastructureNetworks(phys, infraNets);
                for (NetworkVO net:infraNets) {
                    if (types == null || types.isEmpty()) {
                        if (!dbNets.contains(net)) {
                            dbNets.add(net);
                        }
                        continue;
                    }
                    for(TrafficType type:types) {
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