//,temp,TimedLeaderNotifier.java,117,137,temp,TimedLeaderNotifier.java,68,92
//,3
public class xxx {
    public void refreshLeadership(Optional<String> leader, Long timestamp, Long lease, Set<String> members) {
        Objects.requireNonNull(leader, "leader must be non null (use Optional.empty)");
        Objects.requireNonNull(members, "members must be non null (use empty set)");
        long version;
        try {
            lock.lock();

            this.currentLeader = leader;
            this.currentMembers = members;
            this.timestamp = timestamp;
            this.lease = lease;
            version = ++changeCounter;
        } finally {
            lock.unlock();
        }

        LOG.debug("Updated leader to {} at version version {}", leader, version);
        this.executor.execute(() -> checkAndNotify(version));
        if (leader.isPresent()) {
            long time = System.currentTimeMillis();
            long delay = Math.max(timestamp + lease + FIXED_DELAY - time, FIXED_DELAY);
            LOG.debug("Setting expiration in {} millis for version {}", delay, version);
            this.executor.schedule(() -> expiration(version), delay, TimeUnit.MILLISECONDS);
        }
    }

};