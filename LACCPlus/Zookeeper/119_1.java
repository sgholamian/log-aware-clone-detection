//,temp,FLETest.java,472,531,temp,FLETest.java,411,466
//,3
public class xxx {
    @Test
    public void testJoinInconsistentEnsemble() throws Exception {
        int sid;
        QuorumPeer peer;
        int waitTime = 10 * 1000;
        ArrayList<QuorumPeer> peerList = new ArrayList<QuorumPeer>();
        for(sid = 0; sid < 3; sid++) {
            peers.put(Long.valueOf(sid),
                new QuorumServer(sid,
                    new InetSocketAddress(
                        "127.0.0.1", PortAssignment.unique()),
                    new InetSocketAddress(
                        "127.0.0.1", PortAssignment.unique())));
            tmpdir[sid] = ClientBase.createTmpDir();
            port[sid] = PortAssignment.unique();
        }
        // start 2 peers and verify if they form the cluster
        for (sid = 0; sid < 2; sid++) {
            peer = new QuorumPeer(peers, tmpdir[sid], tmpdir[sid],
                                             port[sid], 3, sid, 2000, 2, 2);
            LOG.info("Starting peer " + peer.getId());
            peer.start();
            peerList.add(sid, peer);
        }
        peer = peerList.get(0);
        VerifyState v1 = new VerifyState(peerList.get(0));
        v1.start();
        v1.join(waitTime);
        Assert.assertFalse("Unable to form cluster in " +
            waitTime + " ms",
            !v1.isSuccess());
        // Change the election round for one of the members of the ensemble
        long leaderSid = peer.getCurrentVote().getId();
        long zxid = peer.getCurrentVote().getZxid();
        long electionEpoch = peer.getCurrentVote().getElectionEpoch();
        ServerState state = peer.getCurrentVote().getState();
        long peerEpoch = peer.getCurrentVote().getPeerEpoch();
        Vote newVote = new Vote(leaderSid, zxid+100, electionEpoch+100, peerEpoch, state);
        peer.setCurrentVote(newVote);
        // Start 3rd peer and check if it joins the quorum
        peer = new QuorumPeer(peers, tmpdir[2], tmpdir[2],
                 port[2], 3, 2, 2000, 2, 2);
        LOG.info("Starting peer " + peer.getId());
        peer.start();
        peerList.add(sid, peer);
        v1 = new VerifyState(peer);
        v1.start();
        v1.join(waitTime);
        if (v1.isAlive()) {
               Assert.fail("Peer " + peer.getId() + " failed to join the cluster " +
                "within " + waitTime + " ms");
        }
        // cleanup
        for (int id = 0; id < 3; id++) {
            peer = peerList.get(id);
            if (peer != null) {
                peer.shutdown();
            }
        }
    }

};