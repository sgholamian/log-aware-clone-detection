//,temp,FailoverDuplicateTest.java,116,127,temp,TwoBrokerQueueClientsReconnectTest.java,630,640
//,3
public class xxx {
                                @Override
                                public void run() {
                                    try {
                                        LOG.info("Waiting for recepit");
                                        assertTrue("message received on time", gotMessageLatch.await(60, TimeUnit.SECONDS));
                                        LOG.info("Stopping connection post send and receive by local queue over bridge");
                                        producerExchange.getConnectionContext().getConnection().stop();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

};