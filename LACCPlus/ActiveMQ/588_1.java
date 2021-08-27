//,temp,TemporaryDestinationToFromNameTest.java,43,53,temp,TemporaryDestinationToFromNameTest.java,31,41
//,2
public class xxx {
    public void testCreateTemporaryTopicThenCreateATopicFromItsName() throws Exception {
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic tempTopic = session.createTemporaryTopic();
        String name = tempTopic.getTopicName();
        LOG.info("Created topic named: " + name);

        Topic createdTopic = session.createTopic(name);

        assertEquals("created topic not equal to temporary topic", tempTopic, createdTopic);
    }

};