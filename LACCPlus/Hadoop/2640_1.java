//,temp,SwiftTestUtils.java,302,307,temp,FederationMembershipStateStoreInputValidator.java,304,313
//,3
public class xxx {
  public static void downgrade(String message, Throwable failure) {
    LOG.warn("Downgrading test " + message, failure);
    AssumptionViolatedException ave =
      new AssumptionViolatedException(failure, null);
    throw ave;
  }

};