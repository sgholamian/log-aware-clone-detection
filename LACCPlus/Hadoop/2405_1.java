//,temp,TestFederationStateStoreInputValidator.java,785,839,temp,TestFederationStateStoreInputValidator.java,588,669
//,3
public class xxx {
  @Test
  public void testGetSubClusterInfoRequest() {

    // Execution with valid inputs

    try {
      GetSubClusterInfoRequest request =
          GetSubClusterInfoRequest.newInstance(subClusterId);
      FederationMembershipStateStoreInputValidator
          .validate(request);
    } catch (FederationStateStoreInvalidInputException e) {
      Assert.fail(e.getMessage());
    }

    // Execution with null request

    try {
      GetSubClusterInfoRequest request = null;
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(
          e.getMessage().startsWith("Missing GetSubClusterInfo Request."));
    }

    // Execution with null SubClusterId

    try {
      GetSubClusterInfoRequest request =
          GetSubClusterInfoRequest.newInstance(subClusterIdNull);
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
      GetSubClusterInfoRequest request =
          GetSubClusterInfoRequest.newInstance(subClusterIdInvalid);
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(
          e.getMessage().startsWith("Invalid SubCluster Id information."));
    }
  }

};