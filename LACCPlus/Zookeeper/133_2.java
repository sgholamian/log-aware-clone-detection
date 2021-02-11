//,temp,CnxManagerTest.java,291,336,temp,CnxManagerTest.java,229,282
//,3
public class xxx {
    @Test
    public void testCnxManagerSpinLock() throws Exception {
        QuorumPeer peer = new QuorumPeer(peers, peerTmpdir[1], peerTmpdir[1], peerClientPort[1], 3, 1, 1000, 2, 2);
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

        SocketChannel sc = SocketChannel.open();
        sc.socket().connect(peers.get(1L).electionAddr, 5000);

        InetSocketAddress otherAddr = peers.get(new Long(2)).electionAddr;
        DataOutputStream dout = new DataOutputStream(sc.socket().getOutputStream());
        dout.writeLong(QuorumCnxManager.PROTOCOL_VERSION);
        dout.writeLong(new Long(2));
        String addr = otherAddr.getHostString()+ ":" + otherAddr.getPort();
        byte[] addr_bytes = addr.getBytes();
        dout.writeInt(addr_bytes.length);
        dout.write(addr_bytes);
        dout.flush();
        

        ByteBuffer msgBuffer = ByteBuffer.wrap(new byte[4]);
        msgBuffer.putInt(-20);
        msgBuffer.position(0);
        sc.write(msgBuffer);

        Thread.sleep(1000);

        try{
            /*
             * Write a number of times until it
             * detects that the socket is broken.
             */
            for(int i = 0; i < 100; i++){
                msgBuffer.position(0);
                sc.write(msgBuffer);
            }
            Assert.fail("Socket has not been closed");
        } catch (Exception e) {
            LOG.info("Socket has been closed as expected");
        }
        peer.shutdown();
        cnxManager.halt();
        Assert.assertFalse(cnxManager.listener.isAlive());
    }

};