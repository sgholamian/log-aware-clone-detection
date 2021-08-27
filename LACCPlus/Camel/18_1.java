//,temp,WebsocketProducerRouteRestartTest.java,110,115,temp,WebsocketProducerRouteExampleTest.java,90,95
//,2
public class xxx {
                            @Override
                            public void onTextFrame(String payload, boolean finalFragment, int rsv) {
                                received.add(payload);
                                log.info("received --> " + payload);
                                latch.countDown();
                            }

};