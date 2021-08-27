//,temp,sample_3562.java,2,14,temp,sample_3561.java,2,14
//,3
public class xxx {
public void testDefaultTaskExecutor() throws Exception {
context.startRoute("default");
Long beforeThreadCount = currentThreadCount();
getMockEndpoint("mock:result.default").expectedMessageCount(1000);
doSendMessages("foo.default", 500, 5, null);
Thread.sleep(100);
doSendMessages("foo.default", 500, 5, null);
assertMockEndpointsSatisfied();
Long numberThreadsCreated = currentThreadCount() - beforeThreadCount;


log.info("number of threads created testdefaulttaskexecutor");
}

};