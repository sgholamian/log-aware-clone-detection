//,temp,TestFederationStateStoreInputValidator.java,935,1027,temp,TestFederationStateStoreInputValidator.java,841,933
//,2
public class xxx {
  @Test
  public void testUpdateApplicationHomeSubClusterRequest() {

    // Execution with valid inputs

    ApplicationHomeSubCluster applicationHomeSubCluster =
        ApplicationHomeSubCluster.newInstance(appId, subClusterId);
    try {
      UpdateApplicationHomeSubClusterRequest request =
          UpdateApplicationHomeSubClusterRequest
              .newInstance(applicationHomeSubCluster);
      FederationApplicationHomeSubClusterStoreInputValidator
          .validate(request);
    } catch (FederationStateStoreInvalidInputException e) {
      Assert.fail(e.getMessage());
    }

    // Execution with null request

    try {
      UpdateApplicationHomeSubClusterRequest request = null;
      FederationApplicationHomeSubClusterStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      Assert.assertTrue(e.getMessage()
          .startsWith("Missing UpdateApplicationHomeSubCluster Request."));
    }

    // Execution with null ApplicationHomeSubCluster

    applicationHomeSubCluster = null;
    try {
      UpdateApplicationHomeSubClusterRequest request =
          UpdateApplicationHomeSubClusterRequest
              .newInstance(applicationHomeSubCluster);
      FederationApplicationHomeSubClusterStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      Assert.assertTrue(
          e.getMessage().startsWith("Missing ApplicationHomeSubCluster Info."));
    }

    // Execution with null SubClusteId

    applicationHomeSubCluster =
        ApplicationHomeSubCluster.newInstance(appId, subClusterIdNull);
    try {
      UpdateApplicationHomeSubClusterRequest request =
          UpdateApplicationHomeSubClusterRequest
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
      UpdateApplicationHomeSubClusterRequest request =
          UpdateApplicationHomeSubClusterRequest
              .newInstance(applicationHomeSubCluster);
      FederationApplicationHomeSubClusterStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(
          e.getMessage().startsWith("Invalid SubCluster Id information."));
    }

    // Execution with null ApplicationId

    applicationHomeSubCluster =
        ApplicationHomeSubCluster.newInstance(appIdNull, subClusterId);
    try {
      UpdateApplicationHomeSubClusterRequest request =
          UpdateApplicationHomeSubClusterRequest
              .newInstance(applicationHomeSubCluster);
      FederationApplicationHomeSubClusterStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      Assert.assertTrue(e.getMessage().startsWith("Missing Application Id."));
    }
  }

};