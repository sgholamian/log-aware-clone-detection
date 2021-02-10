//,temp,EventFetcher.java,98,106,temp,AllocationFileLoaderService.java,167,179
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