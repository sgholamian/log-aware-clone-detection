//,temp,Hive.java,5203,5242,temp,Utilities.java,1730,1771
//,3
public class xxx {
  private static Map<Path, FileStatus[]> getNonEmptySubDirs(FileSystem fs, Configuration hConf, FileStatus[] parts)
          throws IOException {
    int threadCount = hConf.getInt(ConfVars.HIVE_MOVE_FILES_THREAD_COUNT.varname, 15);
    final ExecutorService pool = (threadCount <= 0 ? null :
      Executors.newFixedThreadPool(threadCount, new ThreadFactoryBuilder().setDaemon(true).setNameFormat(
                            "Remove-Temp-%d").build()));
    Map<Path, FileStatus[]> partStatusMap = new ConcurrentHashMap<>();
    List<Future<Void>> futures = new LinkedList<>();

    for (FileStatus part : parts) {
      Path path = part.getPath();
      if (pool != null) {
        futures.add(pool.submit(() -> {
          FileStatus[] items = removeEmptyDpDirectory(fs, path);
          partStatusMap.put(path, items);
          return null;
        }));
      } else {
        partStatusMap.put(path, removeEmptyDpDirectory(fs, path));
      }
    }

    if (null != pool) {
      pool.shutdown();
      try {
        for (Future<Void> future : futures) {
          future.get();
        }
      } catch (InterruptedException | ExecutionException e) {
        LOG.error("Exception in getting dir status", e);
        for (Future<Void> future : futures) {
          future.cancel(true);
        }
        throw new IOException(e);
      }
    }

    // Dump of its metrics
    LOG.debug("FS {}", fs);

    return partStatusMap;
  }

};