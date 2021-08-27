//,temp,JGroupsRaftEndpoint.java,103,116,temp,JGroupsRaftClusterView.java,101,122
//,3
public class xxx {
    @Override
    protected void doShutdown() throws Exception {
        isMaster = false;
        fireLeadershipChangedEvent(Optional.empty());
        if (raftHandle != null) {
            raftHandle.channel().close();
            raftHandle = null;
        }

        LOG.info("Closing JGroupsraft Channel for JGroupsRaftClusterView with Id {}", raftId);
        if (raftHandle != null && raftHandle.channel() != null) {
            raftHandle.channel().close();

            LOG.info("Closed JGroupsraft Channel Channel for JGroupsRaftClusterView with Id {}", raftId);
        }
        LOG.info("Closing Log for JGroupsRaftClusterView with Id {}", raftId);
        if (raftHandle != null && raftHandle.log() != null) {
            raftHandle.log().close();
            LOG.info("Closed Log for JGroupsRaftClusterView with Id {}", raftId);
        }
        raftHandle = null;
    }

};