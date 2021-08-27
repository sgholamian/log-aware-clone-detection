//,temp,WebsocketTwoRoutesToSIndividualAndBroadcastEndpointExampleTest.java,55,116,temp,UndertowWsProducerRouteTest.java,49,92
//,3
public class xxx {
    @Test
    public void testWSHttpCall() throws Exception {

        final CountDownLatch latch = new CountDownLatch(1);
        AsyncHttpClient c = new DefaultAsyncHttpClient();
        final List<Object> received = Collections.synchronizedList(new ArrayList<>());

        WebSocket websocket = c.prepareGet("ws://localhost:" + getPort() + "/shop")
                .execute(new WebSocketUpgradeHandler.Builder().addWebSocketListener(new WebSocketListener() {

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
                }).build()).get();

        // Send message to the direct endpoint
        producer.sendBodyAndHeader("Beer on stock at Apache Mall", UndertowConstants.SEND_TO_ALL, "true");

        assertTrue(latch.await(10, TimeUnit.SECONDS));

        assertEquals(1, received.size());
        Object r = received.get(0);
        assertTrue(r instanceof String);
        assertEquals("Beer on stock at Apache Mall", r);

        websocket.sendCloseFrame();
        c.close();
    }

};