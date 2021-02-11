//,temp,FederationApplicationHomeSubClusterStoreInputValidator.java,144,162,temp,FederationApplicationHomeSubClusterStoreInputValidator.java,122,134
//,3
public class xxx {
  private static void checkApplicationHomeSubCluster(
      ApplicationHomeSubCluster applicationHomeSubCluster)

      throws FederationStateStoreInvalidInputException {
    if (applicationHomeSubCluster == null) {
      String message = "Missing ApplicationHomeSubCluster Info."
          + " Please try again by specifying"
          + " an ApplicationHomeSubCluster information.";
      LOG.warn(message);
      throw new FederationStateStoreInvalidInputException(message);
    }
    // validate application Id
    checkApplicationId(applicationHomeSubCluster.getApplicationId());

    // validate subcluster Id
    FederationMembershipStateStoreInputValidator
        .checkSubClusterId(applicationHomeSubCluster.getHomeSubCluster());

  }

};