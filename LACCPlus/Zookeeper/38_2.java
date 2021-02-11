//,temp,FuzzySnapshotRelatedTest.java,213,237,temp,FuzzySnapshotRelatedTest.java,180,204
//,3
public class xxx {
    @Test
    public void testPZxidUpdatedDuringSnapSyncing() throws Exception {
        LOG.info("Enable force snapshot sync");
        System.setProperty(LearnerHandler.FORCE_SNAP_SYNC, "true");

        final String parent = "/testPZxidUpdatedWhenDeletingNonExistNode";
        final String child = parent + "/child";
        createEmptyNode(zk[leaderId], parent);
        createEmptyNode(zk[leaderId], child);

        LOG.info("shutdown follower {}", followerA);
        mt[followerA].shutdown();
        QuorumPeerMainTest.waitForOne(zk[followerA], States.CONNECTING);

        LOG.info("Set up ZKDatabase to catch the node serializing in DataTree");
        addSerializeListener(leaderId, parent, child);

        LOG.info("Restart follower A to trigger a SNAP sync with leader");
        mt[followerA].start();
        QuorumPeerMainTest.waitForOne(zk[followerA], States.CONNECTED);

        LOG.info("Check and make sure the pzxid of the parent is the same " +
                "on leader and follower A");
        compareStat(parent, leaderId, followerA);
    }

};