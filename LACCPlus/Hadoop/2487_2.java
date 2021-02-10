//,temp,TestFederationStateStoreInputValidator.java,671,783,temp,TestFederationStateStoreInputValidator.java,588,669
//,3
public class xxx {
  @Test
  public void testValidateSubClusterDeregisterRequest() {

    // Execution with valid inputs

    try {
      SubClusterDeregisterRequest request =
          SubClusterDeregisterRequest.newInstance(subClusterId, stateLost);
      FederationMembershipStateStoreInputValidator
          .validate(request);
    } catch (FederationStateStoreInvalidInputException e) {
      Assert.fail(e.getMessage());
    }

    // Execution with null request

    try {
      SubClusterDeregisterRequest request = null;
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(
          e.getMessage().startsWith("Missing SubClusterDeregister Request."));
    }

    // Execution with null SubClusterId

    try {
      SubClusterDeregisterRequest request =
          SubClusterDeregisterRequest.newInstance(subClusterIdNull, stateLost);
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
      SubClusterDeregisterRequest request = SubClusterDeregisterRequest
          .newInstance(subClusterIdInvalid, stateLost);
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
      SubClusterDeregisterRequest request =
          SubClusterDeregisterRequest.newInstance(subClusterId, stateNull);
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(
          e.getMessage().startsWith("Missing SubCluster State information."));
    }

    // Execution with invalid SubClusterState

    try {
      SubClusterDeregisterRequest request =
          SubClusterDeregisterRequest.newInstance(subClusterId, stateNew);
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(e.getMessage().startsWith("Invalid non-final state: "));
    }
  }

};