//,temp,TestFileAppend.java,352,388,temp,TestFileAppend.java,313,349
//,3
public class xxx {
  @Test
  public void testAppendTwice() throws Exception {
    Configuration conf = new HdfsConfiguration();
    MiniDFSCluster cluster = new MiniDFSCluster.Builder(conf).build();
    final FileSystem fs1 = cluster.getFileSystem();
    final FileSystem fs2 = AppendTestUtil.createHdfsWithDifferentUsername(conf);
    try {
  
      final Path p = new Path("/testAppendTwice/foo");
      final int len = 1 << 16;
      final byte[] fileContents = AppendTestUtil.initBuffer(len);

      {
        // create a new file with a full block.
        FSDataOutputStream out = fs2.create(p, true, 4096, (short)1, len);
        out.write(fileContents, 0, len);
        out.close();
      }
  
      //1st append does not add any data so that the last block remains full
      //and the last block in INodeFileUnderConstruction is a BlockInfo
      //but not BlockInfoUnderConstruction. 
      fs2.append(p);
      
      //2nd append should get AlreadyBeingCreatedException
      fs1.append(p);
      Assert.fail();
    } catch(RemoteException re) {
      AppendTestUtil.LOG.info("Got an exception:", re);
      Assert.assertEquals(AlreadyBeingCreatedException.class.getName(),
          re.getClassName());
    } finally {
      fs2.close();
      fs1.close();
      cluster.shutdown();
    }
  }

};