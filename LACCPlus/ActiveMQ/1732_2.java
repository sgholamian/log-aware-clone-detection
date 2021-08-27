//,temp,QueueBrowsingLimitTest.java,80,112,temp,JDBCTablePrefixAssignedTest.java,66,92
//,3
public class xxx {
    @Test
    public void testTablesHave() throws Exception {

        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("vm://localhost?create=false");
        ActiveMQConnection connection = (ActiveMQConnection) cf.createConnection();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("TEST.FOO");
        MessageProducer producer = session.createProducer(destination);

        for (int i = 0; i < 10; ++i) {
            producer.send(session.createTextMessage("test"));
        }
        producer.close();
        connection.close();

        List<Message> queuedMessages = null;
        try {
            queuedMessages = dumpMessages();
        } catch (Exception ex) {
            LOG.info("Caught ex: ", ex);
            fail("Should not have thrown an exception");
        }

        assertNotNull(queuedMessages);
        assertEquals("Should have found 10 messages", 10, queuedMessages.size());
    }

};