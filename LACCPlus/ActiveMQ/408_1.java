//,temp,JMSInteroperabilityTest.java,170,252,temp,JMSInteroperabilityTest.java,89,168
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    @Test(timeout = 60000)
    public void testMessagePropertiesArePreservedAMQPToOpenWire() throws Exception {

        // Raw Transformer doesn't expand message properties.
        assumeFalse(transformer.equals("raw"));

        boolean bool = true;
        byte bValue = 127;
        short nShort = 10;
        int nInt = 5;
        long nLong = 333;
        float nFloat = 1;
        double nDouble = 100;
        Enumeration<String> propertyNames = null;
        String testMessageBody = "Testing msgPropertyExistTest";

        Connection openwire = createJMSConnection();
        Connection amqp = createConnection();

        openwire.start();
        amqp.start();

        Session openwireSession = openwire.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Session amqpSession = amqp.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination queue = openwireSession.createQueue(getDestinationName());

        MessageProducer amqpProducer = amqpSession.createProducer(queue);
        MessageConsumer openwireConsumer = openwireSession.createConsumer(queue);

        TextMessage outbound = amqpSession.createTextMessage();
        outbound.setText(testMessageBody);
        outbound.setBooleanProperty("Boolean", bool);
        outbound.setByteProperty("Byte", bValue);
        outbound.setShortProperty("Short", nShort);
        outbound.setIntProperty("Integer", nInt);
        outbound.setFloatProperty("Float", nFloat);
        outbound.setDoubleProperty("Double", nDouble);
        outbound.setStringProperty("String", "test");
        outbound.setLongProperty("Long", nLong);
        outbound.setObjectProperty("BooleanObject", Boolean.valueOf(bool));

        amqpProducer.send(outbound);

        Message inbound = openwireConsumer.receive(2500);

        propertyNames = inbound.getPropertyNames();
        int propertyCount = 0;
        do {
            String propertyName = propertyNames.nextElement();

            if (propertyName.indexOf("JMS") != 0) {
                propertyCount++;
                if (propertyName.equals("Boolean") || propertyName.equals("Byte") ||
                    propertyName.equals("Integer") || propertyName.equals("Short") ||
                    propertyName.equals("Float") || propertyName.equals("Double") ||
                    propertyName.equals("String") || propertyName.equals("Long") ||
                    propertyName.equals("BooleanObject")) {

                    LOG.debug("Appclication Property set by client is: {}", propertyName);
                    if (!inbound.propertyExists(propertyName)) {
                        assertTrue(inbound.propertyExists(propertyName));
                        LOG.debug("Positive propertyExists test failed for {}", propertyName);
                    } else if (inbound.propertyExists(propertyName + "1")) {
                        LOG.debug("Negative propertyExists test failed for {} 1", propertyName);
                        fail("Negative propertyExists test failed for " + propertyName + "1");
                    }
                } else {
                    LOG.debug("Appclication Property not set by client: {}", propertyName);
                    fail("Appclication Property not set by client: " + propertyName);
                }
            } else {
                LOG.debug("JMSProperty Name is: {}", propertyName);
            }

        } while (propertyNames.hasMoreElements());

        amqp.close();
        openwire.close();

        assertEquals("Unexpected number of properties in received message.", 9, propertyCount);
    }

};