//,temp,MllpServerResource.java,1209,1219,temp,MllpServerResource.java,849,863
//,3
public class xxx {
        @Override
        public void interrupt() {
            for (ClientSocketThread clientSocketThread : clientSocketThreads) {
                clientSocketThread.interrupt();
            }

            if (serverSocket != null && serverSocket.isBound() && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (Exception ex) {
                    log.warn("Exception encountered closing server socket on interrupt", ex);
                }
            }
            super.interrupt();
        }

};