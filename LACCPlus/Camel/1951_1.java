//,temp,WebsocketTwoRoutesToSIndividualAndBroadcastEndpointExampleTest.java,55,116,temp,UndertowWsProducerRouteTest.java,49,92
//,3
public class xxx {
    @Test
    public void testWSHttpCallEcho() throws Exception {

        // We call the route WebSocket BAR
        received.clear();
        latch = new CountDownLatch(2);

        AsyncHttpClient c = new DefaultAsyncHttpClient();

        WebSocket websocket = c.prepareGet("ws://localhost:" + port + "/bar").execute(
                new WebSocketUpgradeHandler.Builder()
                        .addWebSocketListener(new WebSocketListener() {
                            @Override
                            public void onOpen(WebSocket websocket) {
                            }

                            @Override
                            public void onClose(WebSocket websocket, int code, String reason) {

                            }

                            @Override
                            public void onError(Throwable t) {
                                log.warn("Unhandled exception: {}", t.getMessage(), t);
                            }

                            @Override
                            public void onBinaryFrame(byte[] payload, boolean finalFragment, int rsv) {

                            }

                            @Override
                            public void onTextFrame(String payload, boolean finalFragment, int rsv) {
                                received.add(payload);
                                log.info("received --> " + payload);
                                latch.countDown();
                            }

                            @Override
                            public void onPingFrame(byte[] payload) {

                            }

                            @Override
                            public void onPongFrame(byte[] payload) {

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