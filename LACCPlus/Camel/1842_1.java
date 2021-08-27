//,temp,UndertowWsConsumerRouteTest.java,148,184,temp,UndertowWsConsumerRouteTest.java,63,100
//,3
public class xxx {
    @Test
    public void wsClientSingleBytes() throws Exception {
        AsyncHttpClient c = new DefaultAsyncHttpClient();

        WebSocket websocket = c.prepareGet("ws://localhost:" + getPort() + "/app1")
                .execute(new WebSocketUpgradeHandler.Builder().addWebSocketListener(new WebSocketListener() {

                    @Override
                    public void onOpen(WebSocket webSocket) {
                    }

                    @Override
                    public void onClose(WebSocket webSocket, int code, String reason) {
                    }

                    @Override
                    public void onError(Throwable t) {
                        LOG.warn("Unhandled exception: {}", t.getMessage(), t);
                    }

                    @Override
                    public void onBinaryFrame(byte[] message, boolean finalFragment, int rsv) {
                        System.out.println("got byte[] message");
                    }
                }).build()).get();

        MockEndpoint result = getMockEndpoint("mock:result1");
        final byte[] testmessage = "Test".getBytes("utf-8");
        result.expectedBodiesReceived(testmessage);

        websocket.sendBinaryFrame(testmessage);

        result.assertIsSatisfied();

        websocket.sendCloseFrame();
        c.close();
    }

};