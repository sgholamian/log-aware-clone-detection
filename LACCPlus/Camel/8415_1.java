//,temp,TimedLeaderNotifier.java,117,137,temp,TimedLeaderNotifier.java,68,92
//,3
public class xxx {
    private void expiration(long version) {
        try {
            lock.lock();

            if (version != this.changeCounter) {
                return;
            }

            long time = System.currentTimeMillis();
            if (time < this.timestamp + this.lease) {
                long delay = this.timestamp + this.lease - time;
                LOG.debug("Delaying expiration by {} millis at version version {}", delay + FIXED_DELAY, version);
                this.executor.schedule(() -> expiration(version), delay + FIXED_DELAY, TimeUnit.MILLISECONDS);
                return;
            }
        } finally {
            lock.unlock();
        }

        checkAndNotify(version);
    }

};