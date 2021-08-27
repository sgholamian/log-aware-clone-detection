//,temp,TestClient.java,147,152,temp,UndertowWssRouteTest.java,129,134
//,2
public class xxx {
                            @Override
                            public void onTextFrame(String message, boolean finalFragment, int rsv) {
                                received.add(message);
                                LOG.info("received --> " + message);
                                latch.countDown();
                            }

};