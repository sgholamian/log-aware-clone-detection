//,temp,DeserializationPerfTest.java,37,69,temp,SerializationPerfTest.java,59,76
//,3
public class xxx {
    private static void serializeTree(int depth, int width, int len)
            throws InterruptedException, IOException, KeeperException.NodeExistsException, KeeperException.NoNodeException {
        DataTree tree = new DataTree();
        createNodes(tree, "/", depth, width, tree.getNode("/").stat.getCversion(), new byte[len]);
        int count = tree.getNodeCount();

        BinaryOutputArchive oa =
            BinaryOutputArchive.getArchive(new NullOutputStream());
        System.gc();
        long start = System.nanoTime();
        tree.serialize(oa, "test");
        long end = System.nanoTime();
        long durationms = (end - start)/1000000L;
        long pernodeus = ((end - start)/1000L)/count;
        LOG.info("Serialized " + count + " nodes in "
                + durationms + " ms (" + pernodeus + "us/node), depth="
                + depth + " width=" + width + " datalen=" + len);
    }

};