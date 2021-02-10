//,temp,DFSClient.java,574,598,temp,InMemorySCMStore.java,483,518
//,3
public class xxx {
  public void closeAllFilesBeingWritten(final boolean abort) {
    for(;;) {
      final long inodeId;
      final DFSOutputStream out;
      synchronized(filesBeingWritten) {
        if (filesBeingWritten.isEmpty()) {
          return;
        }
        inodeId = filesBeingWritten.keySet().iterator().next();
        out = filesBeingWritten.remove(inodeId);
      }
      if (out != null) {
        try {
          if (abort) {
            out.abort();
          } else {
            out.close();
          }
        } catch(IOException ie) {
          LOG.error("Failed to " + (abort? "abort": "close") +
                  " inode " + inodeId, ie);
        }
      }
    }
  }

};