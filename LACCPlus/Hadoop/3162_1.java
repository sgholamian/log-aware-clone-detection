//,temp,SQLFederationStateStore.java,608,669,temp,SQLFederationStateStore.java,247,306
//,3
public class xxx {
  @Override
  public UpdateApplicationHomeSubClusterResponse updateApplicationHomeSubCluster(
      UpdateApplicationHomeSubClusterRequest request) throws YarnException {

    // Input validator
    FederationApplicationHomeSubClusterStoreInputValidator.validate(request);

    CallableStatement cstmt = null;
    Connection conn = null;

    ApplicationId appId =
        request.getApplicationHomeSubCluster().getApplicationId();
    SubClusterId subClusterId =
        request.getApplicationHomeSubCluster().getHomeSubCluster();

    try {
      conn = getConnection();
      cstmt = conn.prepareCall(CALL_SP_UPDATE_APPLICATION_HOME_SUBCLUSTER);

      // Set the parameters for the stored procedure
      cstmt.setString(1, appId.toString());
      cstmt.setString(2, subClusterId.getId());
      cstmt.registerOutParameter(3, java.sql.Types.INTEGER);

      // Execute the query
      long startTime = clock.getTime();
      cstmt.executeUpdate();
      long stopTime = clock.getTime();

      // Check the ROWCOUNT value, if it is equal to 0 it means the call
      // did not update the application into FederationStateStore
      if (cstmt.getInt(3) == 0) {
        String errMsg = "Application " + appId + " does not exist";
        FederationStateStoreUtils.logAndThrowStoreException(LOG, errMsg);
      }
      // Check the ROWCOUNT value, if it is different from 1 it means the call
      // had a wrong behavior. Maybe the database is not set correctly.
      if (cstmt.getInt(3) != 1) {
        String errMsg =
            "Wrong behavior during the update of SubCluster " + subClusterId;
        FederationStateStoreUtils.logAndThrowStoreException(LOG, errMsg);
      }

      LOG.info(
          "Update the SubCluster to {} for application {} in the StateStore",
          subClusterId, appId);
      FederationStateStoreClientMetrics
          .succeededStateStoreCall(stopTime - startTime);

    } catch (SQLException e) {
      FederationStateStoreClientMetrics.failedStateStoreCall();
      FederationStateStoreUtils
          .logAndThrowRetriableException(LOG,
              "Unable to update the application "
                  + request.getApplicationHomeSubCluster().getApplicationId(),
              e);
    } finally {
      // Return to the pool the CallableStatement and the Connection
      FederationStateStoreUtils.returnToPool(LOG, cstmt, conn);
    }
    return UpdateApplicationHomeSubClusterResponse.newInstance();
  }

};