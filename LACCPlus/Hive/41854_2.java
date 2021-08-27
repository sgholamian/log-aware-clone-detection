//,temp,LlapTaskSchedulerService.java,1033,1059,temp,LlapTaskSchedulerService.java,1004,1026
//,3
public class xxx {
  @Override
  public Resource getTotalResources() {
    int memory = 0;
    int vcores = 0;
    readLock.lock();
    try {
      int numInstancesFound = 0;
      for (LlapServiceInstance inst : activeInstances.getAll()) {
        Resource r = inst.getResource();
        memory += r.getMemory();
        vcores += r.getVirtualCores();
        numInstancesFound++;
      }
      if (LOG.isDebugEnabled()) {
        LOG.debug("GetTotalResources: numInstancesFound={}, totalMem={}, totalVcores={}",
            numInstancesFound, memory, vcores);
      }
    } finally {
      readLock.unlock();
    }

    return Resource.newInstance(memory, vcores);
  }

};