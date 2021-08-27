//,temp,WebsocketStaticTest.java,81,86,temp,WebsocketSSLRouteExampleTest.java,151,156
//,2
public class xxx {
                            @Override
                            public void onTextFrame(String payload, boolean finalFragment, int rsv) {
                                received.add(payload);
                                log.info("received --> " + payload);
                                latch.countDown();
                            }

};