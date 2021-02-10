//,temp,FsVolumeImpl.java,715,749,temp,FsVolumeImpl.java,676,697
//,3
public class xxx {
    private List<String> getSubdirEntries() throws IOException {
      if (state.curFinalizedSubDir == null) {
        return null; // There are no entries in the null subdir.
      }
      long now = Time.monotonicNow();
      if (cache != null) {
        long delta = now - cacheMs;
        if (delta < maxStalenessMs) {
          return cache;
        } else {
          LOG.trace("getSubdirEntries({}, {}): purging entries cache for {} " +
            "after {} ms.", storageID, bpid, state.curFinalizedSubDir, delta);
          cache = null;
        }
      }
      File dir = Paths.get(bpidDir.getAbsolutePath(), "current", "finalized",
                    state.curFinalizedDir, state.curFinalizedSubDir).toFile();
      List<String> entries = fileIoProvider.listDirectory(
          FsVolumeImpl.this, dir, BlockFileFilter.INSTANCE);
      if (entries.size() == 0) {
        entries = null;
      } else {
        Collections.sort(entries);
      }
      if (entries == null) {
        LOG.trace("getSubdirEntries({}, {}): no entries found in {}",
            storageID, bpid, dir.getAbsolutePath());
      } else {
        LOG.trace("getSubdirEntries({}, {}): listed {} entries in {}", 
            storageID, bpid, entries.size(), dir.getAbsolutePath());
      }
      cache = entries;
      cacheMs = now;
      return cache;
    }

};