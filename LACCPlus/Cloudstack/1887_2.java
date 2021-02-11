//,temp,NetworkACLManagerImpl.java,87,125,temp,VpcManagerImpl.java,1919,1951
//,3
public class xxx {
    protected boolean applyStaticRoutes(final List<? extends StaticRoute> routes, final Account caller, final boolean updateRoutesInDB) throws ResourceUnavailableException {
        final boolean success = true;
        final List<StaticRouteProfile> staticRouteProfiles = new ArrayList<StaticRouteProfile>(routes.size());
        final Map<Long, VpcGateway> gatewayMap = new HashMap<Long, VpcGateway>();
        for (final StaticRoute route : routes) {
            VpcGateway gateway = gatewayMap.get(route.getVpcGatewayId());
            if (gateway == null) {
                gateway = _vpcGatewayDao.findById(route.getVpcGatewayId());
                gatewayMap.put(gateway.getId(), gateway);
            }
            staticRouteProfiles.add(new StaticRouteProfile(route, gateway));
        }
        if (!applyStaticRoutes(staticRouteProfiles)) {
            s_logger.warn("Routes are not completely applied");
            return false;
        } else {
            if (updateRoutesInDB) {
                for (final StaticRoute route : routes) {
                    if (route.getState() == StaticRoute.State.Revoke) {
                        _staticRouteDao.remove(route.getId());
                        s_logger.debug("Removed route " + route + " from the DB");
                    } else if (route.getState() == StaticRoute.State.Add) {
                        final StaticRouteVO ruleVO = _staticRouteDao.findById(route.getId());
                        ruleVO.setState(StaticRoute.State.Active);
                        _staticRouteDao.update(ruleVO.getId(), ruleVO);
                        s_logger.debug("Marked route " + route + " with state " + StaticRoute.State.Active);
                    }
                }
            }
        }

        return success;
    }

};