//,temp,TestDataNodeRollingUpgrade.java,395,467,temp,TestDataNodeRollingUpgrade.java,334,389
//,3
public class xxx {
  @Test(timeout=300000)
  public void testWithLayoutChangeAndFinalize() throws Exception {
    final long seed = 0x600DF00D;
    try {
      startCluster();

      Path[] paths = new Path[3];
      File[] blockFiles = new File[3];

      // Create two files in DFS.
      for (int i = 0; i < 2; ++i) {
        paths[i] = new Path("/" + GenericTestUtils.getMethodName() + "." + i + ".dat");
        DFSTestUtil.createFile(fs, paths[i], BLOCK_SIZE, (short) 2, seed);
      }

      startRollingUpgrade();

      // Delete the first file. The DN will save its block files in trash.
      blockFiles[0] = getBlockForFile(paths[0], true);
      File trashFile0 = getTrashFileForBlock(blockFiles[0], false);
      deleteAndEnsureInTrash(paths[0], blockFiles[0], trashFile0);

      // Restart the DN with a new layout version to trigger layout upgrade.
      LOG.info("Shutting down the Datanode");
      MiniDFSCluster.DataNodeProperties dnprop = cluster.stopDataNode(0);
      DFSTestUtil.addDataNodeLayoutVersion(
          DataNodeLayoutVersion.CURRENT_LAYOUT_VERSION - 1,
          "Test Layout for TestDataNodeRollingUpgrade");
      LOG.info("Restarting the DataNode");
      cluster.restartDataNode(dnprop, true);
      cluster.waitActive();

      dn0 = cluster.getDataNodes().get(0);
      LOG.info("The DN has been restarted");
      assertFalse(trashFile0.exists());
      assertFalse(dn0.getStorage().getBPStorage(blockPoolId).isTrashAllowed(blockFiles[0]));

      // Ensure that the block file for the first file was moved from 'trash' to 'previous'.
      assertTrue(isBlockFileInPrevious(blockFiles[0]));
      assertFalse(isTrashRootPresent());

      // Delete the second file. Ensure that its block file is in previous.
      blockFiles[1] = getBlockForFile(paths[1], true);
      fs.delete(paths[1], false);
      assertTrue(isBlockFileInPrevious(blockFiles[1]));
      assertFalse(isTrashRootPresent());

      // Finalize and ensure that neither block file exists in trash or previous.
      finalizeRollingUpgrade();
      assertFalse(isTrashRootPresent());
      assertFalse(isBlockFileInPrevious(blockFiles[0]));
      assertFalse(isBlockFileInPrevious(blockFiles[1]));
    } finally {
      shutdownCluster();
    }
  }

};