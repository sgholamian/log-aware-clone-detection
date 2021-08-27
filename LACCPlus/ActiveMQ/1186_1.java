//,temp,FailoverReadInactivityBlockWriteTimeoutClientTest.java,139,144,temp,RedeliveryPolicyTest.java,503,507
//,2
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {

                LOG.info("current total message count: " + broker.getAdminView().getTotalMessageCount());
                return broker.getAdminView().getTotalMessageCount() == 0;
            }

};