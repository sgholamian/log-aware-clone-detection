//,temp,FLENewEpochTest.java,92,148,temp,FLERestartTest.java,98,154
//,3
public class xxx {
        public void run() {
            try {
                Vote v = null;
                while(true) {
                    peer.setPeerState(ServerState.LOOKING);
                    LOG.info("Going to call leader election again.");
                    v = peer.getElectionAlg().lookForLeader();
                    if(v == null){
                        LOG.info("Thread " + i + " got a null vote");
                        break;
                    }

                    /*
                     * A real zookeeper would take care of setting the current vote. Here
                     * we do it manually.
                     */
                    peer.setCurrentVote(v);

                    LOG.info("Finished election: " + i + ", " + v.getId());
                    //votes[i] = v;

                    switch(i){
                    case 0:
                        if(peerRound == 0){
                            LOG.info("First peer, shutting it down");
                            QuorumBase.shutdown(peer);
                            ((FastLeaderElection) restartThreads.get(i).peer.getElectionAlg()).shutdown();

                            peer = new QuorumPeer(peers, tmpdir[i], tmpdir[i], port[i], 3, i, 1000, 2, 2);
                            peer.startLeaderElection();
                            peerRound++;
                        } else {
                            finish.release(2);
                            return;
                        }

                        break;
                    case 1:
                        LOG.info("Second entering case");
                        finish.acquire();
                        //if(threads.get(0).peer.getPeerState() == ServerState.LEADING ){
                        LOG.info("Release");

                        return;
                    case 2:
                        LOG.info("First peer, do nothing, just join");
                        finish.acquire();
                        //if(threads.get(0).peer.getPeerState() == ServerState.LEADING ){
                        LOG.info("Release");

                        return;
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }

};