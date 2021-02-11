//,temp,SecondaryNameNode.java,947,998,temp,BackupImage.java,116,148
//,3
public class xxx {
    void recoverCreate(boolean format) throws IOException {
      storage.attemptRestoreRemovedStorage();
      storage.unlockAll();

      for (Iterator<StorageDirectory> it = 
                   storage.dirIterator(); it.hasNext();) {
        StorageDirectory sd = it.next();
        boolean isAccessible = true;
        try { // create directories if don't exist yet
          if(!sd.getRoot().mkdirs()) {
            // do nothing, directory is already created
          }
        } catch(SecurityException se) {
          isAccessible = false;
        }
        if(!isAccessible)
          throw new InconsistentFSStateException(sd.getRoot(),
              "cannot access checkpoint directory.");
        
        if (format) {
          // Don't confirm, since this is just the secondary namenode.
          LOG.info("Formatting storage directory " + sd);
          sd.clearDirectory();
        }
        
        StorageState curState;
        try {
          curState = sd.analyzeStorage(HdfsServerConstants.StartupOption.REGULAR, storage);
          // sd is locked but not opened
          switch(curState) {
          case NON_EXISTENT:
            // fail if any of the configured checkpoint dirs are inaccessible 
            throw new InconsistentFSStateException(sd.getRoot(),
                  "checkpoint directory does not exist or is not accessible.");
          case NOT_FORMATTED:
            break;  // it's ok since initially there is no current and VERSION
          case NORMAL:
            // Read the VERSION file. This verifies that:
            // (a) the VERSION file for each of the directories is the same,
            // and (b) when we connect to a NN, we can verify that the remote
            // node matches the same namespace that we ran on previously.
            storage.readProperties(sd);
            break;
          default:  // recovery is possible
            sd.doRecover(curState);
          }
        } catch (IOException ioe) {
          sd.unlock();
          throw ioe;
        }
      }
    }

};