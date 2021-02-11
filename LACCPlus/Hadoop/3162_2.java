//,temp,SQLFederationStateStore.java,608,669,temp,SQLFederationStateStore.java,247,306
//,3
public class xxx {
  @Override
  public SubClusterDeregisterResponse deregisterSubCluster(
      SubClusterDeregisterRequest subClusterDeregisterRequest)
      throws YarnException {

    // Input validator
    FederationMembershipStateStoreInputValidator
        .validate(subClusterDeregisterRequest);

    CallableStatement cstmt = null;
    Connection conn = null;

    SubClusterId subClusterId = subClusterDeregisterRequest.getSubClusterId();
    SubClusterState state = subClusterDeregisterRequest.getState();

    try {
      conn = getConnection();
      cstmt = conn.prepareCall(CALL_SP_DEREGISTER_SUBCLUSTER);

      // Set the parameters for the stored procedure
      cstmt.setString(1, subClusterId.getId());
      cstmt.setString(2, state.toString());
      cstmt.registerOutParameter(3, java.sql.Types.INTEGER);

      // Execute the query
      long startTime = clock.getTime();
      cstmt.executeUpdate();
      long stopTime = clock.getTime();

      // Check the ROWCOUNT value, if it is equal to 0 it means the call
      // did not deregister the subcluster into FederationStateStore
      if (cstmt.getInt(3) == 0) {
        String errMsg = "SubCluster " + subClusterId + " not found";
        FederationStateStoreUtils.logAndThrowStoreException(LOG, errMsg);
      }
      // Check the ROWCOUNT value, if it is different from 1 it means the call
      // had a wrong behavior. Maybe the database is not set correctly.
      if (cstmt.getInt(3) != 1) {
        String errMsg = "Wrong behavior during deregistration of SubCluster "
            + subClusterId + " from the StateStore";
        FederationStateStoreUtils.logAndThrowStoreException(LOG, errMsg);
      }

      LOG.info("Deregistered the SubCluster " + subClusterId + " state to "
          + state.toString());
      FederationStateStoreClientMetrics
          .succeededStateStoreCall(stopTime - startTime);

    } catch (SQLException e) {
      FederationStateStoreClientMetrics.failedStateStoreCall();
      FederationStateStoreUtils.logAndThrowRetriableException(LOG,
          "Unable to deregister the sub-cluster " + subClusterId + " state to "
              + state.toString(),
          e);
    } finally {
      // Return to the pool the CallableStatement and the Connection
      FederationStateStoreUtils.returnToPool(LOG, cstmt, conn);
    }
    return SubClusterDeregisterResponse.newInstance();
  }

};