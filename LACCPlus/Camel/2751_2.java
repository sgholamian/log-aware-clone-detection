//,temp,InfinispanEmbeddedClusterView.java,273,287,temp,InfinispanEmbeddedClusterView.java,257,271
//,2
public class xxx {
        @CacheEntryRemoved
        public void onCacheEntryRemoved(CacheEntryEvent<Object, Object> event) {
            if (!running.get()) {
                return;
            }

            LOGGER.debug("onCacheEntryRemoved id={}, lock-key={}, event-key={}",
                    getLocalMember().getId(),
                    InfinispanClusterService.LEADER_KEY,
                    event.getKey());

            if (Objects.equals(InfinispanClusterService.LEADER_KEY, event.getKey())) {
                executorService.execute(this::run);
            }
        }

};