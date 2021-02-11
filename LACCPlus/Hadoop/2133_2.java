//,temp,ZKFailoverController.java,484,499,temp,ZKFailoverController_before_log_fix.java,483,498
//,2
public class xxx {
  private synchronized void becomeStandby() {
    LOG.info("ZK Election indicated that " + localTarget +
        " should become standby");
    try {
      int timeout = FailoverController.getGracefulFenceTimeout(conf);
      localTarget.getProxy(conf, timeout).transitionToStandby(createReqInfo());
      LOG.info("Successfully transitioned " + localTarget +
          " to standby state");
    } catch (Exception e) {
      LOG.error("Couldn't transition " + localTarget + " to standby state",
          e);
      // TODO handle this. It's a likely case since we probably got fenced
      // at the same time.
    }
    serviceState = HAServiceState.STANDBY;
  }

};