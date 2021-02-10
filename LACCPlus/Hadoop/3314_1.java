//,temp,TestContainerLaunchRPC.java,165,176,temp,TestContainerResourceIncreaseRPC.java,182,193
//,2
public class xxx {
    @Override
    public StartContainersResponse startContainers(
        StartContainersRequest requests) throws YarnException, IOException {
      try {
        // make the thread sleep to look like its not going to respond
        Thread.sleep(10000);
      } catch (Exception e) {
        LOG.error(e);
        throw new YarnException(e);
      }
      throw new YarnException("Shouldn't happen!!");
    }

};