//,temp,WireformatNegociationTest.java,95,125,temp,WireformatNegociationTest.java,60,85
//,3
public class xxx {
    private void startClient(String uri) throws Exception, URISyntaxException {
        clientTransport = TransportFactory.connect(new URI(uri));
        clientTransport.setTransportListener(new TransportListener() {
            public void onCommand(Object command) {
                if (command instanceof WireFormatInfo) {
                    clientWF.set((WireFormatInfo)command);
                    negociationCounter.countDown();
                }
            }

            public void onException(IOException error) {
                if (!ignoreAsycError.get()) {
                    LOG.info("Client transport error: ", error);
                    asyncError.set(error);
                    negociationCounter.countDown();
                }
            }

            public void transportInterupted() {
            }

            public void transportResumed() {
            }
        });
        clientTransport.start();
    }

};