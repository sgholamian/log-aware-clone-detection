//,temp,sample_6504.java,2,13,temp,sample_6505.java,2,14
//,3
public class xxx {
public void testFutureListCallTwoTimes() throws Exception {
Users service = ProxyHelper.createProxy(context.getEndpoint("direct:echo"), Users.class);
Future<List<String>> future = service.getUsers(true);
assertFalse("Should not be done", future.isDone());
List<String> users = future.get(2, TimeUnit.SECONDS);
assertEquals("Claus", users.get(0));
assertEquals("Jonathan", users.get(1));
future = service.getUsers(true);
assertFalse("Should not be done", future.isDone());


log.info("waiting for future to be done");
}

};