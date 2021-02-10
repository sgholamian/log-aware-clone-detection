//,temp,AMRMProxyService.java,303,312,temp,AMRMProxyService.java,285,296
//,3
public class xxx {
  @Override
  public RegisterApplicationMasterResponse registerApplicationMaster(
      RegisterApplicationMasterRequest request) throws YarnException,
      IOException {
    LOG.info("Registering application master." + " Host:"
        + request.getHost() + " Port:" + request.getRpcPort()
        + " Tracking Url:" + request.getTrackingUrl());
    RequestInterceptorChainWrapper pipeline =
        authorizeAndGetInterceptorChain();
    return pipeline.getRootInterceptor()
        .registerApplicationMaster(request);
  }

};