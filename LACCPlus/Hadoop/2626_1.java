//,temp,ActiveStandbyElector.java,724,731,temp,NMTokenSecretManagerInNM.java,249,254
//,3
public class xxx {
  private void monitorActiveStatus() {
    assert wantToBeInElection;
    if (LOG.isDebugEnabled()) {
      LOG.debug("Monitoring active leader for " + this);
    }
    statRetryCount = 0;
    monitorLockNodeAsync();
  }

};