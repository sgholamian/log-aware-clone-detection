//,temp,InfinispanClusterView.java,50,63,temp,ConsulClusterView.java,142,153
//,3
public class xxx {
        public void setLeader(boolean master) {
            if (master && this.leader.compareAndSet(false, true)) {
                LOGGER.debug("Leadership taken for id: {}", id);

                fireLeadershipChangedEvent(Optional.of(this));
                return;
            }
            if (!master && this.leader.compareAndSet(true, false)) {
                LOGGER.debug("Leadership lost for id: {}", id);

                fireLeadershipChangedEvent(getLeader());
                return;
            }
        }

};