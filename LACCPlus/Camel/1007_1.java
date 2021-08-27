//,temp,ProxyReturnFutureExceptionTest.java,32,47,temp,ProxyReturnFutureListTest.java,35,47
//,3
public class xxx {
    @Test
    public void testFutureEchoException() throws Exception {
        Echo service = ProxyHelper.createProxy(context.getEndpoint("direct:echo"), Echo.class);

        Future<String> future = service.asText(4);
        log.info("Got future");

        log.info("Waiting for future to be done ...");
        try {
            assertEquals("Four", future.get(5, TimeUnit.SECONDS));
            fail("Should have thrown exception");
        } catch (ExecutionException e) {
            IllegalArgumentException cause = assertIsInstanceOf(IllegalArgumentException.class, e.getCause());
            assertEquals("Forced", cause.getMessage());
        }
    }

};