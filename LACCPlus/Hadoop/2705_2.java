//,temp,SystemServiceManagerImpl.java,135,148,temp,EventFetcher.java,98,106
//,3
public class xxx {
  public void shutDown() {
    this.stopped = true;
    interrupt();
    try {
      join(5000);
    } catch(InterruptedException ie) {
      LOG.warn("Got interrupted while joining " + getName(), ie);
    }
  }

};