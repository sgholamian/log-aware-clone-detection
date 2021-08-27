//,temp,InfinispanRemoteClusterView.java,300,314,temp,InfinispanRemoteClusterView.java,284,298
//,2
public class xxx {
        @ClientCacheEntryExpired
        public void onCacheEntryExpired(ClientCacheEntryExpiredEvent<String> event) {
            if (!running.get()) {
                return;
            }

            LOGGER.debug("onCacheEntryExpired id={}, lock-key={}, event-key={}",
                    getLocalMember().getId(),
                    InfinispanClusterService.LEADER_KEY,
                    event.getKey());

            if (Objects.equals(InfinispanClusterService.LEADER_KEY, event.getKey())) {
                executorService.execute(this::run);
            }
        }

};