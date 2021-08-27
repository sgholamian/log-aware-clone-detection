//,temp,QueryCompactor.java,262,273,temp,CompactorMR.java,1036,1043
//,3
public class xxx {
    static void removeFilesForMmTable(HiveConf conf, AcidDirectory dir) throws IOException {
      List<Path> filesToDelete = dir.getAbortedDirectories();
      if (filesToDelete.size() < 1) {
        return;
      }
      LOG.info("About to remove " + filesToDelete.size() + " aborted directories from " + dir);
      FileSystem fs = filesToDelete.get(0).getFileSystem(conf);
      for (Path dead : filesToDelete) {
        LOG.debug("Going to delete path " + dead.toString());
        fs.delete(dead, true);
      }
    }

};