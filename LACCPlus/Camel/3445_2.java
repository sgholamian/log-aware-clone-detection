//,temp,InfinispanClusterView.java,50,63,temp,ConsulClusterView.java,142,153
//,3
public class xxx {
        void setMaster(boolean master) {
            if (master && this.master.compareAndSet(false, true)) {
                LOGGER.debug("Leadership taken for session id {}", sessionId.get());
                fireLeadershipChangedEvent(Optional.of(this));
                return;
            }
            if (!master && this.master.compareAndSet(true, false)) {
                LOGGER.debug("Leadership lost for session id {}", sessionId.get());
                fireLeadershipChangedEvent(getLeader());
                return;
            }
        }

};