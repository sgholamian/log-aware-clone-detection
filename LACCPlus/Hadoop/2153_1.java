//,temp,ZKFailoverController.java,380,415,temp,ZKFailoverController_before_log_fix.java,379,414
//,2
public class xxx {
  private synchronized void becomeActive() throws ServiceFailedException {
    LOG.info("Trying to make " + localTarget + " active...");
    try {
      HAServiceProtocolHelper.transitionToActive(localTarget.getProxy(
          conf, FailoverController.getRpcTimeoutToNewActive(conf)),
          createReqInfo());
      String msg = "Successfully transitioned " + localTarget +
          " to active state";
      LOG.info(msg);
      serviceState = HAServiceState.ACTIVE;
      recordActiveAttempt(new ActiveAttemptRecord(true, msg));

    } catch (Throwable t) {
      String msg = "Couldn't make " + localTarget + " active";
      LOG.fatal(msg, t);
      
      recordActiveAttempt(new ActiveAttemptRecord(false, msg + "\n" +
          StringUtils.stringifyException(t)));

      if (t instanceof ServiceFailedException) {
        throw (ServiceFailedException)t;
      } else {
        throw new ServiceFailedException("Couldn't transition to active",
            t);
      }
/*
* TODO:
* we need to make sure that if we get fenced and then quickly restarted,
* none of these calls will retry across the restart boundary
* perhaps the solution is that, whenever the nn starts, it gets a unique
* ID, and when we start becoming active, we record it, and then any future
* calls use the same ID
*/
      
    }
  }

};