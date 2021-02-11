//,temp,FederationMembershipStateStoreInputValidator.java,206,214,temp,FederationApplicationHomeSubClusterStoreInputValidator.java,171,179
//,3
public class xxx {
  private static void checkTimestamp(long timestamp)
      throws FederationStateStoreInvalidInputException {
    if (timestamp < 0) {
      String message = "Invalid timestamp information."
          + " Please try again by specifying valid Timestamp Information.";
      LOG.warn(message);
      throw new FederationStateStoreInvalidInputException(message);
    }
  }

};