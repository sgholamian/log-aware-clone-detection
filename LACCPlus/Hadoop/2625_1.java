//,temp,AMRMProxyService.java,303,312,temp,AMRMProxyService.java,285,296
//,3
public class xxx {
  @Override
  public FinishApplicationMasterResponse finishApplicationMaster(
      FinishApplicationMasterRequest request) throws YarnException,
      IOException {
    LOG.info("Finishing application master. Tracking Url:"
        + request.getTrackingUrl());
    RequestInterceptorChainWrapper pipeline =
        authorizeAndGetInterceptorChain();
    return pipeline.getRootInterceptor().finishApplicationMaster(request);
  }

};