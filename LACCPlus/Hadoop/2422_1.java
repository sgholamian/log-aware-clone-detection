//,temp,SQLFederationStateStore.java,825,878,temp,SQLFederationStateStore.java,671,725
//,3
public class xxx {
  @Override
  public GetSubClusterPolicyConfigurationResponse getPolicyConfiguration(
      GetSubClusterPolicyConfigurationRequest request) throws YarnException {

    // Input validator
    FederationPolicyStoreInputValidator.validate(request);

    CallableStatement cstmt = null;
    Connection conn = null;
    SubClusterPolicyConfiguration subClusterPolicyConfiguration = null;

    try {
      conn = getConnection();
      cstmt = conn.prepareCall(CALL_SP_GET_POLICY_CONFIGURATION);

      // Set the parameters for the stored procedure
      cstmt.setString(1, request.getQueue());
      cstmt.registerOutParameter(2, java.sql.Types.VARCHAR);
      cstmt.registerOutParameter(3, java.sql.Types.VARBINARY);

      // Execute the query
      long startTime = clock.getTime();
      cstmt.executeUpdate();
      long stopTime = clock.getTime();

      // Check if the output it is a valid policy
      if (cstmt.getString(2) != null && cstmt.getBytes(3) != null) {
        subClusterPolicyConfiguration =
            SubClusterPolicyConfiguration.newInstance(request.getQueue(),
                cstmt.getString(2), ByteBuffer.wrap(cstmt.getBytes(3)));
        if (LOG.isDebugEnabled()) {
          LOG.debug("Selected from StateStore the policy for the queue: "
              + subClusterPolicyConfiguration.toString());
        }
      } else {
        LOG.warn("Policy for queue: {} does not exist.", request.getQueue());
        return null;
      }

      FederationStateStoreClientMetrics
          .succeededStateStoreCall(stopTime - startTime);

    } catch (SQLException e) {
      FederationStateStoreClientMetrics.failedStateStoreCall();
      FederationStateStoreUtils.logAndThrowRetriableException(LOG,
          "Unable to select the policy for the queue :" + request.getQueue(),
          e);
    } finally {
      // Return to the pool the CallableStatement and the Connection
      FederationStateStoreUtils.returnToPool(LOG, cstmt, conn);
    }
    return GetSubClusterPolicyConfigurationResponse
        .newInstance(subClusterPolicyConfiguration);
  }

};