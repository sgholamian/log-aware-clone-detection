//,temp,DurableSubSelectorDelayWithRestartTest.java,136,171,temp,DurableSubSelectorDelayTest.java,129,161
//,3
public class xxx {
        public void send() throws JMSException {

            int trans = ++transRover;
            boolean relevantTrans = true;
            int count = 40;

            LOG.info("Sending Trans[id=" + trans + ", count="
                    + count + "]");

            Connection con = cf.createConnection();

            Session sess = con.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageProducer prod = sess.createProducer(null);

            for (int i = 0; i < count; i++) {
                Message message = sess.createMessage();
                message.setIntProperty("ID", ++messageRover);
                message.setIntProperty("TRANS", trans);
                message.setBooleanProperty("RELEVANT", false);
                prod.send(topic, message);
            }

            Message message = sess.createMessage();
            message.setIntProperty("ID", ++messageRover);
            message.setIntProperty("TRANS", trans);
            message.setBooleanProperty("COMMIT", true);
            message.setBooleanProperty("RELEVANT", relevantTrans);
            prod.send(topic, message);

            LOG.info("Committed Trans[id=" + trans + ", count="
                    + count + "], ID=" + messageRover);

            sess.close();
            con.close();
        }

};