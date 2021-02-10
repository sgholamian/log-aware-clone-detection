//,temp,TestCopyMapper.java,776,785,temp,TestCopyMapper.java,620,629
//,2
public class xxx {
        @Override
        public FileSystem run() {
          try {
            return FileSystem.get(cluster.getConfiguration(0));
          } catch (IOException e) {
            LOG.error("Exception encountered ", e);
            Assert.fail("Test failed: " + e.getMessage());
            throw new RuntimeException("Test ought to fail here");
          }
        }

};