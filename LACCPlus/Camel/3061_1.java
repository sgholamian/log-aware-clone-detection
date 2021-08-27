//,temp,MllpServerResource.java,1209,1219,temp,MllpServerResource.java,849,863
//,3
public class xxx {
        @Override
        public void interrupt() {
            if (clientSocket != null && clientSocket.isConnected() && !clientSocket.isClosed()) {
                try {
                    clientSocket.close();
                } catch (Exception ex) {
                    log.warn("Exception encountered closing client socket on interrput", ex);
                }
            }
            super.interrupt();
        }

};