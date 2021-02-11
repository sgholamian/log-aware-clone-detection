//,temp,FederationApplicationHomeSubClusterStoreInputValidator.java,77,89,temp,FederationApplicationHomeSubClusterStoreInputValidator.java,54,66
//,2
public class xxx {
  public static void validate(UpdateApplicationHomeSubClusterRequest request)
      throws FederationStateStoreInvalidInputException {
    if (request == null) {
      String message = "Missing UpdateApplicationHomeSubCluster Request."
          + " Please try again by specifying"
          + " an ApplicationHomeSubCluster information.";
      LOG.warn(message);
      throw new FederationStateStoreInvalidInputException(message);
    }

    // validate ApplicationHomeSubCluster info
    checkApplicationHomeSubCluster(request.getApplicationHomeSubCluster());
  }

};