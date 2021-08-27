//,temp,JDBCXACommitExceptionTest.java,497,501,temp,OptimizedAckTest.java,173,177
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("inflight count: " + regionBroker.getDestinationStatistics().getInflight().getCount());
                return 0 == regionBroker.getDestinationStatistics().getInflight().getCount();
            }

};