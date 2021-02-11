//,temp,FederationPolicyStoreInputValidator.java,133,141,temp,FederationMembershipStateStoreInputValidator.java,223,231
//,2
public class xxx {
  private static void checkCapability(String capability)
      throws FederationStateStoreInvalidInputException {
    if (capability == null || capability.isEmpty()) {
      String message = "Invalid capability information."
          + " Please try again by specifying valid Capability Information.";
      LOG.warn(message);
      throw new FederationStateStoreInvalidInputException(message);
    }
  }

};