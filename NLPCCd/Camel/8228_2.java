//,temp,sample_1929.java,2,12,temp,sample_7706.java,2,12
//,2
public class xxx {
public void setJmsMessage(Message jmsMessage) {
if (jmsMessage != null) {
try {
setMessageId(jmsMessage.getJMSMessageID());
} catch (JMSException e) {


log.info("unable to retrieve jmsmessageid from jms message");
}
}
}

};