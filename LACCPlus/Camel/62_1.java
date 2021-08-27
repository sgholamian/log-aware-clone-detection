//,temp,WebsocketClientCamelRouteTest.java,81,86,temp,WebsocketSSLContextInUriRouteExampleTest.java,154,159
//,2
public class xxx {
                            @Override
                            public void onTextFrame(String payload, boolean finalFragment, int rsv) {
                                received.add(payload);
                                log.info("received --> " + payload);
                                latch.countDown();
                            }

};