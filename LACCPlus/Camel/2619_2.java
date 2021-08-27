//,temp,UndertowWsTwoRoutesTest.java,42,125,temp,UndertowWsConsumerRouteTest.java,233,302
//,3
public class xxx {
    @Test
    public void wsClientMultipleText() throws Exception {
        AsyncHttpClient c1 = new DefaultAsyncHttpClient();

        WebSocket websocket1 = c1.prepareGet("ws://localhost:" + getPort() + "/app1")
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
        AsyncHttpClient c2 = new DefaultAsyncHttpClient();

        WebSocket websocket2 = c2.prepareGet("ws://localhost:" + getPort() + "/app1")
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
        result.expectedMessageCount(2);

        websocket1.sendTextFrame("Test1");
        websocket2.sendTextFrame("Test2");

        result.await(60, TimeUnit.SECONDS);
        result.assertIsSatisfied();
        List<Exchange> exchanges = result.getReceivedExchanges();
        Set<String> actual = new HashSet<>();
        actual.add(exchanges.get(0).getIn().getBody(String.class));
        actual.add(exchanges.get(1).getIn().getBody(String.class));
        assertEquals(new HashSet<>(Arrays.asList("Test1", "Test2")), actual);

        websocket1.sendCloseFrame();
        websocket2.sendCloseFrame();
        c1.close();
        c2.close();
    }

};