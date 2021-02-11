//,temp,CnxManagerTest.java,341,364,temp,CnxManagerTest.java,187,219
//,3
public class xxx {
    @Test
    public void testCnxManagerTimeout() throws Exception {
        Random rand = new Random();
        byte b = (byte) rand.nextInt();
        int deadPort = PortAssignment.unique();
        String deadAddress = "10.1.1." + b;

        LOG.info("This is the dead address I'm trying: " + deadAddress);

        peers.put(Long.valueOf(2),
                new QuorumServer(2,
                        new InetSocketAddress(deadAddress, deadPort),
                        new InetSocketAddress(deadAddress, PortAssignment.unique()),
                        new InetSocketAddress(deadAddress, PortAssignment.unique())));
        peerTmpdir[2] = ClientBase.createTmpDir();

        QuorumPeer peer = new QuorumPeer(peers, peerTmpdir[1], peerTmpdir[1], peerClientPort[1], 3, 1, 1000, 2, 2);
        QuorumCnxManager cnxManager = peer.createCnxnManager();
        QuorumCnxManager.Listener listener = cnxManager.listener;
        if(listener != null){
            listener.start();
        } else {
            LOG.error("Null listener when initializing cnx manager");
        }

        long begin = Time.currentElapsedTime();
        cnxManager.toSend(2L, createMsg(ServerState.LOOKING.ordinal(), 1, -1, 1));
        long end = Time.currentElapsedTime();

        if((end - begin) > 6000) Assert.fail("Waited more than necessary");
        cnxManager.halt();
        Assert.assertFalse(cnxManager.listener.isAlive());
    }

};