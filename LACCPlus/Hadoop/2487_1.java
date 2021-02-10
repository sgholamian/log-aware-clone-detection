//,temp,TestFederationStateStoreInputValidator.java,671,783,temp,TestFederationStateStoreInputValidator.java,588,669
//,3
public class xxx {
  @Test
  public void testSubClusterHeartbeatRequest() {

    // Execution with valid inputs

    try {
      SubClusterHeartbeatRequest request = SubClusterHeartbeatRequest
          .newInstance(subClusterId, lastHeartBeat, stateLost, capability);
      FederationMembershipStateStoreInputValidator
          .validate(request);
    } catch (FederationStateStoreInvalidInputException e) {
      Assert.fail(e.getMessage());
    }

    // Execution with null request

    try {
      SubClusterHeartbeatRequest request = null;
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(
          e.getMessage().startsWith("Missing SubClusterHeartbeat Request."));
    }

    // Execution with null SubClusterId

    try {
      SubClusterHeartbeatRequest request = SubClusterHeartbeatRequest
          .newInstance(subClusterIdNull, lastHeartBeat, stateLost, capability);
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(
          e.getMessage().startsWith("Missing SubCluster Id information."));
    }

    // Execution with invalid SubClusterId

    try {
      SubClusterHeartbeatRequest request =
          SubClusterHeartbeatRequest.newInstance(subClusterIdInvalid,
              lastHeartBeat, stateLost, capability);
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(
          e.getMessage().startsWith("Invalid SubCluster Id information."));
    }

    // Execution with null SubClusterState

    try {
      SubClusterHeartbeatRequest request = SubClusterHeartbeatRequest
          .newInstance(subClusterId, lastHeartBeat, stateNull, capability);
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(
          e.getMessage().startsWith("Missing SubCluster State information."));
    }

    // Execution with negative Last Heartbeat

    try {
      SubClusterHeartbeatRequest request =
          SubClusterHeartbeatRequest.newInstance(subClusterId,
              lastHeartBeatNegative, stateLost, capability);
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(
          e.getMessage().startsWith("Invalid timestamp information."));
    }

    // Execution with null Capability

    try {
      SubClusterHeartbeatRequest request = SubClusterHeartbeatRequest
          .newInstance(subClusterId, lastHeartBeat, stateLost, capabilityNull);
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(
          e.getMessage().startsWith("Invalid capability information."));
    }

    // Execution with empty Capability

    try {
      SubClusterHeartbeatRequest request = SubClusterHeartbeatRequest
          .newInstance(subClusterId, lastHeartBeat, stateLost, capabilityEmpty);
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(
          e.getMessage().startsWith("Invalid capability information."));
    }
  }

};