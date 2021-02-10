//,temp,TestCopyMapper.java,908,919,temp,TestCopyMapper.java,744,752
//,3
public class xxx {
            @Override
            public Mapper<Text, CopyListingFileStatus, Text, Text>.Context
            run() {
              try {
                StubContext stubContext = new StubContext(
                    getConfiguration(), null, 0);
                return stubContext.getContext();
              } catch (Exception e) {
                LOG.error("Exception encountered when get stub context", e);
                throw new RuntimeException(e);
              }
            }

};