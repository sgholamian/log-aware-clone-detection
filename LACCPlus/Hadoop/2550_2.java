//,temp,FederationMembershipStateStoreInputValidator.java,206,214,temp,FederationApplicationHomeSubClusterStoreInputValidator.java,171,179
//,3
public class xxx {
  private static void checkApplicationId(ApplicationId appId)
      throws FederationStateStoreInvalidInputException {
    if (appId == null) {
      String message = "Missing Application Id."
          + " Please try again by specifying an Application Id.";
      LOG.warn(message);
      throw new FederationStateStoreInvalidInputException(message);
    }
  }

};