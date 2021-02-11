//,temp,TestFederationStateStoreInputValidator.java,450,586,temp,TestFederationStateStoreInputValidator.java,135,264
//,3
public class xxx {
  @Test
  public void testValidateSubClusterRegisterRequest() {

    // Execution with valid inputs

    SubClusterInfo subClusterInfo =
        SubClusterInfo.newInstance(subClusterId, amRMServiceAddress,
            clientRMServiceAddress, rmAdminServiceAddress, rmWebServiceAddress,
            lastHeartBeat, stateNew, lastStartTime, capability);
    try {
      SubClusterRegisterRequest request =
          SubClusterRegisterRequest.newInstance(subClusterInfo);
      FederationMembershipStateStoreInputValidator
          .validate(request);
    } catch (FederationStateStoreInvalidInputException e) {
      Assert.fail(e.getMessage());
    }

    // Execution with null request

    try {
      SubClusterRegisterRequest request = null;
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(
          e.getMessage().startsWith("Missing SubClusterRegister Request."));
    }

    // Execution with null SubClusterInfo

    subClusterInfo = null;
    try {
      SubClusterRegisterRequest request =
          SubClusterRegisterRequest.newInstance(subClusterInfo);
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(
          e.getMessage().startsWith("Missing SubCluster Information."));
    }

    // Execution with Null SubClusterId

    subClusterInfo =
        SubClusterInfo.newInstance(subClusterIdNull, amRMServiceAddress,
            clientRMServiceAddress, rmAdminServiceAddress, rmWebServiceAddress,
            lastHeartBeat, stateNew, lastStartTime, capability);
    try {
      SubClusterRegisterRequest request =
          SubClusterRegisterRequest.newInstance(subClusterInfo);
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(
          e.getMessage().startsWith("Missing SubCluster Id information."));
    }

    // Execution with Invalid SubClusterId

    subClusterInfo =
        SubClusterInfo.newInstance(subClusterIdInvalid, amRMServiceAddress,
            clientRMServiceAddress, rmAdminServiceAddress, rmWebServiceAddress,
            lastHeartBeat, stateNew, lastStartTime, capability);
    try {
      SubClusterRegisterRequest request =
          SubClusterRegisterRequest.newInstance(subClusterInfo);
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(
          e.getMessage().startsWith("Invalid SubCluster Id information."));
    }

    // Execution with Null State

    subClusterInfo =
        SubClusterInfo.newInstance(subClusterId, amRMServiceAddress,
            clientRMServiceAddress, rmAdminServiceAddress, rmWebServiceAddress,
            lastHeartBeat, stateNull, lastStartTime, capability);
    try {
      SubClusterRegisterRequest request =
          SubClusterRegisterRequest.newInstance(subClusterInfo);
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(
          e.getMessage().startsWith("Missing SubCluster State information."));
    }

    // Execution with Null Capability

    subClusterInfo =
        SubClusterInfo.newInstance(subClusterId, amRMServiceAddress,
            clientRMServiceAddress, rmAdminServiceAddress, rmWebServiceAddress,
            lastHeartBeat, stateNew, lastStartTime, capabilityNull);
    try {
      SubClusterRegisterRequest request =
          SubClusterRegisterRequest.newInstance(subClusterInfo);
      FederationMembershipStateStoreInputValidator
          .validate(request);
    } catch (FederationStateStoreInvalidInputException e) {
      Assert.fail(e.getMessage());
    }

    // Execution with Empty Capability

    subClusterInfo =
        SubClusterInfo.newInstance(subClusterId, amRMServiceAddress,
            clientRMServiceAddress, rmAdminServiceAddress, rmWebServiceAddress,
            lastHeartBeat, stateNew, lastStartTime, capabilityEmpty);
    try {
      SubClusterRegisterRequest request =
          SubClusterRegisterRequest.newInstance(subClusterInfo);
      FederationMembershipStateStoreInputValidator
          .validate(request);
    } catch (FederationStateStoreInvalidInputException e) {
      Assert.fail(e.getMessage());
    }
  }

};