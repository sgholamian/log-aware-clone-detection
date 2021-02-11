//,temp,TestReplaceDatanodeOnFailure.java,290,325,temp,TestReplaceDatanodeOnFailure.java,240,288
//,3
public class xxx {
  @Test
  public void testAppend() throws Exception {
    final Configuration conf = new HdfsConfiguration();
    final short REPLICATION = (short)3;
    
    final MiniDFSCluster cluster = new MiniDFSCluster.Builder(conf
        ).numDataNodes(1).build();

    try {
      final DistributedFileSystem fs = cluster.getFileSystem();
      final Path f = new Path(DIR, "testAppend");
      
      {
        LOG.info("create an empty file " + f);
        fs.create(f, REPLICATION).close();
        final FileStatus status = fs.getFileStatus(f);
        Assert.assertEquals(REPLICATION, status.getReplication());
        Assert.assertEquals(0L, status.getLen());
      }
      
      
      final byte[] bytes = new byte[1000];
      {
        LOG.info("append " + bytes.length + " bytes to " + f);
        final FSDataOutputStream out = fs.append(f);
        out.write(bytes);
        out.close();

        final FileStatus status = fs.getFileStatus(f);
        Assert.assertEquals(REPLICATION, status.getReplication());
        Assert.assertEquals(bytes.length, status.getLen());
      }

      {
        LOG.info("append another " + bytes.length + " bytes to " + f);
        try {
          final FSDataOutputStream out = fs.append(f);
          out.write(bytes);
          out.close();

          Assert.fail();
        } catch(IOException ioe) {
          LOG.info("This exception is expected", ioe);
        }
      }
    } finally {
      if (cluster != null) {cluster.shutdown();}
    }
  }

};