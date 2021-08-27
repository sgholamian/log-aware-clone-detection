//,temp,ServerSessionImplTest.java,415,427,temp,AMQ4441Test.java,69,83
//,3
public class xxx {
                @Override
                public void run() {
                    while (!done.get() && latch.getCount() > 0) {
                        try {
                            final PooledConnection pooledConnection = (PooledConnection) pooled.createConnection();
                            if (pooledConnection.getConnection() == null) {
                                LOG.info("Found broken connection.");
                                latch.countDown();
                            }
                            pooledConnection.close();
                        } catch (JMSException e) {
                            LOG.warn("Caught Exception", e);
                        }
                    }
                }

};