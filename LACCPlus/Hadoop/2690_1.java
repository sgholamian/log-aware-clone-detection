//,temp,HddsDatanodeService.java,189,196,temp,ApplicationMasterLauncher.java,97,106
//,3
public class xxx {
  public void join() {
    try {
      datanodeStateMachine.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOG.info("Interrupted during StorageContainerManager join.");
    }
  }

};