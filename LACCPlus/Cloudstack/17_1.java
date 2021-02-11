//,temp,ConsoleProxyRdpClient.java,88,101,temp,ConsoleProxyVncClient.java,65,72
//,3
public class xxx {
    @Override
    public boolean isFrontEndAlive() {
        if (_socket != null) {
            if (_workerDone || System.currentTimeMillis() - getClientLastFrontEndActivityTime() > ConsoleProxy.VIEWER_LINGER_SECONDS * 1000) {
                s_logger.info("Front end has been idle for too long");
                _socket.shutdown();
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

};