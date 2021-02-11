//,temp,CommitterEventHandler.java,117,159,temp,ContainerLauncherImpl.java,263,325
//,3
public class xxx {
  @Override
  protected void serviceStart() throws Exception {
    ThreadFactoryBuilder tfBuilder = new ThreadFactoryBuilder()
        .setNameFormat("CommitterEvent Processor #%d");
    if (jobClassLoader != null) {
      // if the job classloader is enabled, we need to use the job classloader
      // as the thread context classloader (TCCL) of these threads in case the
      // committer needs to load another class via TCCL
      ThreadFactory backingTf = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
          Thread thread = new Thread(r);
          thread.setContextClassLoader(jobClassLoader);
          return thread;
        }
      };
      tfBuilder.setThreadFactory(backingTf);
    }
    ThreadFactory tf = tfBuilder.build();
    launcherPool = new ThreadPoolExecutor(5, 5, 1,
        TimeUnit.HOURS, new LinkedBlockingQueue<Runnable>(), tf);
    eventHandlingThread = new Thread(new Runnable() {
      @Override
      public void run() {
        CommitterEvent event = null;
        while (!stopped.get() && !Thread.currentThread().isInterrupted()) {
          try {
            event = eventQueue.take();
          } catch (InterruptedException e) {
            if (!stopped.get()) {
              LOG.error("Returning, interrupted : " + e);
            }
            return;
          }
          // the events from the queue are handled in parallel
          // using a thread pool
          launcherPool.execute(new EventProcessor(event));        }
      }
    });
    eventHandlingThread.setName("CommitterEvent Handler");
    eventHandlingThread.start();
    super.serviceStart();
  }

};