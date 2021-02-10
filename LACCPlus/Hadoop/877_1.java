//,temp,TestDomainSocketWatcher.java,194,218,temp,TestDomainSocketWatcher.java,124,147
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
            TimeUnit.MILLISECONDS.sleep(1);
          }
        } catch (Throwable e) {
          LOG.error(e);
          throw new RuntimeException(e);
        }
      }

};