//,temp,NMClientAsyncImpl.java,157,205,temp,ContainerLauncherImpl.java,289,335
//,3
public class xxx {
      @Override
      public void run() {
        ContainerLauncherEvent event = null;
        Set<String> allNodes = new HashSet<String>();

        while (!stopped.get() && !Thread.currentThread().isInterrupted()) {
          try {
            event = eventQueue.take();
          } catch (InterruptedException e) {
            if (!stopped.get()) {
              LOG.error("Returning, interrupted : " + e);
            }
            return;
          }
          allNodes.add(event.getContainerMgrAddress());

          int poolSize = launcherPool.getCorePoolSize();

          // See if we need up the pool size only if haven't reached the
          // maximum limit yet.
          if (poolSize != limitOnPoolSize) {

            // nodes where containers will run at *this* point of time. This is
            // *not* the cluster size and doesn't need to be.
            int numNodes = allNodes.size();
            int idealPoolSize = Math.min(limitOnPoolSize, numNodes);

            if (poolSize < idealPoolSize) {
              // Bump up the pool size to idealPoolSize+initialPoolSize, the
              // later is just a buffer so we are not always increasing the
              // pool-size
              int newPoolSize = Math.min(limitOnPoolSize, idealPoolSize
                  + initialPoolSize);
              LOG.info("Setting ContainerLauncher pool size to " + newPoolSize
                  + " as number-of-nodes to talk to is " + numNodes);
              launcherPool.setCorePoolSize(newPoolSize);
            }
          }

          // the events from the queue are handled in parallel
          // using a thread pool
          launcherPool.execute(createEventProcessor(event));

          // TODO: Group launching of multiple containers to a single
          // NodeManager into a single connection
        }
      }

};