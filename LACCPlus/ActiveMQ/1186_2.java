//,temp,FailoverReadInactivityBlockWriteTimeoutClientTest.java,139,144,temp,RedeliveryPolicyTest.java,503,507
//,2
public class xxx {
                    @Override
                    public boolean isSatisified() throws Exception {
                        LOG.info("total dequeue count: " + broker.getAdminView().getTotalDequeueCount());
                        return broker.getAdminView().getTotalDequeueCount() == 1;
                    }

};