//,temp,ZKFailoverController.java,513,536,temp,ZKFailoverController_before_log_fix.java,512,535
//,2
public class xxx {
  private void doFence(HAServiceTarget target) {
    LOG.info("Should fence: " + target);
    boolean gracefulWorked = new FailoverController(conf,
        RequestSource.REQUEST_BY_ZKFC).tryGracefulFence(target);
    if (gracefulWorked) {
      // It's possible that it's in standby but just about to go into active,
      // no? Is there some race here?
      LOG.info("Successfully transitioned " + target + " to standby " +
          "state without fencing");
      return;
    }
    
    try {
      target.checkFencingConfigured();
    } catch (BadFencingConfigurationException e) {
      LOG.error("Couldn't fence old active " + target, e);
      recordActiveAttempt(new ActiveAttemptRecord(false, "Unable to fence old active"));
      throw new RuntimeException(e);
    }
    
    if (!target.getFencer().fence(target)) {
      throw new RuntimeException("Unable to fence " + target);
    }
  }

};