//,temp,TestReplaceDatanodeOnFailure.java,290,325,temp,TestReplaceDatanodeOnFailure.java,240,288
//,3
public class xxx {
  @Test
  public void testBestEffort() throws Exception {
    final Configuration conf = new HdfsConfiguration();
    
    //always replace a datanode but do not throw exception
    ReplaceDatanodeOnFailure.write(Policy.ALWAYS, true, conf);

    final MiniDFSCluster cluster = new MiniDFSCluster.Builder(conf
        ).numDataNodes(1).build();

    try {
      final DistributedFileSystem fs = cluster.getFileSystem();
      final Path f = new Path(DIR, "testIgnoreReplaceFailure");
      
      final byte[] bytes = new byte[1000];
      {
        LOG.info("write " + bytes.length + " bytes to " + f);
        final FSDataOutputStream out = fs.create(f, REPLICATION);
        out.write(bytes);
        out.close();

        final FileStatus status = fs.getFileStatus(f);
        Assert.assertEquals(REPLICATION, status.getReplication());
        Assert.assertEquals(bytes.length, status.getLen());
      }

      {
        LOG.info("append another " + bytes.length + " bytes to " + f);
        final FSDataOutputStream out = fs.append(f);
        out.write(bytes);
        out.close();
      }
    } finally {
      if (cluster != null) {cluster.shutdown();}
    }
  }

};