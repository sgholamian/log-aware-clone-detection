//,temp,RMContainerTokenSecretManager.java,60,87,temp,NMTokenSecretManagerInRM.java,57,86
//,3
public class xxx {
  public NMTokenSecretManagerInRM(Configuration conf) {
    this.conf = conf;
    timer = new Timer();
    rollingInterval = this.conf.getLong(
        YarnConfiguration.RM_NMTOKEN_MASTER_KEY_ROLLING_INTERVAL_SECS,
        YarnConfiguration.DEFAULT_RM_NMTOKEN_MASTER_KEY_ROLLING_INTERVAL_SECS)
        * 1000;
    // Add an activation delay. This is to address the following race: RM may
    // roll over master-key, scheduling may happen at some point of time, an
    // NMToken created with a password generated off new master key, but NM
    // might not have come again to RM to update the shared secret: so AM has a
    // valid password generated off new secret but NM doesn't know about the
    // secret yet.
    // Adding delay = 1.5 * expiry interval makes sure that all active NMs get
    // the updated shared-key.
    this.activationDelay =
        (long) (conf.getLong(YarnConfiguration.RM_NM_EXPIRY_INTERVAL_MS,
            YarnConfiguration.DEFAULT_RM_NM_EXPIRY_INTERVAL_MS) * 1.5);
    LOG.info("NMTokenKeyRollingInterval: " + this.rollingInterval
        + "ms and NMTokenKeyActivationDelay: " + this.activationDelay
        + "ms");
    if (rollingInterval <= activationDelay * 2) {
      throw new IllegalArgumentException(
          YarnConfiguration.RM_NMTOKEN_MASTER_KEY_ROLLING_INTERVAL_SECS
              + " should be more than 3 X "
              + YarnConfiguration.RM_NM_EXPIRY_INTERVAL_MS);
    }
    appAttemptToNodeKeyMap =
        new ConcurrentHashMap<ApplicationAttemptId, HashSet<NodeId>>();
  }

};