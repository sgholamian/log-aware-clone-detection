//,temp,CGroupsCpuResourceHandlerImpl.java,205,246,temp,CGroupsMemoryResourceHandlerImpl.java,110,150
//,3
public class xxx {
  @Override
  public List<PrivilegedOperation> updateContainer(Container container)
      throws ResourceHandlerException {
    String cgroupId = container.getContainerId().toString();
    File cgroup = new File(cGroupsHandler.getPathForCGroup(MEMORY, cgroupId));
    if (cgroup.exists()) {
      //memory is in MB
      long containerSoftLimit =
          (long) (container.getResource().getMemorySize() * this.softLimit);
      long containerHardLimit = container.getResource().getMemorySize();
      if (enforce) {
        try {
          cGroupsHandler.updateCGroupParam(MEMORY, cgroupId,
              CGroupsHandler.CGROUP_PARAM_MEMORY_HARD_LIMIT_BYTES,
              String.valueOf(containerHardLimit) + "M");
          ContainerTokenIdentifier id = container.getContainerTokenIdentifier();
          if (id != null && id.getExecutionType() ==
              ExecutionType.OPPORTUNISTIC) {
            cGroupsHandler.updateCGroupParam(MEMORY, cgroupId,
                CGroupsHandler.CGROUP_PARAM_MEMORY_SOFT_LIMIT_BYTES,
                String.valueOf(OPPORTUNISTIC_SOFT_LIMIT) + "M");
            cGroupsHandler.updateCGroupParam(MEMORY, cgroupId,
                CGroupsHandler.CGROUP_PARAM_MEMORY_SWAPPINESS,
                String.valueOf(OPPORTUNISTIC_SWAPPINESS));
          } else {
            cGroupsHandler.updateCGroupParam(MEMORY, cgroupId,
                CGroupsHandler.CGROUP_PARAM_MEMORY_SOFT_LIMIT_BYTES,
                String.valueOf(containerSoftLimit) + "M");
            cGroupsHandler.updateCGroupParam(MEMORY, cgroupId,
                CGroupsHandler.CGROUP_PARAM_MEMORY_SWAPPINESS,
                String.valueOf(swappiness));
          }
        } catch (ResourceHandlerException re) {
          cGroupsHandler.deleteCGroup(MEMORY, cgroupId);
          LOG.warn("Could not update cgroup for container", re);
          throw re;
        }
      }
    }
    return null;
  }

};