//,temp,WebsocketRouteExampleTest.java,55,108,temp,WebsocketProducerRouteRestartTest.java,86,139
//,3
public class xxx {
    private void doTestWSHttpCall() throws Exception {
        AsyncHttpClient c = new DefaultAsyncHttpClient();

        WebSocket websocket = c.prepareGet("ws://localhost:" + port + "/shop").execute(
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

        // Send message to the direct endpoint
        producer.sendBodyAndHeader("Beer on stock at Apache Mall", WebsocketConstants.SEND_TO_ALL, "true");

        assertTrue(latch.await(10, TimeUnit.SECONDS));

        assertEquals(1, received.size());
        Object r = received.get(0);
        assertTrue(r instanceof String);
        assertEquals("Beer on stock at Apache Mall", r);

        websocket.sendCloseFrame();
        c.close();
    }

};