//,temp,SQLFederationStateStore.java,771,823,temp,SQLFederationStateStore.java,247,306
//,3
public class xxx {
  @Override
  public DeleteApplicationHomeSubClusterResponse deleteApplicationHomeSubCluster(
      DeleteApplicationHomeSubClusterRequest request) throws YarnException {

    // Input validator
    FederationApplicationHomeSubClusterStoreInputValidator.validate(request);

    CallableStatement cstmt = null;
    Connection conn = null;

    try {
      conn = getConnection();
      cstmt = conn.prepareCall(CALL_SP_DELETE_APPLICATION_HOME_SUBCLUSTER);

      // Set the parameters for the stored procedure
      cstmt.setString(1, request.getApplicationId().toString());
      cstmt.registerOutParameter(2, java.sql.Types.INTEGER);

      // Execute the query
      long startTime = clock.getTime();
      cstmt.executeUpdate();
      long stopTime = clock.getTime();

      // Check the ROWCOUNT value, if it is equal to 0 it means the call
      // did not delete the application from FederationStateStore
      if (cstmt.getInt(2) == 0) {
        String errMsg =
            "Application " + request.getApplicationId() + " does not exist";
        FederationStateStoreUtils.logAndThrowStoreException(LOG, errMsg);
      }
      // Check the ROWCOUNT value, if it is different from 1 it means the call
      // had a wrong behavior. Maybe the database is not set correctly.
      if (cstmt.getInt(2) != 1) {
        String errMsg = "Wrong behavior during deleting the application "
            + request.getApplicationId();
        FederationStateStoreUtils.logAndThrowStoreException(LOG, errMsg);
      }

      LOG.info("Delete from the StateStore the application: {}",
          request.getApplicationId());
      FederationStateStoreClientMetrics
          .succeededStateStoreCall(stopTime - startTime);

    } catch (SQLException e) {
      FederationStateStoreClientMetrics.failedStateStoreCall();
      FederationStateStoreUtils.logAndThrowRetriableException(LOG,
          "Unable to delete the application " + request.getApplicationId(), e);
    } finally {
      // Return to the pool the CallableStatement and the Connection
      FederationStateStoreUtils.returnToPool(LOG, cstmt, conn);
    }
    return DeleteApplicationHomeSubClusterResponse.newInstance();
  }

};