//,temp,DurableSubProcessMultiRestartTest.java,312,320,temp,DurableSubProcessMultiRestartTest.java,301,310
//,3
public class xxx {
        private void subscribe() throws JMSException {
            Connection con = openConnection();
            Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);

            session.createDurableSubscriber(topic, SUBSCRIPTION_NAME);
            LOG.info(toString() + " SUBSCRIBED");

            session.close();
            con.close();
        }

};