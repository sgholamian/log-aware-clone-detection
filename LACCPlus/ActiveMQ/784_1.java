//,temp,WireformatNegociationTest.java,95,125,temp,WireformatNegociationTest.java,60,85
//,3
public class xxx {
            public void onAccept(Transport transport) {
                try {
                    LOG.info("[" + getName() + "] Server Accepted a Connection");
                    serverTransport = transport;
                    serverTransport.setTransportListener(new TransportListener() {
                        public void onCommand(Object command) {
                            if (command instanceof WireFormatInfo) {
                                serverWF.set((WireFormatInfo)command);
                                negociationCounter.countDown();
                            }
                        }

                        public void onException(IOException error) {
                            if (!ignoreAsycError.get()) {
                                LOG.info("Server transport error: ", error);
                                asyncError.set(error);
                                negociationCounter.countDown();
                            }
                        }

                        public void transportInterupted() {
                        }

                        public void transportResumed() {
                        }
                    });
                    serverTransport.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

};