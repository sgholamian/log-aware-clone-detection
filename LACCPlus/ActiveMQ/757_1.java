//,temp,JDBCXACommitExceptionTest.java,497,501,temp,OptimizedAckTest.java,173,177
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("MESSAGES DRAINED :" + ((RegionBroker)other.getRegionBroker()).getDestinationStatistics().getMessages().getCount());
                return 0 == ((RegionBroker)other.getRegionBroker()).getDestinationStatistics().getMessages().getCount();
            }

};