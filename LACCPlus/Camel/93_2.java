//,temp,UndertowWsTwoRoutesToSameEndpointSendToAllHeaderTest.java,54,59,temp,UndertowWsTwoRoutesToSameEndpointTest.java,53,58
//,2
public class xxx {
                            @Override
                            public void onTextFrame(String message, boolean finalFragment, int rsv) {
                                received.add(message);
                                LOG.info("received --> " + message);
                                latch.countDown();
                            }

};