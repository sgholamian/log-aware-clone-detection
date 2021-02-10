//,temp,NMClientAsyncImpl.java,113,181,temp,ContainerLauncherImpl.java,263,325
//,3
public class xxx {
  @Override
  protected void serviceStart() throws Exception {
    client.start();

    ThreadFactory tf = new ThreadFactoryBuilder().setNameFormat(
        this.getClass().getName() + " #%d").setDaemon(true).build();

    // Start with a default core-pool size and change it dynamically.
    int initSize = Math.min(INITIAL_THREAD_POOL_SIZE, maxThreadPoolSize);
    threadPool = new ThreadPoolExecutor(initSize, Integer.MAX_VALUE, 1,
        TimeUnit.HOURS, new LinkedBlockingQueue<Runnable>(), tf);

    eventDispatcherThread = new Thread() {
      @Override
      public void run() {
        ContainerEvent event = null;
        Set<String> allNodes = new HashSet<String>();

        while (!stopped.get() && !Thread.currentThread().isInterrupted()) {
          try {
            event = events.take();
          } catch (InterruptedException e) {
            if (!stopped.get()) {
              LOG.error("Returning, thread interrupted", e);
            }
            return;
          }

          allNodes.add(event.getNodeId().toString());

          int threadPoolSize = threadPool.getCorePoolSize();

          // We can increase the pool size only if haven't reached the maximum
          // limit yet.
          if (threadPoolSize != maxThreadPoolSize) {

            // nodes where containers will run at *this* point of time. This is
            // *not* the cluster size and doesn't need to be.
            int nodeNum = allNodes.size();
            int idealThreadPoolSize = Math.min(maxThreadPoolSize, nodeNum);

            if (threadPoolSize < idealThreadPoolSize) {
              // Bump up the pool size to idealThreadPoolSize +
              // INITIAL_POOL_SIZE, the later is just a buffer so we are not
              // always increasing the pool-size
              int newThreadPoolSize = Math.min(maxThreadPoolSize,
                  idealThreadPoolSize + INITIAL_THREAD_POOL_SIZE);
              LOG.info("Set NMClientAsync thread pool size to " +
                  newThreadPoolSize + " as the number of nodes to talk to is "
                  + nodeNum);
              threadPool.setCorePoolSize(newThreadPoolSize);
            }
          }

          // the events from the queue are handled in parallel with a thread
          // pool
          threadPool.execute(getContainerEventProcessor(event));

          // TODO: Group launching of multiple containers to a single
          // NodeManager into a single connection
        }
      }
    };
    eventDispatcherThread.setName("Container  Event Dispatcher");
    eventDispatcherThread.setDaemon(false);
    eventDispatcherThread.start();

    super.serviceStart();
  }

};