//,temp,DeserializationPerfTest.java,37,69,temp,SerializationPerfTest.java,59,76
//,3
public class xxx {
    private static void deserializeTree(int depth, int width, int len)
            throws InterruptedException, IOException, KeeperException.NodeExistsException, KeeperException.NoNodeException {
        BinaryInputArchive ia;
        int count;
        {
            DataTree tree = new DataTree();
            SerializationPerfTest.createNodes(tree, "/", depth, width, tree.getNode("/").stat.getCversion(), new byte[len]);
            count = tree.getNodeCount();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BinaryOutputArchive oa = BinaryOutputArchive.getArchive(baos);
            tree.serialize(oa, "test");
            baos.flush();

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ia = BinaryInputArchive.getArchive(bais);
        }

        DataTree dserTree = new DataTree();

        System.gc();
        long start = System.nanoTime();
        dserTree.deserialize(ia, "test");
        long end = System.nanoTime();
        long durationms = (end - start) / 1000000L;
        long pernodeus = ((end - start) / 1000L) / count;

        Assert.assertEquals(count, dserTree.getNodeCount());

        LOG.info("Deserialized " + count + " nodes in " + durationms
                + " ms (" + pernodeus + "us/node), depth=" + depth + " width="
                + width + " datalen=" + len);
    }

};