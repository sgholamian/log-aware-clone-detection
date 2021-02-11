//,temp,FileSystem.java,1406,1421,temp,InMemorySCMStore.java,483,518
//,3
public class xxx {
  protected void processDeleteOnExit() {
    synchronized (deleteOnExit) {
      for (Iterator<Path> iter = deleteOnExit.iterator(); iter.hasNext();) {
        Path path = iter.next();
        try {
          if (exists(path)) {
            delete(path, true);
          }
        }
        catch (IOException e) {
          LOG.info("Ignoring failure to deleteOnExit for path " + path);
        }
        iter.remove();
      }
    }
  }

};