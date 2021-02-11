//,temp,TestCopyMapper.java,933,941,temp,TestCopyMapper.java,665,673
//,3
public class xxx {
        @Override
        public FileSystem run() {
          try {
            return FileSystem.get(cluster.getConfiguration(0));
          } catch (IOException e) {
            LOG.error("Exception encountered when get FileSystem.", e);
            throw new RuntimeException(e);
          }
        }

};