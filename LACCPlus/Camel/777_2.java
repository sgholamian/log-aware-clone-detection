//,temp,JdbcProducer.java,262,272,temp,MllpServerResource.java,903,913
//,3
public class xxx {
        void closeConnection() {
            if (clientSocket != null && !clientSocket.isClosed()) {
                try {
                    clientSocket.close();
                } catch (IOException ioEx) {
                    log.warn("Ignoring IOException encountered when closing client Socket", ioEx);
                } finally {
                    clientSocket = null;
                }
            }
        }

};