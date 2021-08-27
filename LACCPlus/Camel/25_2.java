//,temp,WebsocketRouteExampleTest.java,81,86,temp,WebsocketSSLClientAuthRouteExampleTest.java,157,162
//,2
public class xxx {
                            @Override
                            public void onTextFrame(String payload, boolean finalFragment, int rsv) {
                                received.add(payload);
                                log.info("received --> " + payload);
                                latch.countDown();
                            }

};