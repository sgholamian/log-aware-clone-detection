//,temp,SpringProducer.java,40,45,temp,MessagePriorityTest.java,227,232
//,3
public class xxx {
                public Message createMessage(Session session) throws JMSException {
                    LOG.info("Sending message: " + text);
                    TextMessage message = session.createTextMessage(text);
                    message.setStringProperty("next", "foo");
                    return message;
                }

};