//,temp,FsVolumeImpl.java,715,749,temp,FsVolumeImpl.java,676,697
//,3
public class xxx {
    private String getNextSubDir(String prev, File dir)
          throws IOException {
      List<String> children = fileIoProvider.listDirectory(
          FsVolumeImpl.this, dir, SubdirFilter.INSTANCE);
      cache = null;
      cacheMs = 0;
      if (children.size() == 0) {
        LOG.trace("getNextSubDir({}, {}): no subdirectories found in {}",
            storageID, bpid, dir.getAbsolutePath());
        return null;
      }
      Collections.sort(children);
      String nextSubDir = nextSorted(children, prev);
      if (nextSubDir == null) {
        LOG.trace("getNextSubDir({}, {}): no more subdirectories found in {}",
            storageID, bpid, dir.getAbsolutePath());
      } else {
        LOG.trace("getNextSubDir({}, {}): picking next subdirectory {} " +
            "within {}", storageID, bpid, nextSubDir, dir.getAbsolutePath());
      }
      return nextSubDir;
    }

};