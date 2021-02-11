//,temp,FuzzySnapshotRelatedTest.java,213,237,temp,FuzzySnapshotRelatedTest.java,180,204
//,3
public class xxx {
    @Test
    public void testPZxidUpdatedWhenLoadingSnapshot() throws Exception {

        final String parent = "/testPZxidUpdatedDuringTakingSnapshot";
        final String child = parent + "/child";
        createEmptyNode(zk[followerA], parent);
        createEmptyNode(zk[followerA], child);

        LOG.info("Set up ZKDatabase to catch the node serializing in DataTree");
        addSerializeListener(followerA, parent, child);

        LOG.info("Take snapshot on follower A");
        ZooKeeperServer zkServer = mt[followerA].main.quorumPeer.getActiveServer();
        zkServer.takeSnapshot(true);

        LOG.info("Restarting follower A to load snapshot");
        mt[followerA].shutdown();
        QuorumPeerMainTest.waitForOne(zk[followerA], States.CONNECTING);
        mt[followerA].start();
        QuorumPeerMainTest.waitForOne(zk[followerA], States.CONNECTED);

        LOG.info("Check and make sure the pzxid of the parent is the same " +
                "on leader and follower A");
        compareStat(parent, leaderId, followerA);
    }

};