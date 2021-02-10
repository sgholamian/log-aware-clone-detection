//,temp,TestFederationStateStoreInputValidator.java,450,586,temp,TestFederationStateStoreInputValidator.java,306,448
//,2
public class xxx {
  @Test
  public void testValidateSubClusterRegisterRequestAddress() {
    // Execution with Null Address for amRMServiceAddress

    SubClusterInfo subClusterInfo =
        SubClusterInfo.newInstance(subClusterId, addressNull,
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
      Assert.assertTrue(e.getMessage()
          .startsWith("Missing SubCluster Endpoint information."));
    }

    // Execution with Empty Address for amRMServiceAddress

    subClusterInfo = SubClusterInfo.newInstance(subClusterId, addressEmpty,
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
      Assert.assertTrue(e.getMessage()
          .startsWith("Missing SubCluster Endpoint information."));
    }

    // Execution with Null Address for clientRMServiceAddress

    subClusterInfo =
        SubClusterInfo.newInstance(subClusterId, amRMServiceAddress,
            addressNull, rmAdminServiceAddress, rmWebServiceAddress,
            lastHeartBeat, stateNew, lastStartTime, capability);
    try {
      SubClusterRegisterRequest request =
          SubClusterRegisterRequest.newInstance(subClusterInfo);
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(e.getMessage()
          .startsWith("Missing SubCluster Endpoint information."));
    }

    // Execution with Empty Address for clientRMServiceAddress

    subClusterInfo =
        SubClusterInfo.newInstance(subClusterId, amRMServiceAddress,
            addressEmpty, rmAdminServiceAddress, rmWebServiceAddress,
            lastHeartBeat, stateNew, lastStartTime, capability);
    try {
      SubClusterRegisterRequest request =
          SubClusterRegisterRequest.newInstance(subClusterInfo);
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(e.getMessage()
          .startsWith("Missing SubCluster Endpoint information."));
    }

    // Execution with Null Address for rmAdminServiceAddress

    subClusterInfo =
        SubClusterInfo.newInstance(subClusterId, amRMServiceAddress,
            clientRMServiceAddress, addressNull, rmWebServiceAddress,
            lastHeartBeat, stateNew, lastStartTime, capability);
    try {
      SubClusterRegisterRequest request =
          SubClusterRegisterRequest.newInstance(subClusterInfo);
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(e.getMessage()
          .startsWith("Missing SubCluster Endpoint information."));
    }

    // Execution with Empty Address for rmAdminServiceAddress

    subClusterInfo =
        SubClusterInfo.newInstance(subClusterId, amRMServiceAddress,
            clientRMServiceAddress, addressEmpty, rmWebServiceAddress,
            lastHeartBeat, stateNew, lastStartTime, capability);
    try {
      SubClusterRegisterRequest request =
          SubClusterRegisterRequest.newInstance(subClusterInfo);
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(e.getMessage()
          .startsWith("Missing SubCluster Endpoint information."));
    }

    // Execution with Null Address for rmWebServiceAddress

    subClusterInfo = SubClusterInfo.newInstance(subClusterId,
        amRMServiceAddress, clientRMServiceAddress, rmAdminServiceAddress,
        addressNull, lastHeartBeat, stateNew, lastStartTime, capability);
    try {
      SubClusterRegisterRequest request =
          SubClusterRegisterRequest.newInstance(subClusterInfo);
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(e.getMessage()
          .startsWith("Missing SubCluster Endpoint information."));
    }

    // Execution with Empty Address for rmWebServiceAddress

    subClusterInfo = SubClusterInfo.newInstance(subClusterId,
        amRMServiceAddress, clientRMServiceAddress, rmAdminServiceAddress,
        addressEmpty, lastHeartBeat, stateNew, lastStartTime, capability);
    try {
      SubClusterRegisterRequest request =
          SubClusterRegisterRequest.newInstance(subClusterInfo);
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(e.getMessage()
          .startsWith("Missing SubCluster Endpoint information."));
    }
  }

};