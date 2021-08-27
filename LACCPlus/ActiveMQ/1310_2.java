//,temp,DurableSubProcessConcurrentCommitActivateNoDuplicateTest.java,382,394,temp,DurableSubProcessTest.java,272,283
//,2
public class xxx {
        private void createNewClient() throws JMSException {
            ClientType type = ClientType.randomClientType();

            Client client;
            synchronized (server.sendMutex) {
                client = new Client(++clientRover, type, CLIENT_LIFETIME, CLIENT_ONLINE, CLIENT_OFFLINE);
                clients.add(client);
            }
            client.start();

            LOG.info(client.toString() + " created. " + this);
        }

};