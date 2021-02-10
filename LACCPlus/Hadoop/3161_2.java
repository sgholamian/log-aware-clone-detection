//,temp,SQLFederationStateStore.java,771,823,temp,SQLFederationStateStore.java,521,606
//,3
public class xxx {
  @Override
  public AddApplicationHomeSubClusterResponse addApplicationHomeSubCluster(
      AddApplicationHomeSubClusterRequest request) throws YarnException {

    // Input validator
    FederationApplicationHomeSubClusterStoreInputValidator.validate(request);

    CallableStatement cstmt = null;
    Connection conn = null;

    String subClusterHome = null;
    ApplicationId appId =
        request.getApplicationHomeSubCluster().getApplicationId();
    SubClusterId subClusterId =
        request.getApplicationHomeSubCluster().getHomeSubCluster();

    try {
      conn = getConnection();
      cstmt = conn.prepareCall(CALL_SP_ADD_APPLICATION_HOME_SUBCLUSTER);

      // Set the parameters for the stored procedure
      cstmt.setString(1, appId.toString());
      cstmt.setString(2, subClusterId.getId());
      cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
      cstmt.registerOutParameter(4, java.sql.Types.INTEGER);

      // Execute the query
      long startTime = clock.getTime();
      cstmt.executeUpdate();
      long stopTime = clock.getTime();

      subClusterHome = cstmt.getString(3);
      SubClusterId subClusterIdHome = SubClusterId.newInstance(subClusterHome);

      FederationStateStoreClientMetrics
          .succeededStateStoreCall(stopTime - startTime);

      // For failover reason, we check the returned SubClusterId.
      // If it is equal to the subclusterId we sent, the call added the new
      // application into FederationStateStore. If the call returns a different
      // SubClusterId it means we already tried to insert this application but a
      // component (Router/StateStore/RM) failed during the submission.
      if (subClusterId.equals(subClusterIdHome)) {
        // Check the ROWCOUNT value, if it is equal to 0 it means the call
        // did not add a new application into FederationStateStore
        if (cstmt.getInt(4) == 0) {
          LOG.info(
              "The application {} was not inserted in the StateStore because it"
                  + " was already present in SubCluster {}",
              appId, subClusterHome);
        } else if (cstmt.getInt(4) != 1) {
          // Check the ROWCOUNT value, if it is different from 1 it means the
          // call had a wrong behavior. Maybe the database is not set correctly.
          String errMsg = "Wrong behavior during the insertion of SubCluster "
              + subClusterId;
          FederationStateStoreUtils.logAndThrowStoreException(LOG, errMsg);
        }

        LOG.info("Insert into the StateStore the application: " + appId
            + " in SubCluster:  " + subClusterHome);
      } else {
        // Check the ROWCOUNT value, if it is different from 0 it means the call
        // did edited the table
        if (cstmt.getInt(4) != 0) {
          String errMsg =
              "The application " + appId + " does exist but was overwritten";
          FederationStateStoreUtils.logAndThrowStoreException(LOG, errMsg);
        }
        LOG.info("Application: " + appId + " already present with SubCluster:  "
            + subClusterHome);
      }

    } catch (SQLException e) {
      FederationStateStoreClientMetrics.failedStateStoreCall();
      FederationStateStoreUtils
          .logAndThrowRetriableException(LOG,
              "Unable to insert the newly generated application "
                  + request.getApplicationHomeSubCluster().getApplicationId(),
              e);
    } finally {
      // Return to the pool the CallableStatement and the Connection
      FederationStateStoreUtils.returnToPool(LOG, cstmt, conn);
    }
    return AddApplicationHomeSubClusterResponse
        .newInstance(SubClusterId.newInstance(subClusterHome));
  }

};