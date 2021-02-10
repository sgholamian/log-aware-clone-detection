//,temp,CleanerTask.java,70,90,temp,CleanerService.java,175,192
//,3
public class xxx {
  private void removeGlobalCleanerPidFile() {
    try {
      FileSystem fs = FileSystem.get(this.conf);
      String root =
          conf.get(YarnConfiguration.SHARED_CACHE_ROOT,
              YarnConfiguration.DEFAULT_SHARED_CACHE_ROOT);

      Path pidPath = new Path(root, GLOBAL_CLEANER_PID);


      fs.delete(pidPath, false);
      LOG.info("Removed the global cleaner pid file at " + pidPath.toString());
    } catch (IOException e) {
      LOG.error(
          "Unable to remove the global cleaner pid file! The file may need "
              + "to be removed manually.", e);
    }
  }

};