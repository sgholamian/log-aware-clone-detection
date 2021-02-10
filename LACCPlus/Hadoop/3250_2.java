//,temp,TestDomainSocketWatcher.java,186,258,temp,TestDomainSocketWatcher.java,116,184
//,3
public class xxx {
  @Test(timeout=300000)
  public void testStress() throws Exception {
    final int SOCKET_NUM = 250;
    final ReentrantLock lock = new ReentrantLock();
    final DomainSocketWatcher watcher = newDomainSocketWatcher(10000000);
    final ArrayList<DomainSocket[]> pairs = new ArrayList<DomainSocket[]>();
    final AtomicInteger handled = new AtomicInteger(0);

    final Thread adderThread = new Thread(new Runnable() {
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
    });
    
    final Thread removerThread = new Thread(new Runnable() {
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
          LOG.error(e.toString());
          throw new RuntimeException(e);
        }
      }
    });

    adderThread.start();
    removerThread.start();
    Uninterruptibles.joinUninterruptibly(adderThread);
    Uninterruptibles.joinUninterruptibly(removerThread);
    watcher.close();
  }

};