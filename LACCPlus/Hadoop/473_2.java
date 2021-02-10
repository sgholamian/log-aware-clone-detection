//,temp,TestCheckpoint.java,492,566,temp,TestCheckpoint.java,421,487
//,3
public class xxx {
  @Test
  public void testSecondaryNamenodeError2() throws IOException {
    LOG.info("Starting testSecondaryNamenodeError2");
    Configuration conf = new HdfsConfiguration();
    Path file1 = new Path("checkpointyy.dat");
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
      // Make the checkpoint fail after uploading the new fsimage.
      //
      secondary = startSecondaryNameNode(conf);
      
      Mockito.doThrow(new IOException(
          "Injecting failure after uploading new image"))
          .when(faultInjector).afterSecondaryUploadsNewImage();

      try {
        secondary.doCheckpoint();  // this should fail
        assertTrue(false);
      } catch (IOException e) {
        // expected
      }
      Mockito.reset(faultInjector);

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
    // namenode restart accounted for the rolled edit logs.
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