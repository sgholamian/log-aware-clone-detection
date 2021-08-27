//,temp,BadConnectionTest.java,39,46,temp,ActiveMQMessageTest.java,538,547
//,3
public class xxx {
    public void testSetNullPropertyName() throws JMSException {
        Message msg = new ActiveMQMessage();

        try {
            msg.setStringProperty(null, "Cheese");
            fail("Should have thrown exception");
        } catch (IllegalArgumentException e) {
            LOG.info("Worked, caught: " + e);
        }
    }

};