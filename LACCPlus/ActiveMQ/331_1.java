//,temp,DurableSubProcessConcurrentCommitActivateNoDuplicateTest.java,477,521,temp,DurableSubProcessTest.java,357,388
//,3
public class xxx {
        @Override
        public void run() {
            long end = System.currentTimeMillis() + 60000;
            try {
                boolean sleep = false;
                while (true) {
                    long max = end - System.currentTimeMillis();
                    if (max <= 0)
                        break;

                    /*
                    if (sleep)
                        offline.sleepRandom();
                    else
                        sleep = true;
                    */

                    Thread.sleep(100);

                    processLock.readLock().lock();
                    onlineCount.incrementAndGet();
                    try {
                        process(online.next());
                    } finally {
                        onlineCount.decrementAndGet();
                        processLock.readLock().unlock();
                    }
                }

                if (!ALLOW_SUBSCRIPTION_ABANDONMENT || random(1) > 0)
                    unsubscribe();
                else {
                    LOG.info("Client abandon the subscription. "
                            + this);

                    // housekeeper should sweep these abandoned subscriptions
                    houseKeeper.abandonedSubscriptions.add(conClientId);
                }
            } catch (Throwable e) {
                exit(toString() + " failed.", e);
            }

            clientManager.removeClient(this);
            LOG.info(toString() + " DONE.");
        }

};