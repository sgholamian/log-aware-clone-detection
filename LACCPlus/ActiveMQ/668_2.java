//,temp,DurableSubSelectorDelayWithRestartTest.java,245,254,temp,DurableSubProcessConcurrentCommitActivateNoDuplicateTest.java,705,719
//,3
public class xxx {
        private void unsubscribe() throws JMSException {
            processLock.readLock().lock();
            LOG.info("Unsubscribe: " + this);
            try {
                Connection con = openConnection();
                Session session = con
                        .createSession(false, Session.AUTO_ACKNOWLEDGE);
                session.unsubscribe(SUBSCRIPTION_NAME);
                session.close();
                con.close();
            }
            finally {
                processLock.readLock().unlock();
            }
        }

};