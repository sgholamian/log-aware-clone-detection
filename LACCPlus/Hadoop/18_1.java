//,temp,ResourceManager.java,1027,1043,temp,ResourceManager.java,1002,1025
//,3
public class xxx {
  synchronized void transitionToStandby(boolean initialize)
      throws Exception {
    if (rmContext.getHAServiceState() ==
        HAServiceProtocol.HAServiceState.STANDBY) {
      LOG.info("Already in standby state");
      return;
    }

    LOG.info("Transitioning to standby state");
    if (rmContext.getHAServiceState() ==
        HAServiceProtocol.HAServiceState.ACTIVE) {
      stopActiveServices();
      reinitialize(initialize);
    }
    rmContext.setHAServiceState(HAServiceProtocol.HAServiceState.STANDBY);
    LOG.info("Transitioned to standby state");
  }

};