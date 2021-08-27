//,temp,ServerSessionImplTest.java,415,427,temp,AMQ4441Test.java,69,83
//,3
public class xxx {
                @Override
                public void run() {
                    LOG.info("[" + iteration + "] Closing pool on delivered {} and dispatched {}", messageSent.getCount(), messageCountRef.get().getCount());
                    try {
                        pool.close();
                        closeSuccess.countDown();
                    } catch (InvalidMessageEndpointException error) {
                        LOG.error("Ex on pool close", error);
                        //error.printStackTrace();
                    } finally {
                        closeDone.countDown();
                    }
                }

};