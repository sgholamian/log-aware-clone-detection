//,temp,TestCopyMapper.java,744,752,temp,TestCopyMapper.java,665,673
//,2
public class xxx {
        @Override
        public StubContext run() {
          try {
            return new StubContext(getConfiguration(), null, 0);
          } catch (Exception e) {
            LOG.error("Exception encountered ", e);
            throw new RuntimeException(e);
          }
        }

};