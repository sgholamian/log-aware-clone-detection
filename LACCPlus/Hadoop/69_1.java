//,temp,LocalJobRunner.java,413,432,temp,LocalJobRunner.java,384,406
//,3
public class xxx {
    protected synchronized ExecutorService createReduceExecutor() {

      // Determine the size of the thread pool to use
      int maxReduceThreads = job.getInt(LOCAL_MAX_REDUCES, 1);
      if (maxReduceThreads < 1) {
        throw new IllegalArgumentException(
            "Configured " + LOCAL_MAX_REDUCES + " must be >= 1");
      }
      maxReduceThreads = Math.min(maxReduceThreads, this.numReduceTasks);
      maxReduceThreads = Math.max(maxReduceThreads, 1); // In case of no tasks.

      LOG.debug("Starting reduce thread pool executor.");
      LOG.debug("Max local threads: " + maxReduceThreads);
      LOG.debug("Reduce tasks to process: " + this.numReduceTasks);

      // Create a new executor service to drain the work queue.
      ExecutorService executor = Executors.newFixedThreadPool(maxReduceThreads);

      return executor;
    }

};