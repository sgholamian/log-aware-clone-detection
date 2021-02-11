//,temp,FuzzySnapshotRelatedTest.java,266,313,temp,FuzzySnapshotRelatedTest.java,130,171
//,3
public class xxx {
    @Test
    public void testGlobalSessionConsistency() throws Exception {
        LOG.info("Hook to catch the commitSession event on followerA");
        CustomizedQPMain followerAMain = (CustomizedQPMain) mt[followerA].main;
        final ZooKeeperServer zkServer = followerAMain.quorumPeer.getActiveServer();

        // only take snapshot for the next global session we're going to create
        final AtomicBoolean shouldTakeSnapshot = new AtomicBoolean(true);
        followerAMain.setCommitSessionListener(new CommitSessionListener() {
            @Override
            public void process(long sessionId) {
                LOG.info("Take snapshot");
                if (shouldTakeSnapshot.getAndSet(false)) {
                    zkServer.takeSnapshot(true);
                }
            }
        });

        LOG.info("Create a global session");
        ZooKeeper globalClient = new ZooKeeper(
                "127.0.0.1:" + clientPorts[followerA],
                ClientBase.CONNECTION_TIMEOUT, this);
        QuorumPeerMainTest.waitForOne(globalClient, States.CONNECTED);

        LOG.info("Restart followerA to load the data from disk");
        mt[followerA].shutdown();
        QuorumPeerMainTest.waitForOne(zk[followerA], States.CONNECTING);

        mt[followerA].start();
        QuorumPeerMainTest.waitForOne(zk[followerA], States.CONNECTED);

        LOG.info("Make sure the global sessions are consistent with leader");

        Map<Long, Integer> globalSessionsOnLeader =
                mt[leaderId].main.quorumPeer.getZkDb().getSessionWithTimeOuts();
        if (mt[followerA].main.quorumPeer == null) {
            LOG.info("quorumPeer is null");
        }
        if (mt[followerA].main.quorumPeer.getZkDb() == null) {
            LOG.info("zkDb is null");
        }
        Map<Long, Integer> globalSessionsOnFollowerA =
                mt[followerA].main.quorumPeer.getZkDb().getSessionWithTimeOuts();
        LOG.info("sessions are {}, {}", globalSessionsOnLeader.keySet(),
                globalSessionsOnFollowerA.keySet());
        Assert.assertTrue(globalSessionsOnFollowerA.keySet().containsAll(
                  globalSessionsOnLeader.keySet()));
    }

};