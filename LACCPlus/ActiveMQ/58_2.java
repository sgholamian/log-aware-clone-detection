//,temp,FailoverTransactionTest.java,885,892,temp,FailoverXATransactionTest.java,169,176
//,3
public class xxx {
                                public void run() {
                                    LOG.info("Stopping broker on prepare");
                                    try {
                                        context.getConnection().stop();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

};