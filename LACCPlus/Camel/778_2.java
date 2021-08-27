//,temp,UndertowWsTwoRoutesTest.java,93,98,temp,WebsocketProducerRouteExampleTest.java,145,150
//,3
public class xxx {
                            @Override
                            public void onBinaryFrame(byte[] payload, boolean finalFragment, int rsv) {
                                received.add(payload);
                                log.info("received --> " + Arrays.toString(payload));
                                latch.countDown();
                            }

};