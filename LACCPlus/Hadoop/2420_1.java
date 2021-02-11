//,temp,SQLFederationStateStore.java,880,938,temp,SQLFederationStateStore.java,771,823
//,3
public class xxx {
  @Override
  public SetSubClusterPolicyConfigurationResponse setPolicyConfiguration(
      SetSubClusterPolicyConfigurationRequest request) throws YarnException {

    // Input validator
    FederationPolicyStoreInputValidator.validate(request);

    CallableStatement cstmt = null;
    Connection conn = null;

    SubClusterPolicyConfiguration policyConf = request.getPolicyConfiguration();

    try {
      conn = getConnection();
      cstmt = conn.prepareCall(CALL_SP_SET_POLICY_CONFIGURATION);

      // Set the parameters for the stored procedure
      cstmt.setString(1, policyConf.getQueue());
      cstmt.setString(2, policyConf.getType());
      cstmt.setBytes(3, getByteArray(policyConf.getParams()));
      cstmt.registerOutParameter(4, java.sql.Types.INTEGER);

      // Execute the query
      long startTime = clock.getTime();
      cstmt.executeUpdate();
      long stopTime = clock.getTime();

      // Check the ROWCOUNT value, if it is equal to 0 it means the call
      // did not add a new policy into FederationStateStore
      if (cstmt.getInt(4) == 0) {
        String errMsg = "The policy " + policyConf.getQueue()
            + " was not insert into the StateStore";
        FederationStateStoreUtils.logAndThrowStoreException(LOG, errMsg);
      }
      // Check the ROWCOUNT value, if it is different from 1 it means the call
      // had a wrong behavior. Maybe the database is not set correctly.
      if (cstmt.getInt(4) != 1) {
        String errMsg =
            "Wrong behavior during insert the policy " + policyConf.getQueue();
        FederationStateStoreUtils.logAndThrowStoreException(LOG, errMsg);
      }

      LOG.info("Insert into the state store the policy for the queue: "
          + policyConf.getQueue());
      FederationStateStoreClientMetrics
          .succeededStateStoreCall(stopTime - startTime);

    } catch (SQLException e) {
      FederationStateStoreClientMetrics.failedStateStoreCall();
      FederationStateStoreUtils.logAndThrowRetriableException(LOG,
          "Unable to insert the newly generated policy for the queue :"
              + policyConf.getQueue(),
          e);
    } finally {
      // Return to the pool the CallableStatement and the Connection
      FederationStateStoreUtils.returnToPool(LOG, cstmt, conn);
    }
    return SetSubClusterPolicyConfigurationResponse.newInstance();
  }

};