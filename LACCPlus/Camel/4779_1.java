//,temp,InfinispanEmbeddedClusterView.java,201,255,temp,InfinispanRemoteClusterView.java,213,282
//,3
public class xxx {
        private synchronized void run() {
            if (!running.get()) {
                return;
            }

            final String leaderKey = InfinispanClusterService.LEADER_KEY;
            final String localId = getLocalMember().getId();

            if (isLeader()) {
                LOGGER.debug("Lock refresh key={}, id{}", leaderKey, localId);

                // I'm still the leader, so refresh the key so it does not expire.
                if (!cache.replace(
                        InfinispanClusterService.LEADER_KEY,
                        getClusterService().getId(),
                        getClusterService().getId(),
                        configuration.getLifespan(),
                        configuration.getLifespanTimeUnit())) {

                    LOGGER.debug("Failed to refresh the lock key={}, id={}", leaderKey, localId);

                    // Looks like I've lost the leadership.
                    setLeader(false);
                }
            }
            if (!isLeader()) {
                LOGGER.debug("Try to acquire lock key={}, id={}", leaderKey, localId);

                Object result = cache.putIfAbsent(
                        InfinispanClusterService.LEADER_KEY,
                        getClusterService().getId(),
                        configuration.getLifespan(),
                        configuration.getLifespanTimeUnit());
                if (result == null) {
                    LOGGER.debug("Lock acquired key={}, id={}", leaderKey, localId);
                    // Acquired the key so I'm the leader.
                    setLeader(true);
                } else if (Objects.equals(getClusterService().getId(), result) && !isLeader()) {
                    LOGGER.debug("Lock resumed key={}, id={}", leaderKey, localId);
                    // Hey, I may have recovered from failure (or reboot was really
                    // fast) and my key was still there so yeah, I'm the leader again!
                    setLeader(true);
                } else {
                    LOGGER.debug("Failed to acquire the lock key={}, id={}", leaderKey, localId);
                    setLeader(false);
                }
            }

            // refresh local membership
            cache.put(
                    getLocalMember().getId(),
                    isLeader() ? "true" : "false",
                    configuration.getLifespan(),
                    configuration.getLifespanTimeUnit());
        }

};