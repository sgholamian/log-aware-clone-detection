//,temp,TemporaryDestinationToFromNameTest.java,43,53,temp,TemporaryDestinationToFromNameTest.java,31,41
//,2
public class xxx {
    public void testCreateTemporaryQueueThenCreateAQueueFromItsName() throws Exception {
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue tempQueue = session.createTemporaryQueue();
        String name = tempQueue.getQueueName();
        LOG.info("Created queue named: " + name);

        Queue createdQueue = session.createQueue(name);

        assertEquals("created queue not equal to temporary queue", tempQueue, createdQueue);
    }

};