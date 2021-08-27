//,temp,UndertowWsConsumerRouteTest.java,186,231,temp,UndertowWsConsumerRouteTest.java,102,146
//,3
public class xxx {
    @Test
    public void wsClientSingleTextStreaming() throws Exception {
        AsyncHttpClient c = new DefaultAsyncHttpClient();

        WebSocket websocket = c.prepareGet("ws://localhost:" + getPort() + "/app2")
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

        MockEndpoint result = getMockEndpoint("mock:result2");
        result.expectedMessageCount(1);

        websocket.sendTextFrame("Test");

        result.await(60, TimeUnit.SECONDS);
        List<Exchange> exchanges = result.getReceivedExchanges();
        assertEquals(1, exchanges.size());
        Exchange exchange = result.getReceivedExchanges().get(0);
        assertNotNull(exchange.getIn().getHeader(UndertowConstants.CHANNEL));
        Object body = exchange.getIn().getBody();
        assertTrue(body instanceof Reader, "body is " + body.getClass().getName());
        Reader r = (Reader) body;
        assertEquals("Test", IOConverter.toString(r));

        websocket.sendCloseFrame();
        c.close();
    }

};