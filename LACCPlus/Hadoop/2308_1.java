//,temp,ContainerManagerImpl.java,321,337,temp,MiniYARNCluster.java,874,893
//,3
public class xxx {
  protected void createAMRMProxyService(Configuration conf) {
    this.amrmProxyEnabled =
        conf.getBoolean(YarnConfiguration.AMRM_PROXY_ENABLED,
            YarnConfiguration.DEFAULT_AMRM_PROXY_ENABLED) ||
            conf.getBoolean(YarnConfiguration.DIST_SCHEDULING_ENABLED,
                YarnConfiguration.DEFAULT_DIST_SCHEDULING_ENABLED);

    if (amrmProxyEnabled) {
      LOG.info("AMRMProxyService is enabled. "
          + "All the AM->RM requests will be intercepted by the proxy");
      this.setAMRMProxyService(
          new AMRMProxyService(this.context, this.dispatcher));
      addService(this.getAMRMProxyService());
    } else {
      LOG.info("AMRMProxyService is disabled");
    }
  }

};