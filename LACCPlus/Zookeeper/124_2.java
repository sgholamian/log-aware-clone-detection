//,temp,FuzzySnapshotRelatedTest.java,266,313,temp,FuzzySnapshotRelatedTest.java,130,171
//,3
public class xxx {
    @Test
    public void testMultiOpConsistency() throws Exception {
        LOG.info("Create a parent node");
        final String path = "/testMultiOpConsistency";
        createEmptyNode(zk[followerA], path);

        LOG.info("Hook to catch the 2nd sub create node txn in multi-op");
        CustomDataTree dt =
                (CustomDataTree) mt[followerA].main.quorumPeer.getZkDb().getDataTree();

        final ZooKeeperServer zkServer = mt[followerA].main.quorumPeer.getActiveServer();

        String node1 = path + "/1";
        String node2 = path + "/2";

        dt.addNodeCreateListener(node2, new NodeCreateListener() {
            @Override
            public void process(String path) {
                LOG.info("Take a snapshot");
                zkServer.takeSnapshot(true);
            }
        });

        LOG.info("Issue a multi op to create 2 nodes");
        zk[followerA].multi(Arrays.asList(
            Op.create(node1, node1.getBytes(),
                    Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT),
            Op.create(node2, node2.getBytes(),
                    Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT))
        );

        LOG.info("Restart the server");
        mt[followerA].shutdown();
        QuorumPeerMainTest.waitForOne(zk[followerA], States.CONNECTING);

        mt[followerA].start();
        QuorumPeerMainTest.waitForOne(zk[followerA], States.CONNECTED);

        LOG.info("Make sure the node consistent with leader");
        Assert.assertEquals(new String(zk[leaderId].getData(node2, null, null)),
                new String(zk[followerA].getData(node2, null, null)));
    }

};