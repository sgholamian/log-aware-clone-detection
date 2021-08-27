//,temp,InactivityMonitorTest.java,139,173,temp,InactivityMonitorTest.java,74,104
//,3
public class xxx {
    @Override
    public void onAccept(Transport transport) {
        try {
            LOG.info("[" + getName() + "] Server Accepted a Connection");
            serverTransport = transport;
            serverTransport.setTransportListener(new TransportListener() {
                @Override
                public void onCommand(Object command) {
                    serverReceiveCount.incrementAndGet();
                    if (serverRunOnCommand != null) {
                        serverRunOnCommand.run();
                    }
                }

                @Override
                public void onException(IOException error) {
                    if (!ignoreServerError.get()) {
                        LOG.info("Server transport error:", error);
                        serverErrorCount.incrementAndGet();
                    }
                }

                @Override
                public void transportInterupted() {
                }

                @Override
                public void transportResumed() {
                }
            });
            serverTransport.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

};