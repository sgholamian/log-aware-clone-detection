//,temp,SQLFederationStateStore.java,308,369,temp,SQLFederationStateStore.java,247,306
//,3
public class xxx {
  @Override
  public SubClusterHeartbeatResponse subClusterHeartbeat(
      SubClusterHeartbeatRequest subClusterHeartbeatRequest)
      throws YarnException {

    // Input validator
    FederationMembershipStateStoreInputValidator
        .validate(subClusterHeartbeatRequest);

    CallableStatement cstmt = null;
    Connection conn = null;

    SubClusterId subClusterId = subClusterHeartbeatRequest.getSubClusterId();
    SubClusterState state = subClusterHeartbeatRequest.getState();

    try {
      conn = getConnection();
      cstmt = conn.prepareCall(CALL_SP_SUBCLUSTER_HEARTBEAT);

      // Set the parameters for the stored procedure
      cstmt.setString(1, subClusterId.getId());
      cstmt.setString(2, state.toString());
      cstmt.setString(3, subClusterHeartbeatRequest.getCapability());
      cstmt.registerOutParameter(4, java.sql.Types.INTEGER);

      // Execute the query
      long startTime = clock.getTime();
      cstmt.executeUpdate();
      long stopTime = clock.getTime();

      // Check the ROWCOUNT value, if it is equal to 0 it means the call
      // did not update the subcluster into FederationStateStore
      if (cstmt.getInt(4) == 0) {
        String errMsg = "SubCluster " + subClusterId.toString()
            + " does not exist; cannot heartbeat";
        FederationStateStoreUtils.logAndThrowStoreException(LOG, errMsg);
      }
      // Check the ROWCOUNT value, if it is different from 1 it means the call
      // had a wrong behavior. Maybe the database is not set correctly.
      if (cstmt.getInt(4) != 1) {
        String errMsg =
            "Wrong behavior during the heartbeat of SubCluster " + subClusterId;
        FederationStateStoreUtils.logAndThrowStoreException(LOG, errMsg);
      }

      LOG.info("Heartbeated the StateStore for the specified SubCluster "
          + subClusterId);
      FederationStateStoreClientMetrics
          .succeededStateStoreCall(stopTime - startTime);

    } catch (SQLException e) {
      FederationStateStoreClientMetrics.failedStateStoreCall();
      FederationStateStoreUtils.logAndThrowRetriableException(LOG,
          "Unable to heartbeat the StateStore for the specified SubCluster "
              + subClusterId,
          e);
    } finally {
      // Return to the pool the CallableStatement and the Connection
      FederationStateStoreUtils.returnToPool(LOG, cstmt, conn);
    }
    return SubClusterHeartbeatResponse.newInstance();
  }

};