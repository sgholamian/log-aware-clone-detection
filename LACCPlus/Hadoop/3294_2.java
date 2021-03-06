//,temp,FederationApplicationHomeSubClusterStoreInputValidator.java,77,89,temp,FederationApplicationHomeSubClusterStoreInputValidator.java,54,66
//,2
public class xxx {
  public static void validate(AddApplicationHomeSubClusterRequest request)
      throws FederationStateStoreInvalidInputException {
    if (request == null) {
      String message = "Missing AddApplicationHomeSubCluster Request."
          + " Please try again by specifying"
          + " an AddApplicationHomeSubCluster information.";
      LOG.warn(message);
      throw new FederationStateStoreInvalidInputException(message);
    }

    // validate ApplicationHomeSubCluster info
    checkApplicationHomeSubCluster(request.getApplicationHomeSubCluster());
  }

};