//,temp,BrokerRedeliveryTest.java,174,179,temp,SoWriteTimeoutClientTest.java,94,99
//,2
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                // wait for ack to be processes
                LOG.info("Total message count: " + broker.getAdminView().getTotalMessageCount());
                return broker.getAdminView().getTotalMessageCount() == 0;
            }

};