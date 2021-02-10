//,temp,ServiceApiUtil.java,389,395,temp,ServiceApiUtil.java,382,387
//,3
public class xxx {
  public static Service loadService(SliderFileSystem fs, String
      serviceName) throws IOException {
    Path serviceJson = getServiceJsonPath(fs, serviceName);
    LOG.info("Loading service definition from " + serviceJson);
    return jsonSerDeser.load(fs.getFileSystem(), serviceJson);
  }

};