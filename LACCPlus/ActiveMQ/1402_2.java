//,temp,SpringProducer.java,40,45,temp,MessagePriorityTest.java,227,232
//,3
public class xxx {
    protected Message createMessage(int priority) throws Exception {
        final String text = "priority " + priority;
        Message msg = sess.createTextMessage(text);
        LOG.info("Sending  " + text);
        return msg;
    }

};