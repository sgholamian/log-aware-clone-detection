//,temp,MiniYARNCluster.java,906,925,temp,MiniYARNCluster.java,874,893
//,2
public class xxx {
    @Override
    protected void createAMRMProxyService(Configuration conf) {
      this.amrmProxyEnabled =
          conf.getBoolean(YarnConfiguration.AMRM_PROXY_ENABLED,
              YarnConfiguration.DEFAULT_AMRM_PROXY_ENABLED) ||
              conf.getBoolean(YarnConfiguration.DIST_SCHEDULING_ENABLED,
                  YarnConfiguration.DEFAULT_DIST_SCHEDULING_ENABLED);

      if (this.amrmProxyEnabled) {
        LOG.info("CustomAMRMProxyService is enabled. "
            + "All the AM->RM requests will be intercepted by the proxy");
        AMRMProxyService amrmProxyService =
            useRpc ? new AMRMProxyService(getContext(), dispatcher)
                : new ShortCircuitedAMRMProxy(getContext(), dispatcher);
        this.setAMRMProxyService(amrmProxyService);
        addService(this.getAMRMProxyService());
      } else {
        LOG.info("CustomAMRMProxyService is disabled");
      }
    }

};