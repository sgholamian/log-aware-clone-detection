//,temp,DurableSubProcessWithRestartTest.java,434,472,temp,DurableSubDelayedUnsubscribeTest.java,471,514
//,3
public class xxx {
        @Override
        public void run() {
            long end = System.currentTimeMillis() + lifetime.next();
            try {
                boolean sleep = false;
                while (true) {
                    long max = end - System.currentTimeMillis();
                    if (max <= 0)
                        break;

                    if (sleep)
                        offline.sleepRandom();
                    else
                        sleep = true;

                    processLock.readLock().lock();
                    try {
                        process(online.next());
                    } finally {
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