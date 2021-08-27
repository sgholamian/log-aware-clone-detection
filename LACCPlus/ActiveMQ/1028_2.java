//,temp,DurableSubProcessWithRestartTest.java,434,472,temp,DurableSubDelayedUnsubscribeTest.java,471,514
//,3
public class xxx {
        @Override
        public void run() {
            // long end = System.currentTimeMillis() + lifetime.next();
            long end = System.currentTimeMillis() + lifetime;
            try {
                boolean sleep = false;
                while (true) {
                    long max = end - System.currentTimeMillis();
                    if (max <= 0)
                        break;

                    if (sleep)
                        Thread.sleep(offline);
                    // offline.sleepRandom();
                    else
                        sleep = true;

                    processLock.readLock().lock();
                    try {
                        process(online);
                    } finally {
                        processLock.readLock().unlock();
                    }
                }

                // 50% unsubscribe, 50% abondon subscription
                if (!ALLOW_SUBSCRIPTION_ABANDONMENT) {
                    unsubscribe();
                    ALLOW_SUBSCRIPTION_ABANDONMENT = true;
                } else {

                    LOG.info("Client abandon the subscription. " + this);

                    // housekeeper should sweep these abandoned subscriptions
                    houseKeeper.abandonedSubscriptions.add(conClientId);
                    ALLOW_SUBSCRIPTION_ABANDONMENT = false;
                }
            } catch (Throwable e) {
                exit(toString() + " failed.", e);
            }

            clientManager.removeClient(this);
            LOG.info(toString() + " DONE.");
        }

};