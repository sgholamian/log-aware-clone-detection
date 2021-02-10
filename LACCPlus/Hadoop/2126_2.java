//,temp,TestCopyMapper.java,687,696,temp,TestCopyMapper.java,606,615
//,2
public class xxx {
        @Override
        public FileSystem run() {
          try {
            return FileSystem.get(configuration);
          } catch (IOException e) {
            LOG.error("Exception encountered ", e);
            Assert.fail("Test failed: " + e.getMessage());
            throw new RuntimeException("Test ought to fail here");
          }
        }

};