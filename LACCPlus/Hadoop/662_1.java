//,temp,FileSystemRMStateStore.java,730,745,temp,DU.java,109,131
//,3
public class xxx {
    T runWithRetries() throws Exception {
      int retry = 0;
      while (true) {
        try {
          return run();
        } catch (IOException e) {
          LOG.info("Exception while executing a FS operation.", e);
          if (++retry > fsNumRetries) {
            LOG.info("Maxed out FS retries. Giving up!");
            throw e;
          }
          LOG.info("Retrying operation on FS. Retry no. " + retry);
          Thread.sleep(fsRetryInterval);
        }
      }
    }

};