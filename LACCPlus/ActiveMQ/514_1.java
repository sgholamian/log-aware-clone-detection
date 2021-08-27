//,temp,JmsConnector.java,566,577,temp,JmsConnector.java,553,564
//,2
public class xxx {
    private void scheduleAsyncForeignConnectionReconnect() {
        this.connectionService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    doInitializeConnection(false);
                } catch (Exception e) {
                    LOG.error("Failed to initialize foreign connection for the JMSConnector", e);
                }
            }
        });
    }

};