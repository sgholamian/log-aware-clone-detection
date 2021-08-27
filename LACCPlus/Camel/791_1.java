//,temp,UndertowWsTwoRoutesTest.java,53,58,temp,TestClient.java,140,145
//,3
public class xxx {
                                @Override
                                public void onTextFrame(String message, boolean finalFragment, int rsv) {
                                    received.add(message);
                                    LOG.info("received --> " + message);
                                    latch.countDown();
                                }

};