//,temp,CnxManagerTest.java,291,336,temp,CnxManagerTest.java,229,282
//,3
public class xxx {
    @Test
    public void testCnxManagerNPE() throws Exception {
        // the connecting peer (id = 2) is a 3.4.6 observer
        peers.get(2L).type = LearnerType.OBSERVER;
        QuorumPeer peer = new QuorumPeer(peers, peerTmpdir[1], peerTmpdir[1],
                peerClientPort[1], 3, 1, 1000, 2, 2);
        QuorumCnxManager cnxManager = peer.createCnxnManager();
        QuorumCnxManager.Listener listener = cnxManager.listener;
        if (listener != null) {
            listener.start();
        } else {
            LOG.error("Null listener when initializing cnx manager");
        }
        int port = peers.get(peer.getId()).electionAddr.getPort();
        LOG.info("Election port: " + port);

        Thread.sleep(1000);

        SocketChannel sc = SocketChannel.open();
        sc.socket().connect(peers.get(1L).electionAddr, 5000);

        /*
         * Write id (3.4.6 protocol). This previously caused a NPE in
         * QuorumCnxManager.
         */
        byte[] msgBytes = new byte[8];
        ByteBuffer msgBuffer = ByteBuffer.wrap(msgBytes);
        msgBuffer.putLong(2L);
        msgBuffer.position(0);
        sc.write(msgBuffer);

        msgBuffer = ByteBuffer.wrap(new byte[8]);
        // write length of message
        msgBuffer.putInt(4);
        // write message
        msgBuffer.putInt(5);
        msgBuffer.position(0);
        sc.write(msgBuffer);

        Message m = cnxManager.pollRecvQueue(1000, TimeUnit.MILLISECONDS);
        Assert.assertNotNull(m);

        peer.shutdown();
        cnxManager.halt();
        Assert.assertFalse(cnxManager.listener.isAlive());
    }

};