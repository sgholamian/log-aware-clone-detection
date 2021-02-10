//,temp,CGroupsCpuResourceHandlerImpl.java,205,246,temp,CGroupsMemoryResourceHandlerImpl.java,110,150
//,3
public class xxx {
  @Override
  public List<PrivilegedOperation> updateContainer(Container container)
      throws ResourceHandlerException {
    Resource containerResource = container.getResource();
    String cgroupId = container.getContainerId().toString();
    File cgroup = new File(cGroupsHandler.getPathForCGroup(CPU, cgroupId));
    if (cgroup.exists()) {
      try {
        int containerVCores = containerResource.getVirtualCores();
        ContainerTokenIdentifier id = container.getContainerTokenIdentifier();
        if (id != null && id.getExecutionType() ==
            ExecutionType.OPPORTUNISTIC) {
          cGroupsHandler
              .updateCGroupParam(CPU, cgroupId,
                  CGroupsHandler.CGROUP_CPU_SHARES,
                  String.valueOf(CPU_DEFAULT_WEIGHT_OPPORTUNISTIC));
        } else {
          int cpuShares = CPU_DEFAULT_WEIGHT * containerVCores;
          cGroupsHandler
              .updateCGroupParam(CPU, cgroupId,
                  CGroupsHandler.CGROUP_CPU_SHARES,
                  String.valueOf(cpuShares));
        }
        if (strictResourceUsageMode) {
          if (nodeVCores != containerVCores) {
            float containerCPU =
                (containerVCores * yarnProcessors) / (float) nodeVCores;
            int[] limits = getOverallLimits(containerCPU);
            cGroupsHandler.updateCGroupParam(CPU, cgroupId,
                CGroupsHandler.CGROUP_CPU_PERIOD_US, String.valueOf(limits[0]));
            cGroupsHandler.updateCGroupParam(CPU, cgroupId,
                CGroupsHandler.CGROUP_CPU_QUOTA_US, String.valueOf(limits[1]));
          }
        }
      } catch (ResourceHandlerException re) {
        cGroupsHandler.deleteCGroup(CPU, cgroupId);
        LOG.warn("Could not update cgroup for container", re);
        throw re;
      }
    }
    return null;
  }

};