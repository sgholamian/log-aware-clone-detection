//,temp,TwoBrokerQueueClientsReconnectTest.java,550,560,temp,TwoBrokerQueueClientsReconnectTest.java,396,406
//,2
public class xxx {
                                @Override
                                public void run() {
                                    try {
                                        LOG.info("Waiting for recepit");
                                        assertTrue("message received on time", gotMessageLatch.await(60, TimeUnit.SECONDS));
                                        LOG.info("Stopping connection post send and receive and multiple producers");
                                        producerExchange.getConnectionContext().getConnection().stop();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

};