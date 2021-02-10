//,temp,LocalJobRunner.java,413,432,temp,LocalJobRunner.java,384,406
//,3
public class xxx {
    protected synchronized ExecutorService createMapExecutor() {

      // Determine the size of the thread pool to use
      int maxMapThreads = job.getInt(LOCAL_MAX_MAPS, 1);
      if (maxMapThreads < 1) {
        throw new IllegalArgumentException(
            "Configured " + LOCAL_MAX_MAPS + " must be >= 1");
      }
      maxMapThreads = Math.min(maxMapThreads, this.numMapTasks);
      maxMapThreads = Math.max(maxMapThreads, 1); // In case of no tasks.

      LOG.debug("Starting mapper thread pool executor.");
      LOG.debug("Max local threads: " + maxMapThreads);
      LOG.debug("Map tasks to process: " + this.numMapTasks);

      // Create a new executor service to drain the work queue.
      ThreadFactory tf = new ThreadFactoryBuilder()
        .setNameFormat("LocalJobRunner Map Task Executor #%d")
        .build();
      ExecutorService executor = Executors.newFixedThreadPool(maxMapThreads, tf);

      return executor;
    }

};