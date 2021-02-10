//,temp,FederationClientInterceptor.java,514,562,temp,FederationClientInterceptor.java,449,496
//,3
public class xxx {
  @Override
  public GetApplicationReportResponse getApplicationReport(
      GetApplicationReportRequest request) throws YarnException, IOException {

    long startTime = clock.getTime();

    if (request == null || request.getApplicationId() == null) {
      routerMetrics.incrAppsFailedRetrieved();
      RouterServerUtil.logAndThrowException(
          "Missing getApplicationReport request or applicationId information.",
          null);
    }

    SubClusterId subClusterId = null;

    try {
      subClusterId = federationFacade
          .getApplicationHomeSubCluster(request.getApplicationId());
    } catch (YarnException e) {
      routerMetrics.incrAppsFailedRetrieved();
      RouterServerUtil
          .logAndThrowException("Application " + request.getApplicationId()
              + " does not exist in FederationStateStore", e);
    }

    ApplicationClientProtocol clientRMProxy =
        getClientRMProxyForSubCluster(subClusterId);

    GetApplicationReportResponse response = null;
    try {
      response = clientRMProxy.getApplicationReport(request);
    } catch (Exception e) {
      routerMetrics.incrAppsFailedRetrieved();
      LOG.error("Unable to get the application report for "
          + request.getApplicationId() + "to SubCluster "
          + subClusterId.getId(), e);
      throw e;
    }

    if (response == null) {
      LOG.error("No response when attempting to retrieve the report of "
          + "the application " + request.getApplicationId() + " to SubCluster "
          + subClusterId.getId());
    }

    long stopTime = clock.getTime();
    routerMetrics.succeededAppsRetrieved(stopTime - startTime);
    return response;
  }

};