//,temp,TestCopyMapper.java,606,615,temp,TestCopyMapper.java,463,472
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