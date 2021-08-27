//,temp,DbRestartJDBCQueueTest.java,117,126,temp,TwoBrokerQueueClientsReconnectTest.java,473,483
//,3
public class xxx {
                public void run() {
                    LOG.info("Sleeping for 10 seconds before allowing db restart");
                    try {
                        restartDBLatch.await(10, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ds.setShutdownDatabase("false");
                    LOG.info("DB RESTARTED!@!!!!");
                }

};