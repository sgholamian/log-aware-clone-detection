//,temp,DurableSubProcessWithRestartTest.java,342,354,temp,DurableSubDelayedUnsubscribeTest.java,400,411
//,3
public class xxx {
        private void createNewClient() throws JMSException {
            ClientType type = ClientType.randomClientType();

            Client client;
            synchronized (server.sendMutex) {
                client = new Client(++clientRover, type);
                clients.add(client);
            }
            client.start();

            LOG.info(client.toString() + " created. " + this);
        }

};