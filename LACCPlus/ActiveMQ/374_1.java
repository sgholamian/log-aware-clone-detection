//,temp,NumberOfDestinationsTest.java,48,67,temp,AMQ6293Test.java,134,149
//,3
public class xxx {
    public void testDestinations() throws Exception {
        ConnectionFactory factory = createConnectionFactory();
        Connection connection = factory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer mp = session.createProducer(null);
        for (int j = 0; j < NUMBER_OF_DESTINATIONS; j++) {
            Destination dest = getDestination(session);
          
            for (int i = 0; i < MESSAGE_COUNT; i++) {
                Message msg = session.createTextMessage("test" + i);
                mp.send(dest, msg);
                
            }
            if (j % 500 == 0) {
                LOG.info("Iterator " + j);
            }
        }
        
        connection.close();
    }

};