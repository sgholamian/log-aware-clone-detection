//,temp,DFSClient.java,2957,2982,temp,ErasureCodingWorker.java,74,103
//,3
public class xxx {
  private static synchronized void initThreadsNumForHedgedReads(int num) {
    if (num <= 0 || HEDGED_READ_THREAD_POOL != null) return;
    HEDGED_READ_THREAD_POOL = new ThreadPoolExecutor(1, num, 60,
        TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
        new Daemon.DaemonFactory() {
          private final AtomicInteger threadIndex = new AtomicInteger(0);
          @Override
          public Thread newThread(Runnable r) {
            Thread t = super.newThread(r);
            t.setName("hedgedRead-" + threadIndex.getAndIncrement());
            return t;
          }
        },
        new ThreadPoolExecutor.CallerRunsPolicy() {
          @Override
          public void rejectedExecution(Runnable runnable,
              ThreadPoolExecutor e) {
            LOG.info("Execution rejected, Executing in current thread");
            HEDGED_READ_METRIC.incHedgedReadOpsInCurThread();
            // will run in the current thread
            super.rejectedExecution(runnable, e);
          }
        });
    HEDGED_READ_THREAD_POOL.allowCoreThreadTimeOut(true);
    LOG.debug("Using hedged reads; pool threads={}", num);
  }

};