//,temp,WebsocketSSLRouteExampleTest.java,125,184,temp,WebsocketComponentRouteExampleTest.java,55,105
//,3
public class xxx {
    @Test
    public void testWSHttpCall() throws Exception {

        AsyncHttpClient c = createAsyncHttpSSLClient();
        WebSocket websocket = c.prepareGet("wss://127.0.0.1:" + port + "/test").execute(
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

        getMockEndpoint("mock:client").expectedBodiesReceived("Hello from WS client");

        websocket.sendTextFrame("Hello from WS client");
        assertTrue(latch.await(10, TimeUnit.SECONDS));

        assertMockEndpointsSatisfied();

        assertEquals(10, received.size());
        for (int i = 0; i < 10; i++) {
            assertEquals(">> Welcome on board!", received.get(i));
        }

        websocket.sendCloseFrame();
        c.close();
    }

};