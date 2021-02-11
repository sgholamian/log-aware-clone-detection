//,temp,TestFederationStateStoreInputValidator.java,935,1027,temp,TestFederationStateStoreInputValidator.java,841,933
//,2
public class xxx {
  @Test
  public void testAddApplicationHomeSubClusterRequest() {

    // Execution with valid inputs

    ApplicationHomeSubCluster applicationHomeSubCluster =
        ApplicationHomeSubCluster.newInstance(appId, subClusterId);
    try {
      AddApplicationHomeSubClusterRequest request =
          AddApplicationHomeSubClusterRequest
              .newInstance(applicationHomeSubCluster);
      FederationApplicationHomeSubClusterStoreInputValidator
          .validate(request);
    } catch (FederationStateStoreInvalidInputException e) {
      Assert.fail(e.getMessage());
    }

    // Execution with null request

    try {
      AddApplicationHomeSubClusterRequest request = null;
      FederationApplicationHomeSubClusterStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      Assert.assertTrue(e.getMessage()
          .startsWith("Missing AddApplicationHomeSubCluster Request."));
    }

    // Execution with null ApplicationHomeSubCluster

    applicationHomeSubCluster = null;
    try {
      AddApplicationHomeSubClusterRequest request =
          AddApplicationHomeSubClusterRequest
              .newInstance(applicationHomeSubCluster);
      FederationApplicationHomeSubClusterStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      Assert.assertTrue(
          e.getMessage().startsWith("Missing ApplicationHomeSubCluster Info."));
    }

    // Execution with null SubClusterId

    applicationHomeSubCluster =
        ApplicationHomeSubCluster.newInstance(appId, subClusterIdNull);
    try {
      AddApplicationHomeSubClusterRequest request =
          AddApplicationHomeSubClusterRequest
              .newInstance(applicationHomeSubCluster);
      FederationApplicationHomeSubClusterStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(
          e.getMessage().startsWith("Missing SubCluster Id information."));
    }

    // Execution with invalid SubClusterId

    applicationHomeSubCluster =
        ApplicationHomeSubCluster.newInstance(appId, subClusterIdInvalid);
    try {
      AddApplicationHomeSubClusterRequest request =
          AddApplicationHomeSubClusterRequest
              .newInstance(applicationHomeSubCluster);
      FederationApplicationHomeSubClusterStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(
          e.getMessage().startsWith("Invalid SubCluster Id information."));
    }

    // Execution with Null ApplicationId

    applicationHomeSubCluster =
        ApplicationHomeSubCluster.newInstance(appIdNull, subClusterId);
    try {
      AddApplicationHomeSubClusterRequest request =
          AddApplicationHomeSubClusterRequest
              .newInstance(applicationHomeSubCluster);
      FederationApplicationHomeSubClusterStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      Assert.assertTrue(e.getMessage().startsWith("Missing Application Id."));
    }
  }

};