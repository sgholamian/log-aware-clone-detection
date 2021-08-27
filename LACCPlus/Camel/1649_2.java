//,temp,WebsocketConsumerRouteTest.java,99,147,temp,WebsocketConsumerRouteTest.java,45,97
//,3
public class xxx {
    @Test
    public void testWSHttpCall() throws Exception {
        AsyncHttpClient c = new DefaultAsyncHttpClient();

        WebSocket websocket = c.prepareGet("ws://127.0.0.1:" + port + "/echo").execute(
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
                                LOG.warn("Unhandled exception: {}", t.getMessage(), t);
                            }

                            @Override
                            public void onBinaryFrame(byte[] payload, boolean finalFragment, int rsv) {

                            }

                            @Override
                            public void onTextFrame(String payload, boolean finalFragment, int rsv) {

                            }

                            @Override
                            public void onPingFrame(byte[] payload) {

                            }

                            @Override
                            public void onPongFrame(byte[] payload) {

                            }
                        }).build())
                .get();

        MockEndpoint result = getMockEndpoint("mock:result");
        result.expectedBodiesReceived("Test");

        websocket.sendTextFrame("Test");

        result.assertIsSatisfied();

        websocket.sendCloseFrame();
        c.close();
    }

};