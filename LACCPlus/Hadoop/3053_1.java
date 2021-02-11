//,temp,ServiceApiUtil.java,389,395,temp,ServiceApiUtil.java,382,387
//,3
public class xxx {
  public static Service loadServiceUpgrade(SliderFileSystem fs,
      String serviceName, String version) throws IOException {
    Path versionPath = fs.buildClusterUpgradeDirPath(serviceName, version);
    Path versionedDef = new Path(versionPath, serviceName + ".json");
    LOG.info("Loading service definition from {}", versionedDef);
    return jsonSerDeser.load(fs.getFileSystem(), versionedDef);
  }

};