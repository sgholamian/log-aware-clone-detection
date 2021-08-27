//,temp,ClusteredRoutePolicy.java,194,205,temp,ServiceRegistrationRoutePolicy.java,72,84
//,3
public class xxx {
    @Override
    protected void doInit() throws Exception {
        if (clusterService == null) {
            clusterService = ClusterServiceHelper.lookupService(camelContext, clusterServiceSelector)
                    .orElseThrow(() -> new IllegalStateException("CamelCluster service not found"));
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("ClusteredRoutePolicy {} is using ClusterService instance {} (id={}, type={})", this, clusterService,
                    clusterService.getId(),
                    clusterService.getClass().getName());
        }
    }

};