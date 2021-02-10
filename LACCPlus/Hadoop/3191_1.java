//,temp,FpgaNodeResourceUpdateHandler.java,44,70,temp,GpuNodeResourceUpdateHandler.java,39,67
//,3
public class xxx {
  @Override
  public void updateConfiguredResource(Resource res) throws YarnException {
    LOG.info("Initializing configured FPGA resources for the NodeManager.");
    List<FpgaResourceAllocator.FpgaDevice> list = FpgaDiscoverer.getInstance().getCurrentFpgaInfo();
    List<Integer> minors = new LinkedList<>();
    for (FpgaResourceAllocator.FpgaDevice device : list) {
      minors.add(device.getMinor());
    }
    if (minors.isEmpty()) {
      LOG.info("Didn't find any usable FPGAs on the NodeManager.");
      return;
    }
    long count = minors.size();

    Map<String, ResourceInformation> configuredResourceTypes =
        ResourceUtils.getResourceTypes();
    if (!configuredResourceTypes.containsKey(FPGA_URI)) {
      throw new YarnException("Wrong configurations, found " + count +
          " usable FPGAs, however " + FPGA_URI
          + " resource-type is not configured inside"
          + " resource-types.xml, please configure it to enable FPGA feature or"
          + " remove " + FPGA_URI + " from "
          + YarnConfiguration.NM_RESOURCE_PLUGINS);
    }

    res.setResourceValue(FPGA_URI, count);
  }

};