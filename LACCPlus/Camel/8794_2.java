//,temp,InfinispanEmbeddedClusterView.java,173,191,temp,InfinispanRemoteClusterView.java,181,203
//,3
public class xxx {
        @Override
        protected void doStop() throws Exception {
            super.doStop();

            this.running.set(false);

            if (cache != null) {
                cache.removeClientListener(this);
            }

            getCamelContext().getExecutorServiceManager().shutdownGraceful(executorService);

            if (cache != null) {
                if (this.version != null) {
                    cache.removeWithVersion(InfinispanClusterService.LEADER_KEY, this.version);
                }

                LOGGER.info("Removing local member, key={}", getLocalMember().getId());
                cache.remove(getLocalMember().getId());
            }

            this.version = null;
        }

};