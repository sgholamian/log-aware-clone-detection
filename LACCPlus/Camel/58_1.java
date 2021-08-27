//,temp,UndertowWsProducerRouteRestartTest.java,80,85,temp,UndertowWsProducerRouteTest.java,59,64
//,2
public class xxx {
                    @Override
                    public void onTextFrame(String message, boolean finalFragment, int rsv) {
                        received.add(message);
                        LOG.info("received --> " + message);
                        latch.countDown();
                    }

};