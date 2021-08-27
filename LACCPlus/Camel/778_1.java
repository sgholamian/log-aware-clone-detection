//,temp,UndertowWsTwoRoutesTest.java,93,98,temp,WebsocketProducerRouteExampleTest.java,145,150
//,3
public class xxx {
                                @Override
                                public void onTextFrame(String message, boolean finalFragment, int rsv) {
                                    received.add(message);
                                    LOG.info("received --> " + message);
                                    latch.countDown();
                                }

};