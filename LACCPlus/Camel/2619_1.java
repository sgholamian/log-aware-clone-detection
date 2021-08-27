//,temp,UndertowWsTwoRoutesTest.java,42,125,temp,UndertowWsConsumerRouteTest.java,233,302
//,3
public class xxx {
    @Test
    public void testWSHttpCallEcho() throws Exception {

        // We call the route WebSocket BAR
        {
            final List<String> received = new ArrayList<>();
            final CountDownLatch latch = new CountDownLatch(1);
            final AsyncHttpClient c = new DefaultAsyncHttpClient();
            final WebSocket websocket = c.prepareGet("ws://localhost:" + getPort() + "/bar").execute(
                    new WebSocketUpgradeHandler.Builder()
                            .addWebSocketListener(new WebSocketListener() {
                                @Override
                                public void onTextFrame(String message, boolean finalFragment, int rsv) {
                                    received.add(message);
                                    LOG.info("received --> " + message);
                                    latch.countDown();
                                }

                                @Override
                                public void onOpen(WebSocket websocket) {
                                }

                                @Override
                                public void onClose(WebSocket websocket, int code, String reason) {
                                }

                                @Override
                                public void onError(Throwable t) {
                                    LOG.warn("Unhandled exception: {}", t.getMessage(), t);
                                }
                            }).build())
                    .get();

            websocket.sendTextFrame("Beer");
            assertTrue(latch.await(10, TimeUnit.SECONDS));

            assertEquals(1, received.size());
            assertEquals("The bar has Beer", received.get(0));

            websocket.sendCloseFrame();
            c.close();
        }

        // We call the route WebSocket PUB
        {
            final List<String> received = new ArrayList<>();
            final CountDownLatch latch = new CountDownLatch(1);
            final AsyncHttpClient c = new DefaultAsyncHttpClient();
            final WebSocket websocket = c.prepareGet("ws://localhost:" + getPort() + "/pub").execute(
                    new WebSocketUpgradeHandler.Builder()
                            .addWebSocketListener(new WebSocketListener() {
                                @Override
                                public void onTextFrame(String message, boolean finalFragment, int rsv) {
                                    received.add(message);
                                    LOG.info("received --> " + message);
                                    latch.countDown();
                                }

                                @Override
                                public void onOpen(WebSocket websocket) {
                                }

                                @Override
                                public void onClose(WebSocket websocket, int code, String reason) {
                                }

                                @Override
                                public void onError(Throwable t) {
                                    LOG.warn("Unhandled exception: {}", t.getMessage(), t);
                                }
                            }).build())
                    .get();

            websocket.sendTextFrame("wine");
            assertTrue(latch.await(10, TimeUnit.SECONDS));

            assertEquals(1, received.size());
            assertEquals("The pub has wine", received.get(0));

            websocket.sendCloseFrame();
            c.close();
        }

    }

};