//,temp,NMClientAsyncImpl.java,126,174,temp,ContainerLauncherImpl.java,263,325
//,3
public class xxx {
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