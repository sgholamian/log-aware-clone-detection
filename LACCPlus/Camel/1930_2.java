//,temp,WebsocketClientCamelRouteTest.java,55,112,temp,UndertowWsTwoRoutesToSameEndpointTest.java,41,86
//,3
public class xxx {
    @Test
    public void testWSHttpCallEcho() throws Exception {

        // We call the route WebSocket BAR
        final List<String> received = new ArrayList<>();
        final CountDownLatch latch = new CountDownLatch(2);

        DefaultAsyncHttpClient c = new DefaultAsyncHttpClient();

        WebSocket websocket = c.prepareGet("ws://localhost:" + getPort() + "/bar").execute(
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

        assertEquals(2, received.size());

        //Cannot guarantee the order in which messages are received
        assertTrue(received.contains("The bar has Beer"));
        assertTrue(received.contains("Broadcasting to Bar"));

        websocket.sendCloseFrame();
        c.close();
    }

};