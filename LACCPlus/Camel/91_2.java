//,temp,WebsocketTwoRoutesToSameEndpointExampleTest.java,85,90,temp,WebsocketComponentRouteExampleTest.java,80,85
//,2
public class xxx {
                            @Override
                            public void onTextFrame(String payload, boolean finalFragment, int rsv) {
                                received.add(payload);
                                log.info("received --> " + payload);
                                latch.countDown();
                            }

};