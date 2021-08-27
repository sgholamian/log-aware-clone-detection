//,temp,FailoverTransactionTest.java,1074,1085,temp,AMQ3932Test.java,115,123
//,3
public class xxx {
            public void run() {
                LOG.info("doing async commit...");
                try {
                    consumerSession.commit();
                } catch (JMSException ignored) {
                    ignored.printStackTrace();
                    gotException.countDown();
                } finally {
                    commitDone.countDown();
                }

            }

};