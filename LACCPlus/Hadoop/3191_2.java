//,temp,FpgaNodeResourceUpdateHandler.java,44,70,temp,GpuNodeResourceUpdateHandler.java,39,67
//,3
public class xxx {
  @Override
  public void updateConfiguredResource(Resource res) throws YarnException {
    LOG.info("Initializing configured GPU resources for the NodeManager.");

    List<GpuDevice> usableGpus =
        GpuDiscoverer.getInstance().getGpusUsableByYarn();
    if (null == usableGpus || usableGpus.isEmpty()) {
      String message = "GPU is enabled, but couldn't find any usable GPUs on the "
          + "NodeManager.";
      LOG.error(message);
      // No gpu can be used by YARN.
      throw new YarnException(message);
    }

    long nUsableGpus = usableGpus.size();

    Map<String, ResourceInformation> configuredResourceTypes =
        ResourceUtils.getResourceTypes();
    if (!configuredResourceTypes.containsKey(GPU_URI)) {
      throw new YarnException("Found " + nUsableGpus + " usable GPUs, however "
          + GPU_URI
          + " resource-type is not configured inside"
          + " resource-types.xml, please configure it to enable GPU feature or"
          + " remove " + GPU_URI + " from "
          + YarnConfiguration.NM_RESOURCE_PLUGINS);
    }

    res.setResourceValue(GPU_URI, nUsableGpus);
  }

};