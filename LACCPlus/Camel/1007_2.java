//,temp,ProxyReturnFutureExceptionTest.java,32,47,temp,ProxyReturnFutureListTest.java,35,47
//,3
public class xxx {
    @Test
    public void testFutureList() throws Exception {
        Users service = ProxyHelper.createProxy(context.getEndpoint("direct:echo"), Users.class);

        Future<List<String>> future = service.getUsers(true);
        log.info("Got future");
        assertFalse(future.isDone(), "Should not be done");
        log.info("Waiting for future to be done ...");

        List<String> users = future.get(2, TimeUnit.SECONDS);
        assertEquals("Claus", users.get(0));
        assertEquals("Jonathan", users.get(1));
    }

};