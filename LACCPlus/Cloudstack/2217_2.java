//,temp,LegacyDbUpgrade.java,32,40,temp,ClusterServiceServletContainer.java,112,121
//,3
public class xxx {
        public void stopRunning() {
            if (_serverSocket != null) {
                try {
                    _serverSocket.close();
                } catch (IOException e) {
                    s_logger.info("[ignored] error on closing server socket", e);
                }
                _serverSocket = null;
            }
        }

};