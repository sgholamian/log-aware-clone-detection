//,temp,DurableSubSelectorDelayWithRestartTest.java,245,254,temp,DurableSubProcessConcurrentCommitActivateNoDuplicateTest.java,705,719
//,3
public class xxx {
        public void subscribe() throws JMSException{
            LOG.info(toString() + "SUBSCRIBING");
            Connection con = openConnection();

            Session sess = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
            sess.createDurableSubscriber(topic, subName, selector, false);

            sess.close();
            con.close();
        }

};