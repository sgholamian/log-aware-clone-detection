//,temp,InfinispanEmbeddedClusterView.java,201,255,temp,InfinispanRemoteClusterView.java,213,282
//,3
public class xxx {
        private synchronized void run() {
            if (!running.get()) {
                return;
            }

            final String leaderKey = InfinispanClusterService.LEADER_KEY;
            final String localId = getLocalMember().getId();

            if (isLeader() && version != null) {
                LOGGER.debug("Lock refresh key={}, id{} with version={}", leaderKey, localId, version);

                // I'm still the leader, so refresh the key so it does not expire.
                if (!cache.replaceWithVersion(
                        leaderKey,
                        getClusterService().getId(),
                        version,
                        lifespan)) {

                    LOGGER.debug("Failed to refresh the lock key={}, id={}, version={}", leaderKey, localId, version);

                    setLeader(false);
                } else {
                    version = cache.getWithMetadata(leaderKey).getVersion();

                    LOGGER.debug("Lock refreshed key={}, ud={}, with new version={}", leaderKey, localId, version);
                }
            }

            if (!isLeader()) {
                LOGGER.debug("Try to acquire lock key={}, id={}", leaderKey, localId);

                Object result = cache.withFlags(Flag.FORCE_RETURN_VALUE)
                        .putIfAbsent(
                                leaderKey,
                                localId,
                                configuration.getLifespan(),
                                configuration.getLifespanTimeUnit());

                if (result == null) {
                    // Acquired the key so I'm the leader.
                    setLeader(true);

                    // Get the version
                    version = cache.getWithMetadata(leaderKey).getVersion();

                    LOGGER.debug("Lock acquired key={}, id={}, with version={}", leaderKey, localId, version);

                } else if (Objects.equals(getClusterService().getId(), result) && !isLeader()) {
                    // Hey, I may have recovered from failure (or reboot was really
                    // fast) and my key was still there so yeah, I'm the leader again!
                    setLeader(true);

                    // Get the version
                    version = cache.getWithMetadata(leaderKey).getVersion();

                    LOGGER.debug("Lock resumed key={}, id={} with version={}", leaderKey, localId, version);
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