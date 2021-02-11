//,temp,LocalFetcher.java,98,112,temp,FileSystem.java,1705,1720
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
          LOGGER.info("Ignoring failure to deleteOnExit for path {}", path);
        }
        iter.remove();
      }
    }
  }

};