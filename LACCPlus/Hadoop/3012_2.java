//,temp,TestDomainSocketWatcher.java,195,219,temp,TestDomainSocketWatcher.java,125,148
//,3
public class xxx {
      @Override
      public void run() {
        try {
          for (int i = 0; i < SOCKET_NUM; i++) {
            DomainSocket pair[] = DomainSocket.socketpair();
            watcher.add(pair[1], new DomainSocketWatcher.Handler() {
              @Override
              public boolean handle(DomainSocket sock) {
                handled.incrementAndGet();
                return true;
              }
            });
            lock.lock();
            try {
              pairs.add(pair);
            } finally {
              lock.unlock();
            }
          }
        } catch (Throwable e) {
          LOG.error(e.toString());
          throw new RuntimeException(e);
        }
      }

};