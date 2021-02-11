//,temp,FederationClientInterceptor.java,514,562,temp,FederationClientInterceptor.java,449,496
//,3
public class xxx {
  @Override
  public KillApplicationResponse forceKillApplication(
      KillApplicationRequest request) throws YarnException, IOException {

    long startTime = clock.getTime();

    if (request == null || request.getApplicationId() == null) {
      routerMetrics.incrAppsFailedKilled();
      RouterServerUtil.logAndThrowException(
          "Missing forceKillApplication request or ApplicationId.", null);
    }
    ApplicationId applicationId = request.getApplicationId();
    SubClusterId subClusterId = null;

    try {
      subClusterId = federationFacade
          .getApplicationHomeSubCluster(request.getApplicationId());
    } catch (YarnException e) {
      routerMetrics.incrAppsFailedKilled();
      RouterServerUtil.logAndThrowException("Application " + applicationId
          + " does not exist in FederationStateStore", e);
    }

    ApplicationClientProtocol clientRMProxy =
        getClientRMProxyForSubCluster(subClusterId);

    KillApplicationResponse response = null;
    try {
      LOG.info("forceKillApplication " + applicationId + " on SubCluster "
          + subClusterId);
      response = clientRMProxy.forceKillApplication(request);
    } catch (Exception e) {
      routerMetrics.incrAppsFailedKilled();
      LOG.error("Unable to kill the application report for "
          + request.getApplicationId() + "to SubCluster "
          + subClusterId.getId(), e);
      throw e;
    }

    if (response == null) {
      LOG.error("No response when attempting to kill the application "
          + applicationId + " to SubCluster " + subClusterId.getId());
    }

    long stopTime = clock.getTime();
    routerMetrics.succeededAppsKilled(stopTime - startTime);
    return response;
  }

};