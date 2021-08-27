//,temp,InactivityMonitorTest.java,139,173,temp,InactivityMonitorTest.java,74,104
//,3
public class xxx {
    private void startClient() throws Exception, URISyntaxException {
        clientTransport = TransportFactory.connect(new URI("tcp://localhost:" + serverPort + "?trace=true&wireFormat.maxInactivityDuration=1000"));
        clientTransport.setTransportListener(new TransportListener() {
            @Override
            public void onCommand(Object command) {
                clientReceiveCount.incrementAndGet();
                if (clientRunOnCommand != null) {
                    clientRunOnCommand.run();
                }
            }

            @Override
            public void onException(IOException error) {
                if (!ignoreClientError.get()) {
                    LOG.info("Client transport error:");
                    error.printStackTrace();
                    clientErrorCount.incrementAndGet();
                }
            }

            @Override
            public void transportInterupted() {
            }

            @Override
            public void transportResumed() {
            }
        });

        clientTransport.start();
    }

};