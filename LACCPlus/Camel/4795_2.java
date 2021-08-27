//,temp,WebsocketTwoRoutesToSameEndpointExampleTest.java,54,115,temp,UndertowWssRouteTest.java,120,165
//,3
public class xxx {
    @Test
    public void testWSHttpCall() throws Exception {
        final List<String> received = new ArrayList<>();
        final CountDownLatch latch = new CountDownLatch(10);

        AsyncHttpClient c = createAsyncHttpSSLClient();
        WebSocket websocket = c.prepareGet("wss://localhost:" + getPort() + "/test").execute(
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