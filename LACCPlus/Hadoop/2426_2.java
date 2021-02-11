//,temp,SQLFederationStateStore.java,771,823,temp,SQLFederationStateStore.java,178,245
//,3
public class xxx {
  @Override
  public SubClusterRegisterResponse registerSubCluster(
      SubClusterRegisterRequest registerSubClusterRequest)
      throws YarnException {

    // Input validator
    FederationMembershipStateStoreInputValidator
        .validate(registerSubClusterRequest);

    CallableStatement cstmt = null;
    Connection conn = null;

    SubClusterInfo subClusterInfo =
        registerSubClusterRequest.getSubClusterInfo();
    SubClusterId subClusterId = subClusterInfo.getSubClusterId();

    try {
      conn = getConnection();
      cstmt = conn.prepareCall(CALL_SP_REGISTER_SUBCLUSTER);

      // Set the parameters for the stored procedure
      cstmt.setString(1, subClusterId.getId());
      cstmt.setString(2, subClusterInfo.getAMRMServiceAddress());
      cstmt.setString(3, subClusterInfo.getClientRMServiceAddress());
      cstmt.setString(4, subClusterInfo.getRMAdminServiceAddress());
      cstmt.setString(5, subClusterInfo.getRMWebServiceAddress());
      cstmt.setString(6, subClusterInfo.getState().toString());
      cstmt.setLong(7, subClusterInfo.getLastStartTime());
      cstmt.setString(8, subClusterInfo.getCapability());
      cstmt.registerOutParameter(9, java.sql.Types.INTEGER);

      // Execute the query
      long startTime = clock.getTime();
      cstmt.executeUpdate();
      long stopTime = clock.getTime();

      // Check the ROWCOUNT value, if it is equal to 0 it means the call
      // did not add a new subcluster into FederationStateStore
      if (cstmt.getInt(9) == 0) {
        String errMsg = "SubCluster " + subClusterId
            + " was not registered into the StateStore";
        FederationStateStoreUtils.logAndThrowStoreException(LOG, errMsg);
      }
      // Check the ROWCOUNT value, if it is different from 1 it means the call
      // had a wrong behavior. Maybe the database is not set correctly.
      if (cstmt.getInt(9) != 1) {
        String errMsg = "Wrong behavior during registration of SubCluster "
            + subClusterId + " into the StateStore";
        FederationStateStoreUtils.logAndThrowStoreException(LOG, errMsg);
      }

      LOG.info(
          "Registered the SubCluster " + subClusterId + " into the StateStore");
      FederationStateStoreClientMetrics
          .succeededStateStoreCall(stopTime - startTime);

    } catch (SQLException e) {
      FederationStateStoreClientMetrics.failedStateStoreCall();
      FederationStateStoreUtils.logAndThrowRetriableException(LOG,
          "Unable to register the SubCluster " + subClusterId
              + " into the StateStore",
          e);
    } finally {
      // Return to the pool the CallableStatement and the Connection
      FederationStateStoreUtils.returnToPool(LOG, cstmt, conn);
    }
    return SubClusterRegisterResponse.newInstance();
  }

};