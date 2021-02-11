//,temp,TestDomainSocketWatcher.java,222,247,temp,TestDomainSocketWatcher.java,151,175
//,3
public class xxx {
      @Override
      public void run() {
        final Random random = new Random();
        try {
          while (handled.get() != SOCKET_NUM) {
            lock.lock();
            try {
              if (!pairs.isEmpty()) {
                int idx = random.nextInt(pairs.size());
                DomainSocket pair[] = pairs.remove(idx);
                if (random.nextBoolean()) {
                  pair[0].close();
                } else {
                  watcher.remove(pair[1]);
                }
              }
            } finally {
              lock.unlock();
            }
          }
        } catch (Throwable e) {
          LOG.error(e);
          throw new RuntimeException(e);
        }
      }

};