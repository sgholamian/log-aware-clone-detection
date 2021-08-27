//,temp,Hive.java,5203,5242,temp,Utilities.java,1730,1771
//,3
public class xxx {
  public static boolean trashFiles(final FileSystem fs, final FileStatus[] statuses,
      final Configuration conf, final boolean purge)
      throws IOException {
    boolean result = true;

    if (statuses == null || statuses.length == 0) {
      return false;
    }
    final List<Future<Boolean>> futures = new LinkedList<>();
    final ExecutorService pool = conf.getInt(ConfVars.HIVE_MOVE_FILES_THREAD_COUNT.varname, 25) > 0 ?
        Executors.newFixedThreadPool(conf.getInt(ConfVars.HIVE_MOVE_FILES_THREAD_COUNT.varname, 25),
        new ThreadFactoryBuilder().setDaemon(true).setNameFormat("Delete-Thread-%d").build()) : null;
    final SessionState parentSession = SessionState.get();
    for (final FileStatus status : statuses) {
      if (null == pool) {
        result &= FileUtils.moveToTrash(fs, status.getPath(), conf, purge);
      } else {
        futures.add(pool.submit(new Callable<Boolean>() {
          @Override
          public Boolean call() throws Exception {
            SessionState.setCurrentSessionState(parentSession);
            return FileUtils.moveToTrash(fs, status.getPath(), conf, purge);
          }
        }));
      }
    }
    if (null != pool) {
      pool.shutdown();
      for (Future<Boolean> future : futures) {
        try {
          result &= future.get();
        } catch (InterruptedException | ExecutionException e) {
          LOG.error("Failed to delete: ",e);
          pool.shutdownNow();
          throw new IOException(e);
        }
      }
    }
    return result;
  }

};