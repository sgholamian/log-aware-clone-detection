//,temp,ZKFailoverController.java,260,277,temp,ZKFailoverController_before_log_fix.java,259,276
//,2
public class xxx {
  private int formatZK(boolean force, boolean interactive)
      throws IOException, InterruptedException {
    if (elector.parentZNodeExists()) {
      if (!force && (!interactive || !confirmFormat())) {
        return ERR_CODE_FORMAT_DENIED;
      }
      
      try {
        elector.clearParentZNode();
      } catch (IOException e) {
        LOG.error("Unable to clear zk parent znode", e);
        return 1;
      }
    }
    
    elector.ensureParentZNode();
    return 0;
  }

};