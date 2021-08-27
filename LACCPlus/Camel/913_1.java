//,temp,JGroupsRaftEndpoint.java,103,116,temp,JGroupsRaftClusterView.java,101,122
//,3
public class xxx {
    @Override
    protected void doStop() throws Exception {
        LOG.info("Closing JGroupsraft Channel {}", getEndpointUri());
        if (resolvedRaftHandle != null && resolvedRaftHandle.channel() != null) {
            resolvedRaftHandle.channel().close();
            LOG.info("Closed JGroupsraft Channel {}", getEndpointUri());
        }
        LOG.info("Closing Log {}", getEndpointUri());
        if (resolvedRaftHandle != null && resolvedRaftHandle.log() != null) {
            resolvedRaftHandle.log().close();
            LOG.info("Closed Log Channel {}", getEndpointUri());
        }
        super.doStop();
    }

};