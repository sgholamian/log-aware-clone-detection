//,temp,SQLFederationStateStore.java,771,823,temp,SQLFederationStateStore.java,671,725
//,3
public class xxx {
  @Override
  public GetApplicationHomeSubClusterResponse getApplicationHomeSubCluster(
      GetApplicationHomeSubClusterRequest request) throws YarnException {
    // Input validator
    FederationApplicationHomeSubClusterStoreInputValidator.validate(request);

    CallableStatement cstmt = null;
    Connection conn = null;

    SubClusterId homeRM = null;

    try {
      conn = getConnection();
      cstmt = conn.prepareCall(CALL_SP_GET_APPLICATION_HOME_SUBCLUSTER);

      // Set the parameters for the stored procedure
      cstmt.setString(1, request.getApplicationId().toString());
      cstmt.registerOutParameter(2, java.sql.Types.VARCHAR);

      // Execute the query
      long startTime = clock.getTime();
      cstmt.execute();
      long stopTime = clock.getTime();

      if (cstmt.getString(2) != null) {
        homeRM = SubClusterId.newInstance(cstmt.getString(2));
      } else {
        String errMsg =
            "Application " + request.getApplicationId() + " does not exist";
        FederationStateStoreUtils.logAndThrowStoreException(LOG, errMsg);
      }

      if (LOG.isDebugEnabled()) {
        LOG.debug("Got the information about the specified application  "
            + request.getApplicationId() + ". The AM is running in " + homeRM);
      }

      FederationStateStoreClientMetrics
          .succeededStateStoreCall(stopTime - startTime);

    } catch (SQLException e) {
      FederationStateStoreClientMetrics.failedStateStoreCall();
      FederationStateStoreUtils.logAndThrowRetriableException(LOG,
          "Unable to obtain the application information "
              + "for the specified application " + request.getApplicationId(),
          e);
    } finally {

      // Return to the pool the CallableStatement and the Connection
      FederationStateStoreUtils.returnToPool(LOG, cstmt, conn);
    }
    return GetApplicationHomeSubClusterResponse
        .newInstance(ApplicationHomeSubCluster
            .newInstance(request.getApplicationId(), homeRM));
  }

};