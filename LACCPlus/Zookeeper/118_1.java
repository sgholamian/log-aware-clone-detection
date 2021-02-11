//,temp,CnxManagerTest.java,341,364,temp,CnxManagerTest.java,187,219
//,3
public class xxx {
    @Test
    public void testSocketTimeout() throws Exception {
        QuorumPeer peer = new QuorumPeer(peers, peerTmpdir[1], peerTmpdir[1], peerClientPort[1], 3, 1, 2000, 2, 2);
        QuorumCnxManager cnxManager = peer.createCnxnManager();
        QuorumCnxManager.Listener listener = cnxManager.listener;
        if(listener != null){
            listener.start();
        } else {
            LOG.error("Null listener when initializing cnx manager");
        }
        int port = peers.get(peer.getId()).electionAddr.getPort();
        LOG.info("Election port: " + port);
        Thread.sleep(1000);

        Socket sock = new Socket();
        sock.connect(peers.get(1L).electionAddr, 5000);
        long begin = Time.currentElapsedTime();
        // Read without sending data. Verify timeout.
        cnxManager.receiveConnection(sock);
        long end = Time.currentElapsedTime();
        if((end - begin) > ((peer.getSyncLimit() * peer.getTickTime()) + 500)) Assert.fail("Waited more than necessary");
        cnxManager.halt();
        Assert.assertFalse(cnxManager.listener.isAlive());
    }

};