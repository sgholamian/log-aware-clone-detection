//,temp,DFSClient.java,2957,2982,temp,ErasureCodingWorker.java,74,103
//,3
public class xxx {
  private void initializeStripedReadThreadPool() {
    LOG.debug("Using striped reads");

    // Essentially, this is a cachedThreadPool.
    stripedReadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
        60, TimeUnit.SECONDS,
        new SynchronousQueue<>(),
        new Daemon.DaemonFactory() {
          private final AtomicInteger threadIndex = new AtomicInteger(0);

          @Override
          public Thread newThread(Runnable r) {
            Thread t = super.newThread(r);
            t.setName("stripedRead-" + threadIndex.getAndIncrement());
            return t;
          }
        },
        new ThreadPoolExecutor.CallerRunsPolicy() {
          @Override
          public void rejectedExecution(Runnable runnable,
                                        ThreadPoolExecutor e) {
            LOG.info("Execution for striped reading rejected, "
                + "Executing in current thread");
            // will run in the current thread
            super.rejectedExecution(runnable, e);
          }
        });

    stripedReadPool.allowCoreThreadTimeOut(true);
  }

};