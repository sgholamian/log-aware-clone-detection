//,temp,ConsoleProxyRdpClient.java,88,101,temp,ConsoleProxyVncClient.java,65,72
//,3
public class xxx {
    @Override
    public boolean isFrontEndAlive() {
        if (workerDone || System.currentTimeMillis() - getClientLastFrontEndActivityTime() > ConsoleProxy.VIEWER_LINGER_SECONDS * 1000) {
            s_logger.info("Front end has been idle for too long");
            return false;
        }
        return true;
    }

};