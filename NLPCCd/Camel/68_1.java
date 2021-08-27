//,temp,sample_6503.java,2,9,temp,sample_6502.java,2,8
//,3
public class xxx {
public void testFutureListCallTwoTimes() throws Exception {
Users service = ProxyHelper.createProxy(context.getEndpoint("direct:echo"), Users.class);
Future<List<String>> future = service.getUsers(true);
assertFalse("Should not be done", future.isDone());


log.info("waiting for future to be done");
}

};