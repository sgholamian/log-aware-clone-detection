//,temp,DurableSubProcessMultiRestartTest.java,312,320,temp,DurableSubProcessMultiRestartTest.java,301,310
//,3
public class xxx {
        private void unsubscribe() throws JMSException {
            Connection con = openConnection();
            Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
            session.unsubscribe(SUBSCRIPTION_NAME);
            LOG.info(toString() + " UNSUBSCRIBED");

            session.close();
            con.close();
        }

};