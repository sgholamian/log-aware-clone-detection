//,temp,FLENewEpochTest.java,92,148,temp,FLERestartTest.java,98,154
//,3
public class xxx {
        public void run(){
            boolean flag = true;
            try{
                while(flag){
                    Vote v = null;
                    peer.setPeerState(ServerState.LOOKING);
                    LOG.info("Going to call leader election again: " + i);
                    v = peer.getElectionAlg().lookForLeader();

                    if (v == null){
                        Assert.fail("Thread " + i + " got a null vote");
                    }

                    /*
                     * A real zookeeper would take care of setting the current vote. Here
                     * we do it manually.
                     */
                    peer.setCurrentVote(v);

                    LOG.info("Finished election: " + i + ", " + v.getId());
                    //votes[i] = v;

                    switch (i) {
                    case 0:
                        LOG.info("First peer, do nothing, just join");
                        if(finish0.tryAcquire(1000, java.util.concurrent.TimeUnit.MILLISECONDS)){
                        //if(threads.get(0).peer.getPeerState() == ServerState.LEADING ){
                            LOG.info("Setting flag to false");
                            flag = false;
                        }
                        break;
                    case 1:
                        LOG.info("Second entering case");
                        if(round[1] != 0){
                            finish0.release();
                            flag = false;
                        } else {
                            finish3.acquire();
                            start0.release();
                        }
                        LOG.info("Second is going to start second round");
                        round[1]++;
                        break;
                    case 2:
                        LOG.info("Third peer, shutting it down");
                        QuorumBase.shutdown(peer);
                        flag = false;
                        round[2] = 1;
                        finish3.release();
                        LOG.info("Third leaving");
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

};