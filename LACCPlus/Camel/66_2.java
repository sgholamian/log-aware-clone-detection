//,temp,WebsocketTwoRoutesToSIndividualAndBroadcastEndpointExampleTest.java,86,91,temp,WebsocketTwoRoutesExampleTest.java,87,92
//,2
public class xxx {
                            @Override
                            public void onTextFrame(String payload, boolean finalFragment, int rsv) {
                                received.add(payload);
                                log.info("received --> " + payload);
                                latch.countDown();
                            }

};