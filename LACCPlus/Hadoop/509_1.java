//,temp,TestCopyMapper.java,655,663,temp,TestCopyMapper.java,576,584
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