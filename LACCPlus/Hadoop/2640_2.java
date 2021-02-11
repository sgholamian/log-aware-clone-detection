//,temp,SwiftTestUtils.java,302,307,temp,FederationMembershipStateStoreInputValidator.java,304,313
//,3
public class xxx {
  private static void checkSubClusterState(SubClusterState state)
      throws FederationStateStoreInvalidInputException {
    // check sub-cluster state is not empty
    if (state == null) {
      String message = "Missing SubCluster State information."
          + " Please try again by specifying SubCluster State information.";
      LOG.warn(message);
      throw new FederationStateStoreInvalidInputException(message);
    }
  }

};