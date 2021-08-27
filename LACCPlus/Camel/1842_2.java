//,temp,UndertowWsConsumerRouteTest.java,148,184,temp,UndertowWsConsumerRouteTest.java,63,100
//,3
public class xxx {
    @Test
    public void wsClientSingleText() throws Exception {
        AsyncHttpClient c = new DefaultAsyncHttpClient();

        WebSocket websocket = c.prepareGet("ws://localhost:" + getPort() + "/app1")
                .execute(new WebSocketUpgradeHandler.Builder().addWebSocketListener(new WebSocketListener() {

                    @Override
                    public void onTextFrame(String message, boolean finalFragment, int rsv) {
                        System.out.println("got message " + message);
                    }

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

                }).build()).get();

        MockEndpoint result = getMockEndpoint("mock:result1");
        result.expectedBodiesReceived("Test");

        websocket.sendTextFrame("Test");

        result.await(60, TimeUnit.SECONDS);
        result.assertIsSatisfied();

        websocket.sendCloseFrame();
        c.close();
    }

};