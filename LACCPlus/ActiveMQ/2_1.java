//,temp,AMQ4952Test.java,216,220,temp,OfflineDurableSubscriberTimeoutTest.java,145,149
//,3
public class xxx {
                        @Override
                        public boolean isSatisified() throws Exception {
                            LOG.info("ProducerBroker totalMessageCount: " + producerBroker.getAdminView().getTotalMessageCount());
                            return producerBroker.getAdminView().getTotalMessageCount() == 0;
                        }

};