//,temp,TestCheckpoint.java,492,566,temp,TestCheckpoint.java,421,487
//,3
public class xxx {
  @Test
  public void testSecondaryNamenodeError3() throws IOException {
    LOG.info("Starting testSecondaryNamenodeError3");
    Configuration conf = new HdfsConfiguration();
    Path file1 = new Path("checkpointzz.dat");
    MiniDFSCluster cluster = null;
    FileSystem fileSys = null;
    SecondaryNameNode secondary = null;
    try {
      cluster = new MiniDFSCluster.Builder(conf).numDataNodes(numDatanodes)
          .build();
      cluster.waitActive();
      fileSys = cluster.getFileSystem();
      assertTrue(!fileSys.exists(file1));
      //
      // Make the checkpoint fail after rolling the edit log.
      //
      secondary = startSecondaryNameNode(conf);

      Mockito.doThrow(new IOException(
          "Injecting failure after rolling edit logs"))
          .when(faultInjector).afterSecondaryCallsRollEditLog();

      try {
        secondary.doCheckpoint();  // this should fail
        assertTrue(false);
      } catch (IOException e) {
        // expected
      }
      Mockito.reset(faultInjector);
      secondary.shutdown(); // secondary namenode crash!

      // start new instance of secondary and verify that 
      // a new rollEditLog suceedes inspite of the fact that 
      // edits.new already exists.
      //
      secondary = startSecondaryNameNode(conf);
      secondary.doCheckpoint();  // this should work correctly

      //
      // Create a new file
      //
      writeFile(fileSys, file1, replication);
      checkFile(fileSys, file1, replication);
    } finally {
      fileSys.close();
      cleanup(secondary);
      secondary = null;
      cleanup(cluster);
      cluster = null;
    }

    //
    // Restart cluster and verify that file exists.
    // Then take another checkpoint to verify that the 
    // namenode restart accounted for the twice-rolled edit logs.
    //
    try {
      cluster = new MiniDFSCluster.Builder(conf).numDataNodes(numDatanodes)
          .format(false).build();
      cluster.waitActive();
      fileSys = cluster.getFileSystem();
      checkFile(fileSys, file1, replication);
      cleanupFile(fileSys, file1);
      secondary = startSecondaryNameNode(conf);
      secondary.doCheckpoint();
      secondary.shutdown();
    } finally {
      fileSys.close();
      cleanup(secondary);
      secondary = null;
      cleanup(cluster);
      cluster = null;
    }
  }

};