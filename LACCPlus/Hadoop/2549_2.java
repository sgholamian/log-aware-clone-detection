//,temp,FederationApplicationHomeSubClusterStoreInputValidator.java,144,162,temp,FederationApplicationHomeSubClusterStoreInputValidator.java,122,134
//,3
public class xxx {
  public static void validate(DeleteApplicationHomeSubClusterRequest request)
      throws FederationStateStoreInvalidInputException {
    if (request == null) {
      String message = "Missing DeleteApplicationHomeSubCluster Request."
          + " Please try again by specifying"
          + " an ApplicationHomeSubCluster information.";
      LOG.warn(message);
      throw new FederationStateStoreInvalidInputException(message);
    }

    // validate application Id
    checkApplicationId(request.getApplicationId());
  }

};