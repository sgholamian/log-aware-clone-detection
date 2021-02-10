//,temp,TestCopyMapper.java,512,521,temp,TestCopyMapper.java,438,447
//,2
public class xxx {
            @Override
            public Mapper<Text, CopyListingFileStatus, Text, Text>.Context run() {
              try {
                StubContext stubContext = new StubContext(getConfiguration(), null, 0);
                return stubContext.getContext();
              } catch (Exception e) {
                LOG.error("Exception encountered ", e);
                throw new RuntimeException(e);
              }
            }

};