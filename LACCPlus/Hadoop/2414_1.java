//,temp,FileSystem.java,3310,3337,temp,SaslDataTransferClient.java,238,277
//,3
public class xxx {
  public static Class<? extends FileSystem> getFileSystemClass(String scheme,
      Configuration conf) throws IOException {
    if (!FILE_SYSTEMS_LOADED) {
      loadFileSystems();
    }
    LOGGER.debug("Looking for FS supporting {}", scheme);
    Class<? extends FileSystem> clazz = null;
    if (conf != null) {
      String property = "fs." + scheme + ".impl";
      LOGGER.debug("looking for configuration option {}", property);
      clazz = (Class<? extends FileSystem>) conf.getClass(
          property, null);
    } else {
      LOGGER.debug("No configuration: skipping check for fs.{}.impl", scheme);
    }
    if (clazz == null) {
      LOGGER.debug("Looking in service filesystems for implementation class");
      clazz = SERVICE_FILE_SYSTEMS.get(scheme);
    } else {
      LOGGER.debug("Filesystem {} defined in configuration option", scheme);
    }
    if (clazz == null) {
      throw new UnsupportedFileSystemException("No FileSystem for scheme "
          + "\"" + scheme + "\"");
    }
    LOGGER.debug("FS for {} is {}", scheme, clazz);
    return clazz;
  }

};