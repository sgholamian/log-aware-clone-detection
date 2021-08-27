//,temp,sample_6500.java,2,8,temp,sample_6501.java,2,9
//,3
public class xxx {
public void testFutureList() throws Exception {
Users service = ProxyHelper.createProxy(context.getEndpoint("direct:echo"), Users.class);
Future<List<String>> future = service.getUsers(true);
assertFalse("Should not be done", future.isDone());


log.info("waiting for future to be done");
}

};