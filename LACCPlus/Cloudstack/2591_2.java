//,temp,BasicNetworkTopology.java,268,308,temp,AdvancedNetworkTopology.java,90,118
//,3
public class xxx {
    @Override
    public boolean applyStaticRoutes(final List<StaticRouteProfile> staticRoutes, final List<DomainRouterVO> routers) throws ResourceUnavailableException {

        s_logger.debug("APPLYING STATIC ROUTES RULES");

        if (staticRoutes == null || staticRoutes.isEmpty()) {
            s_logger.debug("No static routes to apply");
            return true;
        }

        final StaticRoutesRules routesRules = new StaticRoutesRules(staticRoutes);

        boolean result = true;
        for (final VirtualRouter router : routers) {
            if (router.getState() == State.Running) {

                result = result && routesRules.accept(_advancedVisitor, router);

            } else if (router.getState() == State.Stopped || router.getState() == State.Stopping) {
                s_logger.debug("Router " + router.getInstanceName() + " is in " + router.getState() + ", so not sending StaticRoute command to the backend");
            } else {
                s_logger.warn("Unable to apply StaticRoute, virtual router is not in the right state " + router.getState());

                throw new ResourceUnavailableException("Unable to apply StaticRoute on the backend," + " virtual router is not in the right state", DataCenter.class,
                        router.getDataCenterId());
            }
        }
        return result;
    }

};