//,temp,JmsConnector.java,566,577,temp,JmsConnector.java,553,564
//,2
public class xxx {
    private void scheduleAsyncLocalConnectionReconnect() {
        this.connectionService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    doInitializeConnection(true);
                } catch (Exception e) {
                    LOG.error("Failed to initialize local connection for the JMSConnector", e);
                }
            }
        });
    }

};