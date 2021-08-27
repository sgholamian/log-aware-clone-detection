//,temp,WebsocketSSLContextGlobalRouteExampleTest.java,155,160,temp,WebsocketTwoRoutesExampleTest.java,142,147
//,2
public class xxx {
                            @Override
                            public void onTextFrame(String payload, boolean finalFragment, int rsv) {
                                received.add(payload);
                                log.info("received --> " + payload);
                                latch.countDown();
                            }

};