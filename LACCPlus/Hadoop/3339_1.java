//,temp,TestCopyMapper.java,600,609,temp,TestCopyMapper.java,526,535
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