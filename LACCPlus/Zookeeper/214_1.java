//,temp,QuorumUtil.java,220,234,temp,QuorumUtil.java,201,212
//,3
public class xxx {
    public void startThenShutdown(int id) throws IOException {
        PeerStruct ps = getPeer(id);
        LOG.info("Creating QuorumPeer " + ps.id + "; public port " + ps.clientPort);
        ps.peer = new QuorumPeer(peersView, ps.dataDir, ps.dataDir, ps.clientPort, electionAlg,
                ps.id, tickTime, initLimit, syncLimit);
        if (localSessionEnabled) {
            ps.peer.enableLocalSessions(true);
        }
        Assert.assertEquals(ps.clientPort, ps.peer.getClientPort());

        ps.peer.start();
        Assert.assertTrue("Waiting for server up", ClientBase.waitForServerUp("127.0.0.1:"
                + getPeer(id).clientPort, ClientBase.CONNECTION_TIMEOUT));
        shutdown(id);
    }

};