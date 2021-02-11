//,temp,FederationPolicyStoreInputValidator.java,133,141,temp,FederationMembershipStateStoreInputValidator.java,223,231
//,2
public class xxx {
  private static void checkType(String type)
      throws FederationStateStoreInvalidInputException {
    if (type == null || type.isEmpty()) {
      String message = "Missing Policy Type."
          + " Please try again by specifying a Policy Type.";
      LOG.warn(message);
      throw new FederationStateStoreInvalidInputException(message);
    }
  }

};