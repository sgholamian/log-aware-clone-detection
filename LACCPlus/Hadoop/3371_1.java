//,temp,TestFederationStateStoreInputValidator.java,450,586,temp,TestFederationStateStoreInputValidator.java,306,448
//,2
public class xxx {
  @Test
  public void testValidateSubClusterRegisterRequestAddressInvalid() {

    // Address is not in host:port format for amRMService

    SubClusterInfo subClusterInfo =
        SubClusterInfo.newInstance(subClusterId, addressWrong,
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
      Assert.assertTrue(e.getMessage().contains("valid host:port authority:"));
    }

    // Address is not in host:port format for clientRMService

    subClusterInfo =
        SubClusterInfo.newInstance(subClusterId, amRMServiceAddress,
            addressWrong, rmAdminServiceAddress, rmWebServiceAddress,
            lastHeartBeat, stateNull, lastStartTime, capability);
    try {
      SubClusterRegisterRequest request =
          SubClusterRegisterRequest.newInstance(subClusterInfo);
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(e.getMessage().contains("valid host:port authority:"));
    }

    // Address is not in host:port format for rmAdminService

    subClusterInfo =
        SubClusterInfo.newInstance(subClusterId, amRMServiceAddress,
            clientRMServiceAddress, addressWrong, rmWebServiceAddress,
            lastHeartBeat, stateNull, lastStartTime, capability);
    try {
      SubClusterRegisterRequest request =
          SubClusterRegisterRequest.newInstance(subClusterInfo);
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(e.getMessage().contains("valid host:port authority:"));
    }

    // Address is not in host:port format for rmWebService

    subClusterInfo = SubClusterInfo.newInstance(subClusterId,
        amRMServiceAddress, clientRMServiceAddress, rmAdminServiceAddress,
        addressWrong, lastHeartBeat, stateNull, lastStartTime, capability);
    try {
      SubClusterRegisterRequest request =
          SubClusterRegisterRequest.newInstance(subClusterInfo);
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(e.getMessage().contains("valid host:port authority:"));
    }

    // Port is not an integer for amRMService

    subClusterInfo = SubClusterInfo.newInstance(subClusterId, addressWrongPort,
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
      Assert.assertTrue(e.getMessage().contains("valid host:port authority:"));
    }

    // Port is not an integer for clientRMService

    subClusterInfo =
        SubClusterInfo.newInstance(subClusterId, amRMServiceAddress,
            addressWrongPort, rmAdminServiceAddress, rmWebServiceAddress,
            lastHeartBeat, stateNull, lastStartTime, capability);
    try {
      SubClusterRegisterRequest request =
          SubClusterRegisterRequest.newInstance(subClusterInfo);
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(e.getMessage().contains("valid host:port authority:"));
    }

    // Port is not an integer for rmAdminService

    subClusterInfo =
        SubClusterInfo.newInstance(subClusterId, amRMServiceAddress,
            clientRMServiceAddress, addressWrongPort, rmWebServiceAddress,
            lastHeartBeat, stateNull, lastStartTime, capability);
    try {
      SubClusterRegisterRequest request =
          SubClusterRegisterRequest.newInstance(subClusterInfo);
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(e.getMessage().contains("valid host:port authority:"));
    }

    // Port is not an integer for rmWebService

    subClusterInfo = SubClusterInfo.newInstance(subClusterId,
        amRMServiceAddress, clientRMServiceAddress, rmAdminServiceAddress,
        addressWrongPort, lastHeartBeat, stateNull, lastStartTime, capability);
    try {
      SubClusterRegisterRequest request =
          SubClusterRegisterRequest.newInstance(subClusterInfo);
      FederationMembershipStateStoreInputValidator
          .validate(request);
      Assert.fail();
    } catch (FederationStateStoreInvalidInputException e) {
      LOG.info(e.getMessage());
      Assert.assertTrue(e.getMessage().contains("valid host:port authority:"));
    }

  }

};