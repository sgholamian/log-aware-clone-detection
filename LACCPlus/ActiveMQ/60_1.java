//,temp,FailoverTransactionTest.java,1135,1146,temp,AMQ3932Test.java,144,152
//,3
public class xxx {
            public void run() {
                LOG.info("doing async commit...");
                try {
                    consumerSession.commit();
                } catch (TransactionRolledBackException ex) {
                    gotRollback.countDown();
                } catch (JMSException ex) {
                    exceptions.add(ex);
                } finally {
                    commitDone.countDown();
                }
            }

};